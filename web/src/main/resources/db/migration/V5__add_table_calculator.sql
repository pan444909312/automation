-- -----------------------------------------------------
-- 计算器Demo测试表及测试数据
-- -----------------------------------------------------
DROP TABLE IF EXISTS `calculator`;

CREATE TABLE IF NOT EXISTS `calculator`
(
    id            INT    NOT NULL AUTO_INCREMENT primary key,
    first_number  DOUBLE NULL,
    second_number DOUBLE NULL,
    result        DOUBLE NULL
)  COMMENT ='计算器测试表';
INSERT INTO `calculator` (`id`, `first_number`, `second_number`, `result`)
VALUES ('1', '1.0', '2.0', '3.0');
INSERT INTO `calculator` (`id`, `first_number`, `second_number`, `result`)
VALUES ('2', '2.0', '3.0', '5.0');