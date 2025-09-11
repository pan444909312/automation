package com.miller.service.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: panjuxiang
 * @Since: 2025/9/11
 */
@Component
public  class ExcelDataListener  extends AnalysisEventListener<ExcelDataListener.ExcelData> {

    private final List<ExcelData> dataList;

    public ExcelDataListener(List<ExcelData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void invoke(ExcelData data, AnalysisContext context) {
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 所有数据解析完成后的操作
    }

    @Data
    public static class ExcelData {
        @ExcelProperty(index = 0)
        private String host;

        @ExcelProperty(index = 1)
        private String method;

        @ExcelProperty(index = 2)
        private String path;

        @ExcelProperty(index = 3)
        private Long requestsTimesProduction;
    }

}
