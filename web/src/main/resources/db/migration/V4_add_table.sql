create table if not exists automation_coverage_api
(
    id                        bigint auto_increment comment '主键id',
    host                      varchar(64)  default '' not null comment '域名',
    method                    varchar(16)  default '' not null comment 'HTTP 请求方法',
    path                      varchar(255) default '' not null comment '请求路径',
    requests_times_production bigint       default 0  not null comment '线上请求次数',
    country                   varchar(64)  default '' not null comment '国家',
    is_automation             tinyint      default 0  not null comment '是否已经实现自动化',
    last_execute_log          varchar(1024) default ''  not null comment '最后一次请求日志，必须包含响应结果',
    last_execute_time         int(16)      default 0          not null comment '最后一次执行时间',
    executor                  varchar(64)  default '' not null comment '执行人员',
    is_delete                 tinyint      default 0  not null comment '是否删除',
    primary key (id),
    index                     idx_path (path) comment '接口路径',
    index                     idx_requests_times (requests_times_production) comment '线上请求次数'
) comment='自动化测试接口覆盖率';

-- # uk 线上阿里云日志导出的数据
-- # update automation_coverage_api set country = 'uk' where id <= 46705;

-- # usa 线上阿里云日志导出的数据
-- # update automation_coverage_api set country = 'usa' where id > 46705;

