CREATE TABLE `problem` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(128) NOT NULL COMMENT '标题',
  `content` varchar(512) NOT NULL COMMENT '内容',
  `smaple_input` varchar(256) DEFAULT NULL COMMENT '输入样例',
  `sample_output` varchar(256) DEFAULT NULL COMMENT '输出样例',
  `source` varchar(32) DEFAULT NULL COMMENT '题目来源',
  `time_limit` int DEFAULT 1000 COMMENT '限时(MS)',
  `memory_limit` int DEFAULT 256 COMMENT '内存限制(MB)',
  `stack_limit` int DEFAULT 64 COMMENT '栈堆限制(MB)',
  `user_id` int(10) NOT NULL COMMENT '上传用户id',
  `spj` bit DEFAULT 0 COMMENT '0 - normal 1 - spj',
  `deleted` bit DEFAULT 0 COMMENT '删除标记: 0 - 正常 1 - 删除',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `problem_id_uindex` (`id`),
  UNIQUE KEY `problem_title_uindex` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目表';