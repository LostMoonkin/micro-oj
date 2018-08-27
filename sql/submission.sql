CREATE TABLE `submission` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `problem_id` int(10) NOT NULL COMMENT '对应问题id',
  `user_id` int(10) NOT NULL COMMENT '对应用户id',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `language` tinyint(4) NOT NULL COMMENT '编程语言',
  `code` text NOT NULL COMMENT '代码',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `submission_id_uindex` (`id`),
  KEY `submission_user_id_index` (`user_id`),
  KEY `submission_problem_id_index` (`problem_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;