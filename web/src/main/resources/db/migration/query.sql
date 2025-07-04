-- 自动化测试覆盖率数据,查询接口负责人所在的项目,在 MaxCompute 中执行
WITH ranked_data AS (
    SELECT 
        aca.*,  -- 保留automation_coverage_api表的所有字段
        u.name as user_name,
        u.user_id,
        ubp.project_id as bind_project_id,
        p.name as project_name,
        ROW_NUMBER() OVER (PARTITION BY aca.path ORDER BY aca.last_execute_time DESC) as rn
    FROM hungry_panda_data_center.ods_hp_automation_coverage_api_df aca
    -- 步骤1：连接user表，获取name和user_id
    LEFT JOIN hungry_panda_data_center.ods_hp_auto_user_df u 
        ON aca.api_test_author = u.name and u.dt = '{{{YESTERDAY}}}'   
    -- 步骤2：连接user_bind_project表，获取project_id
    LEFT JOIN hungry_panda_data_center.ods_hp_user_bind_project_df ubp 
        ON u.user_id = ubp.user_id and ubp.dt = '{{{YESTERDAY}}}'   
    -- 步骤3：连接project表，获取project名称
    LEFT JOIN hungry_panda_data_center.ods_hp_project_df p 
        ON ubp.project_id = p.project_id and p.dt = '{{{YESTERDAY}}}'   
    WHERE aca.api_test_author != '' and aca.dt = '{{{YESTERDAY}}}'   
)
SELECT 
    id, host, method, path, requests_times_production, country, 
    is_automation, api_test_author, last_execute_result, last_execute_time, 
    last_executor, api_status, test_case_response_status_code, 
    test_case_response_body, test_case_request_path, test_case_request_method, 
    test_case_request_uri, test_case_request_headers, test_case_request_body, 
    project_id, dt,
    user_name,
    user_id,
    bind_project_id,
    project_name
FROM ranked_data 
WHERE rn = 1
ORDER BY is_automation DESC;



-- 在 MaxCompute 中执行,用例执行趋势,过滤
WITH execution_trend AS (
    SELECT 
        t.*,
        FROM_UNIXTIME(cast(t.create_time/1000 as bigint)) as create_time_date  -- 新增日期格式字段
    FROM hungry_panda_data_center.ods_hp_auto_case_execution_chart_df t
    WHERE t.execution_type in (2, 3) and t.create_time >= 1746028800000 
        and is_deleted = 0 
        and dt = '2025-06-08'
)
SELECT * FROM execution_trend;


-- 用例增长趋势,在 MaxCompute 中执行
SELECT 
    t.*,
    FROM_UNIXTIME(cast(t.create_time/1000 as bigint)) as create_time_readable,
    DATE_FORMAT(FROM_UNIXTIME(cast(t.create_time/1000 as bigint)), 'yyyy-MM-dd') as create_date,
    DATE_FORMAT(FROM_UNIXTIME(cast(t.create_time/1000 as bigint)), 'yyyy-MM-dd HH:mm:ss') as create_datetime
FROM hungry_panda_data_center.ods_hp_auto_case_increase_chart_df t
WHERE t.create_time >= 1746028800000 
    and is_deleted = 0 
    and chart_date != '' 
    and dt = '{{{yesterday}}}'; 




