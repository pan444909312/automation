create table if not exists auto_case_chart_future_data
(
    id                      bigint unsigned auto_increment comment 'ID 自增'
    primary key,
    future_time             bigint  default 0 not null comment '未来日期',
    chart_type              int     default 0 not null comment '报表类型 1：场景总ROI表 2：用例增长趋势表 3：用例执行趋势表',
    executionType           int     default 0 not null comment '执行策略 0:未知策略 1:日常巡检;2:质量保证;3:效率提升（仅chart_type=1的表该字段有用）',
    expected_save_time      bigint  default 0 not null comment '预计累计收益',
    expected_increase_case  int     default 0 not null comment '预计新增用例数',
    expected_execution_case int     default 0 not null comment '预计执行用例数',
    create_time             bigint  default 0 not null comment '创建时间',
    update_time             bigint  default 0 not null comment '更新时间',
    is_deleted              tinyint default 0 not null comment '删除标记（0:可用 1:不可用）'
)
    comment '自动化用例执行趋势表' charset = utf8mb4;

create table if not exists auto_case_execution_chart
(
    id                     bigint unsigned auto_increment comment 'ID 自增'
    primary key,
    execution_case         int         default 0  not null comment '执行用例数',
    execution_success_time int         default 0  not null comment '执行成功数量',
    execution_fail_time    int         default 0  not null comment '执行失败数量',
    remarks                varchar(50) default '' not null comment '备注',
    create_time            bigint      default 0  not null comment '创建时间',
    update_time            bigint      default 0  not null comment '更新时间',
    is_deleted             tinyint     default 0  not null comment '删除标记（0:可用 1:不可用）'
    )
    comment '自动化用例执行趋势表';

create table if not exists auto_case_increase_chart
(
    id               bigint unsigned auto_increment comment 'ID 自增'
    primary key,
    increase_case    int         default 0  not null comment '新增用例数',
    development_time int         default 0  not null comment '总开发成本',
    manual_test_time int         default 0  not null comment '总手工测试成本',
    remarks          varchar(50) default '' not null comment '备注',
    create_time      bigint      default 0  not null comment '创建时间',
    update_time      bigint      default 0  not null comment '更新时间',
    is_deleted       tinyint     default 0  not null comment '删除标记（0:可用 1:不可用）'
    )
    comment '自动化用例增长趋势表';

create table if not exists auto_case_roi
(
    id               bigint unsigned auto_increment comment 'ID 自增'
    primary key,
    scenario_id      varchar(50)  default '' not null comment '场景id',
    scenario_name    varchar(100) default '' not null comment '场景名称',
    development_time int          default 0  not null comment '开发成本',
    maintenance_time int          default 0  not null comment '维护成本',
    manual_test_time int          default 0  not null comment '手工测试成本',
    times            int          default 0  not null comment '执行次数',
    save_time        bigint       default 0  not null comment '总节省成本',
    roi              varchar(50)  default '' not null comment '场景ROI',
    execution_user   varchar(100) default '' not null comment '执行人员名称',
    create_time      bigint       default 0  not null comment '创建时间',
    update_time      bigint       default 0  not null comment '更新时间',
    is_deleted       tinyint      default 0  not null comment '删除标记（0:可用 1:不可用）'
    )
    comment '自动化用例表';

create table if not exists auto_case_roi_chart
(
    id                     bigint unsigned auto_increment comment 'ID 自增'
    primary key,
    total_maintenance_time bigint      default 0  not null comment '累计维护成本',
    total_development_time bigint      default 0  not null comment '累计开发成本',
    times                  int         default 0  not null comment '累计执行次数',
    save_time              bigint      default 0  not null comment '累计收益',
    roi                    varchar(50) default '' not null comment '场景ROI',
    execution_type         int         default 0  not null comment '执行策略 0:未知策略 1:日常巡检;2:质量保证;3:效率提升',
    create_time            bigint      default 0  not null comment '创建时间',
    update_time            bigint      default 0  not null comment '更新时间',
    is_deleted             tinyint     default 0  not null comment '删除标记（0:可用 1:不可用）'
    )
    comment '测试场景总ROI表';

create table if not exists auto_case_roi_log
(
    id               bigint unsigned auto_increment comment '主键ID'
    primary key,
    scenario_id      varchar(64) default '' not null comment '场景ID，不能为空',
    save_time        bigint      default 0  not null comment '总节省成本',
    roi              varchar(64) default '' null comment '场景ROI',
    execution_user   varchar(64) default '' null comment '执行人员名称',
    create_time      bigint      default 0  null comment '创建时间',
    development_time int         default 0  null comment '开发成本',
    maintenance_time int         default 0  null comment '维护成本',
    manual_test_time int         default 0  null comment '手工测试成本'
    )
    comment '自动化测试框架执行日志';

create index auto_case_roi_log_scenario_id_index
    on auto_case_roi_log (scenario_id);

create table if not exists auto_execution_record
(
    id               bigint unsigned auto_increment comment '执行记录ID 自增'
    primary key,
    scenario_id      varchar(50)  default '' not null comment '场景id',
    execution_type   int          default 0  not null comment '执行策略 0:未知策略1:日常巡检;2:质量保证;3:效率提升',
    execution_time   bigint       default 0  not null comment '执行时间',
    execution_status int          default -1 not null comment '执行结果 -1:执行异常;1:执行成功;2:执行失败',
    execution_user   varchar(100) default '' not null comment '执行人员名称',
    development_time int unsigned            null comment '开发成本',
    maintenance_time int unsigned            null comment '维护成本',
    manual_test_time int unsigned            null comment '手工测试成本',
    create_time      bigint       default 0  not null comment '创建时间',
    update_time      bigint       default 0  not null comment '更新时间',
    is_deleted       tinyint      default 0  not null comment '删除标记（0:可用 1:不可用）'
    )
    comment '自动化用例执行记录表';

create table if not exists config
(
    id               bigint unsigned auto_increment comment 'ID 自增'
    primary key,
    config_key       varchar(80)  default '' not null comment '配置的key值',
    config_value     text                    not null comment '配置的value值',
    config_desc      varchar(100) default '' null comment '配置描述',
    last_modify_user varchar(100) default '' not null comment '最后修改人员名称',
    create_time      bigint       default 0  not null comment '创建时间',
    update_time      bigint       default 0  not null comment '更新时间',
    is_deleted       tinyint      default 0  not null comment '删除标记（0:可用 1:不可用）'
    )
    comment '系统配置表';
