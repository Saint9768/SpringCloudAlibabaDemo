/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 106.15.139.143:3306
 Source Schema         : content_center

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 21/08/2021 09:49:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mid_user_share
-- ----------------------------
DROP TABLE IF EXISTS `mid_user_share`;
CREATE TABLE `mid_user_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `share_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mid_user_share_user1_idx` (`user_id`),
  KEY `fk_mid_user_share_share1_idx` (`share_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of mid_user_share
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `show_flag` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for rocketmq_transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `rocketmq_transaction_log`;
CREATE TABLE `rocketmq_transaction_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `transaction_Id` varchar(45) NOT NULL COMMENT '事务ID',
  `log` varchar(45) NOT NULL COMMENT '日志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='RocketMQ事务日志表';

-- ----------------------------
-- Records of rocketmq_transaction_log
-- ----------------------------
BEGIN;
INSERT INTO `rocketmq_transaction_log` VALUES (1, '78135b56-18e1-44d8-83fa-3adfb1645c8d', '审核分享.....');
INSERT INTO `rocketmq_transaction_log` VALUES (2, 'fb898180-c74a-4f98-985e-34b9a1a4044b', '审核分享.....');
COMMIT;

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sharecol` varchar(45) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(80) NOT NULL COMMENT '标题',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_original` tinyint(1) DEFAULT NULL,
  `author` varchar(45) NOT NULL COMMENT '作者',
  `cover` varchar(256) NOT NULL COMMENT '封面',
  `summary` varchar(256) DEFAULT NULL COMMENT '概述',
  `price` int(11) DEFAULT NULL COMMENT '价格',
  `download_url` varchar(45) DEFAULT NULL COMMENT '下载地址',
  `buy_count` int(11) DEFAULT NULL COMMENT '购买次数',
  `show_flag` tinyint(1) DEFAULT NULL,
  `audit_status` varchar(10) DEFAULT NULL,
  `reason` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of share
-- ----------------------------
BEGIN;
INSERT INTO `share` VALUES (1, NULL, 1, 'Spring Cloud alibaba实战', '2021-06-29 18:34:44', '2021-06-29 18:34:44', NULL, '作者', 'xxx', NULL, 9, NULL, 1, NULL, 'NOT_YET', NULL);
INSERT INTO `share` VALUES (2, NULL, 1, 'Spring Cloud alibaba实战', '2021-06-29 18:34:48', '2021-06-29 18:34:48', NULL, '作者', 'xxx', NULL, 9, NULL, 1, NULL, 'NOT_YET', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
