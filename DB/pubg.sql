SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS `pubgAnalysis`;
CREATE DATABASE `pubgAnalysis`;
USE `pubgAnalysis`;


-- ----------------------------
--  Table structure for `player`
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `accountid` varchar(255) 'pubgId',
  `name` varchar(32) DEFAULT NULL COMMENT 'pubgname',
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='玩家表';