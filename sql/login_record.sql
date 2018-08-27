SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for login_record
-- ----------------------------
DROP TABLE IF EXISTS `login_record`;
CREATE TABLE `login_record` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `user_id` int(10) NOT NULL COMMENT '对应用户id',
  `ip` varchar(46) DEFAULT NULL COMMENT '登录ip',
  `status` tinyint NOT NULL COMMENT '登录状态: 0 - 成功,1 - 失败',
  `user_agent` varchar(256) DEFAULT NULL COMMENT '对应浏览器ua',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `login_record_user_id_index` (`user_id`),
  KEY `login_record_ip_index` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录记录';

-- ----------------------------
-- Records of login_record
-- ----------------------------
