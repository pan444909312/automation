create table if not exists automation_test.automation_coverage_api
(
    id                             bigint auto_increment comment '主键id'
        primary key,
    host                           varchar(64)  default '' not null comment '域名',
    method                         varchar(16)  default '' not null comment 'HTTP 请求方法',
    path                           varchar(512) default '' not null comment '请求路径',
    requests_times_production      bigint       default 0  not null comment '线上请求次数',
    country                        varchar(16)  default '' not null comment '国家',
    is_automation                  tinyint      default 0  not null comment '是否已经实现自动化，0:否、1:是',
    api_test_author                varchar(64)  default '' not null comment '接口测试负责人',
    last_execute_result            varchar(16)  default '' not null comment '测试用例执行结果, Successful, Failed, Disabled, Aborted',
    last_execute_time              bigint       default 0  not null comment '最后一次执行时间',
    last_executor                  varchar(64)  default '' not null comment '最后一次执行人员',
    api_status                     tinyint      default 0  not null comment '接口状态: 0:正常、-1:已废弃',
    test_case_response_status_code varchar(16)  default '' not null comment '测试用例响应状态吗，可用于接口是否响应成功的数据统计',
    test_case_response_body        longtext                null comment '最后一次测试用例的响应结果',
    test_case_request_path         varchar(255) default '' not null comment '测试用例的url参数',
    test_case_request_method       varchar(16)  default '' not null comment '测试用例请求Method',
    test_case_request_uri          varchar(256) default '' not null comment '测试用例请求uri',
    test_case_request_headers      longtext                null comment '测试用例请求头',
    test_case_request_body         longtext                null comment '测试用例请求体',
    project_id                     varchar(64)  default '' not null comment '冗余字段，目前主要给apifox使用'
)
    comment '自动化测试接口覆盖率';

create index automation_coverage_api_requests_times_production_index
    on automation_test.automation_coverage_api (requests_times_production)
    comment '线上请求数';

create index idx_path
    on automation_test.automation_coverage_api (path)
    comment '接口路径';

