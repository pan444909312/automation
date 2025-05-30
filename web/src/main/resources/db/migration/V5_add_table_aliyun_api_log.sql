-- 查询线上log数据，并存储到 aliyun_api_log 表中,默认取100亿数据
* | select host, method,
       url_extract_path(url)                      as "path",
       count(*)                                   as requests_times_production,
       min(date_parse(time, '%d/%b/%Y:%H:%i:%s')) as first_request_time,
       max(date_parse(time, '%d/%b/%Y:%H:%i:%s')) as last_request_time
from log
group by host, method, url_extract_path(url)
order by requests_times_production desc limit 1000000000

CREATE TABLE IF NOT EXISTS aliyun_api_log_summary (
    id                        bigint auto_increment comment '主键ID'
    primary key,
    host                      varchar(255) default '' not null comment '主机名',
    method                    varchar(10)  default '' not null comment '请求方法',
    path                      varchar(500) default '' not null comment '请求URL路径',
    requests_times_production bigint       default 0  not null comment '请求次数',
    first_request_time        datetime                null comment '首次请求时间',
    last_request_time         datetime                null comment '最后请求时间',
    country                   varchar(64)  default '' not null comment '日志来源fr-uk-ack-pro、usa-ca-ack-pro'

    -- 创建索引
    INDEX idx_host (host) COMMENT '主机名索引',
    INDEX idx_method (method) COMMENT '请求方法索引',
    INDEX idx_path (path) COMMENT 'URL路径索引',
    INDEX idx_requests_times_production (requests_times_production) COMMENT '请求次数索引',
    INDEX idx_last_request_time (last_request_time) COMMENT '最后请求时间索引',
    INDEX idx_host_path (host, path) COMMENT '主机名和URL路径组合索引',
    INDEX idx_host_method (host, method) COMMENT '主机名和请求方法组合索引',
    INDEX idx_path_method (path, method) COMMENT 'URL路径和请求方法组合索引',
    INDEX idx_host_path_method (host, path, method) COMMENT '主机名、URL路径和请求方法组合索引',
    INDEX idx_requests_times_production_time (requests_times_production, last_request_time) COMMENT '请求次数和时间组合索引'
) COMMENT='阿里云API访问日志汇总表';
