package com.miller.service.tools.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExcelImportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 导入并更新数据库。自动读取 Excel 的第一行作为表头，从第二行开始读取数据。根据 Excel 中的 path 和负责人列，更新数据库中的api_test_author字段。
     * @param file Excel模版，path和负责人必须要有
     * @param sheetName sheet名称
     * @return 负责人为空的行信息
     */
    public List<String> importAndUpdate(MultipartFile file, String sheetName) {
        AtomicReference<Map<Integer, String>> headerRef = new AtomicReference<>();
        List<Map<Integer, String>> dataList = new ArrayList<>();
        List<String> emptyAuthorRows = new ArrayList<>();

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
        if (headerMap == null) return emptyAuthorRows;
        Map<String, Integer> colIndexMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : headerMap.entrySet()) {
            colIndexMap.put(entry.getValue(), entry.getKey());
        }
        Integer pathIdx = colIndexMap.get("path");
        Integer authorIdx = colIndexMap.get("负责人");
        Integer plannedTimeIdx = colIndexMap.get("预计完成时间");
        Integer remarkIdx = colIndexMap.get("备注");
        if (pathIdx == null || authorIdx == null || plannedTimeIdx == null || remarkIdx == null) return emptyAuthorRows;

        for (Map<Integer, String> row : dataList) {
            String path = row.get(pathIdx);
            String author = row.get(authorIdx);
            String plannedTime = row.get(plannedTimeIdx);
            String remark = row.get(remarkIdx);

            if (author == null || author.trim().isEmpty()) {
                emptyAuthorRows.add("行 " + (dataList.indexOf(row) + 2) + ": path=" + path + ", 负责人为空");
                continue;
            }

            // 构建动态SQL语句
            StringBuilder sqlBuilder = new StringBuilder("UPDATE automation_test.automation_coverage_api SET api_test_author = ?");
            List<Object> params = new ArrayList<>();
            params.add(author);

            // 如果预计完成时间不为空，添加到SQL语句中
            if (plannedTime != null && !plannedTime.trim().isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Timestamp plannedTimeStamp = new Timestamp(sdf.parse(plannedTime).getTime());
                    sqlBuilder.append(", planned_completion_time = ?");
                    params.add(plannedTimeStamp);
                } catch (ParseException e) {
                    throw new RuntimeException("预计完成时间格式错误，应为yyyy/MM/dd格式", e);
                }
            }

            // 如果备注不为空，添加到SQL语句中
            if (remark != null && !remark.trim().isEmpty()) {
                sqlBuilder.append(", remark = ?");
                params.add(remark);
            }

            // 添加WHERE条件
            sqlBuilder.append(" WHERE path = ?");
            params.add(path);

            // 执行更新
            jdbcTemplate.update(sqlBuilder.toString(), params.toArray());
        }
        return emptyAuthorRows;
    }
} 