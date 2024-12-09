-- -----------------------------------------------------
-- 初始化用户表
-- -----------------------------------------------------
INSERT INTO `user` (`user_id`, `name`, `email`, `password`, `status`, `create_time`, `update_time`)
VALUES ('Miller', '弥勒', 'miller.shan@aliyun.com', '123456', '0', '1632401032391', '1632401032391');
INSERT INTO `user` (`user_id`, `name`, `email`, `password`, `status`, `create_time`, `update_time`)
VALUES ('ShanDongDong', '单东东', 'shandongdong@126.com', '123456', '0', '1632401032392', '1632401032392');
INSERT INTO `user` (`user_id`, `name`, `email`, `password`, `status`, `create_time`, `update_time`)
VALUES ('admin', 'Administrator', 'admin@aliyun.com', '123456', '0', '1632401032393', '1632401032393');
INSERT INTO `user` (`user_id`, `name`, `email`, `password`, `status`, `create_time`, `update_time`)
VALUES ('Mila', '米拉', 'mila@aliyun.com', '123456', '0', '1632401032395', '1632401032395');

-- -----------------------------------------------------
-- 初始化角色表
-- -----------------------------------------------------
INSERT INTO `role` (`role_id`, `name`, `create_time`, `update_time`)
VALUES ('test', '测试人员', '1632400997208', '1632400997208');
INSERT INTO `role` (`role_id`, `name`, `create_time`, `update_time`)
VALUES ('dev', '开发人员', '1632401032394', '1632401032394');
INSERT INTO `role` (`role_id`, `name`, `create_time`, `update_time`)
VALUES ('po', '项目拥有者', '1632401032395', '1632401032395');
INSERT INTO `role` (`role_id`, `name`, `create_time`, `update_time`)
VALUES ('admin', '系统管理员', '1632401032396', '1632401032396');

-- -----------------------------------------------------
-- 初始化用户绑定角色表
-- -----------------------------------------------------
INSERT INTO `user_bind_role` (`user_role_id`, `user_id`, `role_id`, `create_time`, `update_time`)
VALUES ('6d86c757-0cef-4271-b01b-18f77a42dab1', 'Miller', 'dev', '1632401976882', '1632401976882');
INSERT INTO `user_bind_role` (`user_role_id`, `user_id`, `role_id`, `create_time`, `update_time`)
VALUES ('f94dbcf3-d527-4fa8-ba89-3a8533efad8d', 'ShanDongDong', 'test', '1632401976883', '1632401976883');
INSERT INTO `user_bind_role` (`user_role_id`, `user_id`, `role_id`, `create_time`, `update_time`)
VALUES ('06bf6259-7c09-49ed-b20d-677d567d80f2', 'Mila', 'po', '1632401976884', '1632401976884');
INSERT INTO `user_bind_role` (`user_role_id`, `user_id`, `role_id`, `create_time`, `update_time`)
VALUES ('7037fff7-547c-4a3f-a0f1-3ae4882a9e6f', 'admin', 'admin', '1632402241376', '1632402241376');


-- -----------------------------------------------------
-- 初始化项目表
-- -----------------------------------------------------
INSERT INTO `project` (`project_id`, `name`, `description`, `create_time`, `update_time`)
VALUES ('c0c42460-5945-464b-b8da-f869e979ca28', '分层自动化', '这是一个分层自动化示例项目', '1632401661904', '1632401661904');
INSERT INTO `project` (`project_id`, `name`, `description`, `create_time`, `update_time`)
VALUES ('0d2ba138-79b6-4d43-92ed-5067a0390d12', '演示项目', '分层自动化演示项目', '1632401748458', '1632401748458');

-- -----------------------------------------------------
-- 初始化项目表中添加用户
-- -----------------------------------------------------
INSERT INTO `user_bind_project` (`user_project_id`, `project_id`, `user_id`, `create_time`, `update_time`)
VALUES ('ff1a014e-5ed1-403c-96db-efc7b303b67d', 'c0c42460-5945-464b-b8da-f869e979ca28', 'admin', '1632402398358',
        '1632402398358');
INSERT INTO `user_bind_project` (`user_project_id`, `project_id`, `user_id`, `create_time`, `update_time`)
VALUES ('978f4876-8708-49bc-a248-22e3c3bb770b', 'c0c42460-5945-464b-b8da-f869e979ca28', 'Mila', '1632402398359',
        '1632402398359');
