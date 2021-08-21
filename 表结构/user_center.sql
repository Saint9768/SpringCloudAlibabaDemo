/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 106.15.139.143:3306
 Source Schema         : user_center

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 21/08/2021 09:49:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bonus_event_log
-- ----------------------------
DROP TABLE IF EXISTS `bonus_event_log`;
CREATE TABLE `bonus_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `event` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bonus_event_log_user_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of bonus_event_log
-- ----------------------------
BEGIN;
INSERT INTO `bonus_event_log` VALUES (1, '1', 50, 'contribute', '2021-07-12 08:28:53', '投稿。。。 加积分');
INSERT INTO `bonus_event_log` VALUES (8, '1', 50, 'contribute', '2021-07-13 17:40:28', '投稿。。。 加积分');
INSERT INTO `bonus_event_log` VALUES (9, '1', 50, 'contribute', '2021-07-13 18:17:18', '投稿。。。 加积分');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_id` varchar(64) DEFAULT NULL COMMENT '微信ID',
  `wx_nickname` varchar(64) DEFAULT NULL COMMENT '微信昵称',
  `roles` varchar(100) DEFAULT NULL COMMENT '角色',
  `avatar_url` varchar(255) NOT NULL COMMENT '头像地址',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `bonus` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'wxid1', 'Saint', 'admin', 'http://baidu.com', '2021-06-29 18:18:58', '2021-06-29 18:18:58', 250);
INSERT INTO `user` VALUES (2, 'wxid2', 'Saint sister', 'user', 'xxx', '2021-06-29 18:19:02', '2021-06-29 18:19:02', 100);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
