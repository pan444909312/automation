package com.miller.entity.apifox.DTO;

import com.miller.entity.tools.ToolEfficiencyStatsEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;
import java.util.List;

@Data
@Accessors(chain = true)
public class ApiFoxToolsExecDTO {

    /**
     * 用例ID
     */
    private String taskId;

    /**
     * 环境配置ID
     */
    private String envId;

    /**
     * 变量信息
     */
    private List<EnvVar> envVarList;

    /**
     * 执行人ID
     */
    private String executor;

    /**
     * 工具基础信息
     */
    private ToolEfficiencyStatsEntity  toolEfficiencyStats;

    @Data
    public static class EnvVar{
        private String key;
        private String value;

        public String toEnvVarStr(){
            return String.format(" --env-var %s=%s ",key,value);
        }
    }
    public String toEnvVarListStr(){
        StringBuilder envVarListStr = new StringBuilder();
        envVarListStr.append(" --global-var auto_execution_record=3 ");
        envVarListStr.append(" --global-var apifox_host=http://localhost:9080 ");

        if (!ObjectUtils.isEmpty(this.envVarList) && this.envVarList.size()>0){
            this.envVarList.forEach(envVar->{
                envVarListStr.append(envVar.toEnvVarStr());
            });
        }
        return envVarListStr.toString();
    }


    /**
     * 必要字段检查
     * @return
     */
    public boolean fieldCheck(){
        if (ObjectUtils.isEmpty(this.taskId) ) return false;
        if (ObjectUtils.isEmpty(this.envId) ) return false;
        return true;
    }

}