INSERT INTO `user_bind_project` (`user_project_id`, `project_id`, `user_id`, `create_time`, `update_time`)
VALUES ('b4c72b68-ce25-4fd5-8e09-fa8c25f97530', 'c0c42460-5945-464b-b8da-f869e979ca28', 'Miller', '1632402398360',
        '1632402398360');
INSERT INTO `user_bind_project` (`user_project_id`, `project_id`, `user_id`, `create_time`, `update_time`)
VALUES ('b55f4d65-1815-4482-bd7f-352ced83064c', 'c0c42460-5945-464b-b8da-f869e979ca28', 'ShanDongDong', '1632402398361',
        '1632402398361');

-- -----------------------------------------------------
-- 修改用户密码为使用MD5加密“邮箱+123456” 之后的密码
-- -----------------------------------------------------
UPDATE `user`
SET `password` = '4a21951e4325ac9e47a3e80f31faef3d'
WHERE (`user_id` = 'admin');
UPDATE `user`
SET `password` = '19544ffa464f45f834efc1262194ec20'
WHERE (`user_id` = 'Mila');
UPDATE `user`
SET `password` = '35f67d75e4b76a5457d3757cd06aa226'
WHERE (`user_id` = 'Miller');
UPDATE `user`
SET `password` = '1397773c2f9e4cdc7f41bfd29adbe426'
WHERE (`user_id` = 'ShanDongDong');


-- -----------------------------------------------------
-- 初始化权限表中添加权限
-- -----------------------------------------------------
INSERT INTO `permission` (`permission_id`, `permission_name`, `path`, `is_menu`, `parent_id`, `permission_code`)
VALUES ('e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a', '缺陷管理', '/issues', '1', '0', 'issues');
INSERT INTO `permission` (`permission_id`, `permission_name`, `path`, `is_menu`, `parent_id`, `permission_code`)
VALUES ('e71f3fe1-aa17-4aff-aa05-2b5a3a5c784b', '缺陷列表', '/issues/list', '1', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a',
        'issues:reports');
INSERT INTO `permission` (`permission_id`, `permission_name`, `path`, `is_menu`, `parent_id`, `permission_code`)
VALUES ('e71f3fe1-aa17-4aff-aa05-2b5a3a5c785a', '系统管理', '/manage', '1', '0', 'manage');
INSERT INTO `permission` (`permission_id`, `permission_name`, `path`, `is_menu`, `parent_id`, `permission_code`)
VALUES ('e71f3fe1-aa17-4aff-aa05-2b5a3a5c785b', '用户管理', '/manage/user', '1', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c785a',
        'manage:user');

-- -----------------------------------------------------
-- 初始化角色绑定的权限关联表数据
-- -----------------------------------------------------
INSERT INTO `role_bind_permission` (`role_id`, `permission_id`)
VALUES ('dev', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a');
INSERT INTO `role_bind_permission` (`role_id`, `permission_id`)
VALUES ('dev', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784b');
INSERT INTO `role_bind_permission` (`role_id`, `permission_id`)
VALUES ('test', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a');
INSERT INTO `role_bind_permission` (`role_id`, `permission_id`)
VALUES ('test', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784b');
INSERT INTO `role_bind_permission` (`role_id`, `permission_id`)
VALUES ('admin', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784a');
INSERT INTO `role_bind_permission` (`role_id`, `permission_id`)
VALUES ('admin', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c784b');
INSERT INTO `role_bind_permission` (`role_id`, `permission_id`)
VALUES ('admin', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c785a');
INSERT INTO `role_bind_permission` (`role_id`, `permission_id`)
VALUES ('admin', 'e71f3fe1-aa17-4aff-aa05-2b5a3a5c785b');

-- -----------------------------------------------------
-- 添加一条默认的缺陷数据
-- -----------------------------------------------------
INSERT INTO `issues` (`issue_id`, `title`, `description`, `status`, `create_time`, `update_time`, `creator`,
                               `update_user`, `project_id`, `handler`)
VALUES ('fca6f822-6f19-4472-b8d7-8c391e7a6bb6', '测试创建缺陷e81d63b3-8639-485f-aac7-5588a887cb93', '这是缺陷描述', '1',
        '1653979814417', '1653979814417', 'miller.shan@aliyun.com', 'miller.shan@aliyun.com',
        'c0c42460-5945-464b-b8da-f869e979ca28', 'miller.shan@aliyun.com');
