-- 时间统一设置为 2025/03/01（时间戳毫秒: 1740758400000）

-- 初始化计算器 demo 表
INSERT INTO calculator (first_number, second_number, result)
VALUES (3, 4, 12);
INSERT INTO calculator (first_number, second_number, result)
VALUES (2, 3, 5);

-- 初始化用户表，统一密码123456.
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('admin', '系统管理员', 'admin@hungrypandagroup.com', 'b9f6c2f2f7a1e03c28d25f1ecc5901b2', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('chenchunxia', '陈春霞', 'chenchunxia@hungrypandagroup.com', 'c4074ba3c470800aca318a05c15c11eb', '0',
        1740758400000, 1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('conan', '江彪', 'conan@hungrypandagroup.com', '347d430d18769520d484bf63964f1cd2', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('heyuan', '贺院', 'heyuan@hungrypandagroup.com', 'b9cfe0996694e938f134790cedaa2d38', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('huyang', '胡杨', 'huyang@hungrypandagroup.com', 'fe495268daaa1d882676069b19954259', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('lipan', '李攀', 'lipan@hungrypandagroup.com', '3e97f30b1e9b6024d95469333b035762', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('liurufeng', '刘汝锋', 'liurufeng@hungrypandagroup.com', 'ad68fef26ace9d82c7671cd3ef89245b', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('luwei', '鲁伟', 'luwei@hungrypandagroup.com', '062279282827f06682280d8b4e4a905d', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('MengFanXu', '孟凡旭', 'mengfanxu@hungrypandagroup.com', '1397773c2f9e4cdc7f41bfd29adbe426', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('panjuxiang', '潘炬翔', 'panjuxiang@hungrypandagroup.com', 'c84c65168a95f2d514afeff1ba6f225b', '0',
        1740758400000, 1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('shandongdong', '单东东', 'shandongdong@hungrypandagroup.com', 'ae3f11b7494499b7dca4504322f1f44f', '0',
        1740758400000, 1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('TestingConsultant', '彭路路', 'TestingConsultant@hungrypandagroup.com', '71bd03086a0f54c3b0f22ee5bf8ffae7',
        '0', 1740758400000, 1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('testingconsultant2', '史欣月', 'testingconsultant2@hungrypandagroup.com', 'a57b469a0b063a680424622a4f460168',
        '0', 1740758400000, 1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('WangXiaoHao', '王晓皓', 'wangxiaohao@hungrypandagroup.com', 'fd14a0fc5db2d70d03d92a1154330b83', '0',
        1740758400000, 1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('yancancan', '严灿灿', 'yancancan@hungrypandagroup.com', 'b8d9363ae4539bff527e00b5afc80d97', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('YuWei', '余薇', 'yuwei@hungrypandagroup.com', '07c6fd9e900f1216389c538ec01c5f87', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('zhangcheng', '张成', 'zhangcheng@hungrypandagroup.com', 'c038009d0e9b147670c8c5a8658af071', '0', 1740758400000,
        1740758400000);
INSERT INTO user (user_id, name, email, password, status, create_time, update_time)
VALUES ('zhangpei', '张培', 'zhangpei@hungrypandagroup.com', '7714b8e7720d303e1601022da3e87128', '0', 1740758400000,
        1740758400000);

-- 初始化项目表
INSERT INTO project (project_id, name, status, description, create_time, update_time)
VALUES ('0d2ba138-79b6-4d43-92ed-5067a0390d12', '演示项目', 0, '演示项目', 1740758400000, 1740758400000);
INSERT INTO project (project_id, name, status, description, create_time, update_time)
VALUES ('1', 'B端-商家组', 0, '', 1740758400000, 1740758400000);
INSERT INTO project (project_id, name, status, description, create_time, update_time)
VALUES ('2', 'C端-导购组', 0, '', 1740758400000, 1740758400000);
INSERT INTO project (project_id, name, status, description, create_time, update_time)
VALUES ('3', 'D端-骑手组', 0, '', 1740758400000, 1740758400000);
INSERT INTO project (project_id, name, status, description, create_time, update_time)
VALUES ('4', 'P端-平台组', 0, '', 1740758400000, 1740758400000);

-- 初始化角色表
INSERT INTO role (role_id, name, description, create_time, update_time)
VALUES ('admin', '系统管理员', null, 1740758400000, 1740758400000);
INSERT INTO role (role_id, name, description, create_time, update_time)
VALUES ('dev', '开发人员', null, 1740758400000, 1740758400000);
INSERT INTO role (role_id, name, description, create_time, update_time)
VALUES ('po', '项目拥有者', null, 1740758400000, 1740758400000);
INSERT INTO role (role_id, name, description, create_time, update_time)
VALUES ('test', '测试人员', null, 1740758400000, 1740758400000);

-- 初始化权限表
INSERT INTO permission (permission_id, permission_name, path, is_menu, parent_id, permission_code)
VALUES ('e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a', '缺陷管理', '/issues', '1', '0', 'issues');
INSERT INTO permission (permission_id, permission_name, path, is_menu, parent_id, permission_code)
VALUES ('e71f3fe1-aa17-4aff-aa05-2b5a3a5c784b', '缺陷列表', '/issues/list', '1', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a',
        'issues:reports');
INSERT INTO permission (permission_id, permission_name, path, is_menu, parent_id, permission_code)
VALUES ('e71f3fe1-aa17-4aff-aa05-2b5a3a5c785a', '系统管理', '/manage', '1', '0', 'manage');
INSERT INTO permission (permission_id, permission_name, path, is_menu, parent_id, permission_code)
VALUES ('e71f3fe1-aa17-4aff-aa05-2b5a3a5c785b', '用户管理', '/manage/user', '1', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c785a',
        'manage:user');

-- 初始化用户绑定项目表
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12PYGJ3ZYHKR30FY5FQJ', '3', 'chenchunxia', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12QFY5BGJ751HFKQYKM5', '3', 'conan', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12QMS2TM47BDBE0WGW1Y', '4', 'heyuan', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12QS3XAD926W5ZGNWY7Z', '2', 'huyang', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12QW2JKNQ4B3496D7KJV', '1', 'lipan', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12QZHDDKA50EWTY6ZYWY', '5', 'liurufeng', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12R235AR72Y4W79ZJBMG', '4', 'luwei', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12R5EKGTNSSJWHEABYFS', '1', 'MengFanXu', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12R8ZVW3ANEKPF9Q795P', '2', 'panjuxiang', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12RB4B9SEPZQ8WWK77CY', '2', 'shandongdong', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12RDPH5QX8QXNN6Q8A7Q', '3', 'TestingConsultant', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12RG1Q7SYZQ63RGX6SF5', '1', 'testingconsultant2', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12RK8MEGNSVS76F8XH5Q', '1', 'WangXiaoHao', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12RPPWR31MNTY5B6NJKS', '2', 'yancancan', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12RSN2E4C71DMWT9H8VT', '1', 'YuWei', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12RX6Y9ZFJFVFXD7HBAK', '4', 'zhangcheng', 1740758400000, 1740758400000);
INSERT INTO user_bind_project (user_project_id, project_id, user_id, create_time, update_time)
VALUES ('01JMCA12S0VG8KTZ20P4Y6EY11', '2', 'zhangpei', 1740758400000, 1740758400000);

-- 初始化用户绑定角色表
INSERT INTO user_bind_role (user_role_id, user_id, role_id, create_time, update_time)
VALUES ('06bf6259-7c09-49ed-b20d-677d567d80f2', 'admin', 'po', 1740758400000, 1740758400000);
INSERT INTO user_bind_role (user_role_id, user_id, role_id, create_time, update_time)
VALUES ('6d86c757-0cef-4271-b01b-18f77a42dab1', 'admin', 'dev', 1740758400000, 1740758400000);
INSERT INTO user_bind_role (user_role_id, user_id, role_id, create_time, update_time)
VALUES ('7037fff7-547c-4a3f-a0f1-3ae4882a9e6f', 'admin', 'admin', 1740758400000, 1740758400000);
INSERT INTO user_bind_role (user_role_id, user_id, role_id, create_time, update_time)
VALUES ('f94dbcf3-d527-4fa8-ba89-3a8533efad8d', 'admin', 'test', 1740758400000, 1740758400000);

-- 初始化角色绑定的权限表
INSERT INTO role_bind_permission (role_id, permission_id)
VALUES ('admin', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a');
INSERT INTO role_bind_permission (role_id, permission_id)
VALUES ('admin', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784b');
INSERT INTO role_bind_permission (role_id, permission_id)
VALUES ('admin', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c785a');
INSERT INTO role_bind_permission (role_id, permission_id)
VALUES ('admin', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c785b');
INSERT INTO role_bind_permission (role_id, permission_id)
VALUES ('dev', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a');
INSERT INTO role_bind_permission (role_id, permission_id)
VALUES ('dev', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784b');
INSERT INTO role_bind_permission (role_id, permission_id)
VALUES ('test', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a');
INSERT INTO role_bind_permission (role_id, permission_id)
VALUES ('test', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784b');

-- 初始化配置表
INSERT INTO config (config_key, config_value, config_desc, last_modify_user, create_time,
                    update_time, is_deleted)
VALUES ('DEFAULT_FUTURE_DATE', '3', '未来日期默认月数', '', 1740758400000, 1740758400000, 0);

-- 初始化缺陷表
INSERT INTO issues (issue_id, title, description, status, create_time, update_time, creator,
                    update_user, project_id, handler)
VALUES ('fca6f822-6f19-4472-b8d7-8c391e7a6bb6', '测试创建缺陷e81d63b3-8639-485f-aac7-5588a887cb93', '这是缺陷描述', '1',
        1740758400000, 1740758400000, 'admin@hungrypandagroup.com', 'admin@hungrypandagroup.com',
        'c0c42460-5945-464b-b8da-f869e979ca28', 'admin@hungrypandagroup.com');


-- 初始化部门表
INSERT INTO dept (name, dept_id)
VALUES (2, 'B-商家组', '2');
INSERT INTO dept (name, dept_id)
VALUES (4, 'P-平台组', '4');
INSERT INTO dept (name, dept_id)
VALUES (6, 'C-导购组', '6');
INSERT INTO dept (name, dept_id)
VALUES (8, 'D-配送组', '8');

-- 初始化用户绑定部门表
INSERT INTO user_bind_dept (user_dept_id, user_id, dept_id, create_time, update_time)
VALUES ('01JJ616QJ47W75CZEV72YVY6HM', 'WangXiaoHao', '2', 1740758400000, 1740758400000);
INSERT INTO user_bind_dept (user_dept_id, user_id, dept_id, create_time, update_time)
VALUES ('01JJ6F2M8SXYG31JA5W3FDV0Z0', 'ShanDongDong', '6', 1740758400000, 1740758400000);

