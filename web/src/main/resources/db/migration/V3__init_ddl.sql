-- -----------------------------------------------------
-- 用户表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;

CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`     VARCHAR(64)  NOT NULL COMMENT '用户ID' primary key,
    `name`        VARCHAR(64)  NULL,
    `email`       VARCHAR(64)  NULL,
    `password`    VARCHAR(256) NULL,
    `status`      VARCHAR(64)  NULL,
    `create_time` BIGINT(13)   NULL,
    `update_time` BIGINT(13)   NULL
    )
    COMMENT = '用户表';


-- -----------------------------------------------------
-- 角色表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role`;

CREATE TABLE IF NOT EXISTS `role`
(
    `role_id`     VARCHAR(64)  NOT NULL primary key,
    `name`        VARCHAR(64)  NULL,
    `description` VARCHAR(255) NULL COMMENT '角色描述',
    `create_time` BIGINT(13)   NULL,
    `update_time` BIGINT(13)   NULL
    )
    COMMENT = '角色表';


-- -----------------------------------------------------
-- 用户绑定的角色
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_bind_role`;

CREATE TABLE IF NOT EXISTS `user_bind_role`
(
    `user_role_id` VARCHAR(64) NOT NULL primary key,
    `user_id`      VARCHAR(64) NULL,
    `role_id`      VARCHAR(64) NULL,
    `create_time`  BIGINT(13)  NULL,
    `update_time`  BIGINT(13)  NULL
    )
    COMMENT = '用户绑定角色';


-- -----------------------------------------------------
-- 项目表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project`;

CREATE TABLE IF NOT EXISTS `project`
(
    `project_id`  VARCHAR(64)  NOT NULL primary key,
    `name`        VARCHAR(64)  NULL,
    `description` VARCHAR(255) NULL,
    `create_time` BIGINT(13)   NULL,
    `update_time` BIGINT(13)   NULL
    )
    COMMENT = '项目表';


-- -----------------------------------------------------
-- 用户绑定的项目表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_bind_project`;

CREATE TABLE IF NOT EXISTS `user_bind_project`
(
    `user_project_id` VARCHAR(64) NOT NULL primary key,
    `project_id`      VARCHAR(64) NULL,
    `user_id`         VARCHAR(64) NULL,
    `create_time`     BIGINT(13)  NULL,
    `update_time`     BIGINT(13)  NULL
    )
    COMMENT = '项目下的用户';


-- -----------------------------------------------------
-- 缺陷表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `issues`;

CREATE TABLE IF NOT EXISTS `issues`
(
    `issue_id`    VARCHAR(64)   NOT NULL primary key,
    `title`       VARCHAR(1024) NULL,
    `description` TEXT          NULL,
    `status`      VARCHAR(64)   NULL,
    `create_time` BIGINT(13)    NULL,
    `update_time` BIGINT(13)    NULL,
    `creator`     VARCHAR(64)   NULL,
    `update_user` VARCHAR(64)   NULL,
    `project_id`  VARCHAR(64)   NULL,
    `handler`     VARCHAR(64)   NULL COMMENT '缺陷处理人'
    )
    COMMENT = '缺陷表';

-- -----------------------------------------------------
-- 权限表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `permission`;

CREATE TABLE IF NOT EXISTS `permission`
(
    `permission_id`   VARCHAR(64)  NOT NULL primary key,
    `permission_name` VARCHAR(64)  NULL,
    `path`            VARCHAR(255) NULL,
    `is_menu`         VARCHAR(64)  NULL,
    `parent_id`       VARCHAR(64)  NULL,
    `permission_code` VARCHAR(64)  NULL
    )
    COMMENT = '权限表';

-- -----------------------------------------------------
-- 角色绑定的权限关联表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role_bind_permission`;

CREATE TABLE IF NOT EXISTS `role_bind_permission`
(
    `role_id`       VARCHAR(64) NOT NULL,
    `permission_id` VARCHAR(64) NOT NULL,
    primary key (role_id, permission_id)
    )
    COMMENT = '角色绑定的权限';