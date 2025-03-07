-- 新增字段
alter table auto_case_roi
    add expect_times int(13) null comment '预期测试用例被执行的次数';
alter table auto_case_roi
    add remark text null comment '备注信息';
