CREATE TABLE `test_case` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '数据id',
  `problem_id` int(10) NOT NULL COMMENT '对应题目id',
  `weight` int DEFAULT 0 COMMENT '权重',
  `input` mediumtext COMMENT '输入',
  `output` mediumtext COMMENT '输出',
  `deleted` bit DEFAULT 0 COMMENT '删除标记: 0 - 正常 1 - 删除',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `test_case_problem_id_index` (`problem_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='判题点';
