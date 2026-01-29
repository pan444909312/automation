package com.miller.service.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 仪表盘过滤选项 - 返回实例
 */
@Data
@Accessors(chain = true)
public class DashboardFilterOptionVO {

    private List<Option> users;

    private List<Option> groups;

    private List<Option> platform;

    @Data
    @Accessors(chain = true)
    public static class Option {
        private String label;
        private Object value;

        public Option(String label, Object value) {
            this.label = label;
            this.value = value;
        }
    }
}
