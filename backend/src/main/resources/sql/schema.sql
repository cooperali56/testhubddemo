-- 创建数据库
CREATE DATABASE IF NOT EXISTS testhub DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE testhub;

-- 用户表
CREATE TABLE IF NOT EXISTS `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `role` varchar(20) NOT NULL COMMENT '角色：ADMIN-管理员，TESTER-测试人员',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1-正常，0-禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 项目表
CREATE TABLE IF NOT EXISTS `tb_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  `description` varchar(500) DEFAULT NULL COMMENT '项目描述',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- 模块表
CREATE TABLE IF NOT EXISTS `tb_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模块ID',
  `name` varchar(100) NOT NULL COMMENT '模块名称',
  `description` varchar(500) DEFAULT NULL COMMENT '模块描述',
  `project_id` bigint(20) NOT NULL COMMENT '所属项目ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父模块ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模块表';

-- 测试用例表
CREATE TABLE IF NOT EXISTS `tb_test_case` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用例ID',
  `name` varchar(200) NOT NULL COMMENT '用例名称',
  `description` varchar(500) DEFAULT NULL COMMENT '用例描述',
  `precondition` varchar(500) DEFAULT NULL COMMENT '前置条件',
  `steps` text COMMENT '测试步骤',
  `expected_result` text COMMENT '预期结果',
  `actual_result` text COMMENT '实际结果',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-未开始，1-进行中，2-已完成，3-已失败，4-已跳过，5-已禁用',
  `priority` tinyint(1) NOT NULL DEFAULT '1' COMMENT '优先级：0-低，1-中，2-高',
  `module_id` bigint(20) NOT NULL COMMENT '所属模块ID',
  `project_id` bigint(20) NOT NULL COMMENT '所属项目ID',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者ID',
  `executor_id` bigint(20) DEFAULT NULL COMMENT '执行者ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_module_id` (`module_id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试用例表';

-- 测试计划表
CREATE TABLE IF NOT EXISTS `tb_test_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `name` varchar(100) NOT NULL COMMENT '计划名称',
  `description` varchar(500) DEFAULT NULL COMMENT '计划描述',
  `project_id` bigint(20) NOT NULL COMMENT '所属项目ID',
  `start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '计划结束时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-未开始，1-进行中，2-已完成，3-已暂停',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试计划表';

-- 计划测试用例关联表
CREATE TABLE IF NOT EXISTS `tb_plan_case` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `plan_id` bigint(20) NOT NULL COMMENT '计划ID',
  `case_id` bigint(20) NOT NULL COMMENT '用例ID',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-未开始，1-进行中，2-已完成，3-已失败，4-已跳过，5-已禁用',
  `executor_id` bigint(20) DEFAULT NULL COMMENT '执行者ID',
  `execute_time` datetime DEFAULT NULL COMMENT '执行时间',
  `result` text COMMENT '执行结果',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_plan_case` (`plan_id`,`case_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划测试用例关联表';

-- 初始化管理员用户，密码：admin123
INSERT INTO `tb_user` (`username`, `password`, `name`, `role`) 
VALUES ('admin', '$2a$10$ULg.7QmRt9jQlLYJvhUsxeW1cyP6WLpaH.qY4oZUJvRt4QZbIY28q', '管理员', 'ADMIN');

-- 初始化测试用户，密码：test123
INSERT INTO `tb_user` (`username`, `password`, `name`, `role`)
VALUES ('tester', '$2a$10$YqwG6tK47OvPvPx8a.1sleJf4bkw0IeWAr5S7MaRYkCVrlJU7M/Uy', '测试人员', 'TESTER');

-- 初始化5个随机测试用户，密码：123456
INSERT INTO `tb_user` (`username`, `password`, `name`, `role`, `email`, `phone`, `status`) 
VALUES 
('tester01', '$2a$10$PEi3qHBGIHlrwqWQc5K9NeXORR4SzbuuHT1Q0GhEiw0AWzsc5KTqa', '测试人员01', 'TESTER', 'tester01@example.com', '13800000001', 1),
('tester02', '$2a$10$PEi3qHBGIHlrwqWQc5K9NeXORR4SzbuuHT1Q0GhEiw0AWzsc5KTqa', '测试人员02', 'TESTER', 'tester02@example.com', '13800000002', 1),
('tester03', '$2a$10$PEi3qHBGIHlrwqWQc5K9NeXORR4SzbuuHT1Q0GhEiw0AWzsc5KTqa', '测试人员03', 'TESTER', 'tester03@example.com', '13800000003', 1),
('tester04', '$2a$10$PEi3qHBGIHlrwqWQc5K9NeXORR4SzbuuHT1Q0GhEiw0AWzsc5KTqa', '测试人员04', 'TESTER', 'tester04@example.com', '13800000004', 1),
('tester05', '$2a$10$PEi3qHBGIHlrwqWQc5K9NeXORR4SzbuuHT1Q0GhEiw0AWzsc5KTqa', '测试人员05', 'TESTER', 'tester05@example.com', '13800000005', 1); 