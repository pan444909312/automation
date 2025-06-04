package com.miller.service.tools.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ExcelImportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 导入并更新数据库。自动读取 Excel 的第一行作为表头，从第二行开始读取数据。根据 Excel 中的 path 和负责人列，更新数据库中的api_test_author字段。
     * @param file Excel模版，path和负责人必须要有
     * @param sheetName sheet名称
     */
    public void importAndUpdate(MultipartFile file, String sheetName) {
        AtomicReference<Map<Integer, String>> headerRef = new AtomicReference<>();
        List<Map<Integer, String>> dataList = new ArrayList<>();

        try {
            EasyExcel.read(file.getInputStream())
                    .sheet(sheetName)
                    .headRowNumber(1)
                    .registerReadListener(new AnalysisEventListener<Map<Integer, String>>() {
                        @Override
                        public void invoke(Map<Integer, String> data, AnalysisContext context) {
                            dataList.add(data);
                        }
                        @Override
                        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                            headerRef.set(headMap);
                        }
                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {}
                    })
                    .doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, String> headerMap = headerRef.get();
        if (headerMap == null) return;
        Map<String, Integer> colIndexMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : headerMap.entrySet()) {
            colIndexMap.put(entry.getValue(), entry.getKey());
        }
        Integer pathIdx = colIndexMap.get("path");
        Integer authorIdx = colIndexMap.get("负责人");
        if (pathIdx == null || authorIdx == null) return;

        for (Map<Integer, String> row : dataList) {
            String path = row.get(pathIdx);
            String author = row.get(authorIdx);
            if (path != null && author != null) {
                String sql = "UPDATE automation_test.automation_coverage_api SET api_test_author = ? WHERE path = ?";
                jdbcTemplate.update(sql, author, path);
            }
        }
    }
} 