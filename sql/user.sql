SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `role` tinyint(4) NOT NULL DEFAULT '0' COMMENT '权限 -1 - ban 0 - 普通用户',
  `gender` tinyint(4) DEFAULT '0' COMMENT '未知 0 男 - 1 女 - 2',
  `description` varchar(128) DEFAULT NULL COMMENT '简介',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记 0 - 未删除 1 - 删除',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`),
  UNIQUE KEY `user_user_name_uindex` (`user_name`),
  UNIQUE KEY `user_email_uindex` (`email`),
  UNIQUE KEY `user_phone_uindex` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', 'root@moonkin.cc', '$2a$10$G6EWq39k9OQimLnBlMIZ0.669XGzKR50naFNbYd7tuOJqrf/DHO2m', '123456', '99', '0', null, '\0', '2018-08-10 10:10:26', '2018-08-10 10:10:36');
