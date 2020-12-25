/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : cs

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 25/12/2020 21:54:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
BEGIN;
INSERT INTO `admin_user` VALUES (1, 'admin', '21232f297a57a5a743894ae4a801fc3');
COMMIT;

-- ----------------------------
-- Table structure for progress
-- ----------------------------
DROP TABLE IF EXISTS `progress`;
CREATE TABLE `progress` (
  `id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `stu_id` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`),
  KEY `stu_id` (`stu_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `class` varchar(255) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `birthday` date DEFAULT '2020-10-01',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `category` varchar(255) DEFAULT NULL,
  `t_id` int NOT NULL COMMENT '教师id',
  PRIMARY KEY (`id`),
  KEY `t_id` (`t_id`),
  CONSTRAINT `t_id` FOREIGN KEY (`t_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` varchar(8) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` int NOT NULL AUTO_INCREMENT,
  `captain` int DEFAULT NULL,
  `member1` int DEFAULT NULL,
  `member2` int DEFAULT NULL,
  `su_id` int DEFAULT NULL,
  `approved` tinyint(1) DEFAULT '0',
  `score` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `captain` (`captain`),
  KEY `member1` (`member1`),
  KEY `member2` (`member2`),
  KEY `su_id` (`su_id`),
  CONSTRAINT `captain` FOREIGN KEY (`captain`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `member1` FOREIGN KEY (`member1`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `member2` FOREIGN KEY (`member2`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `su_id` FOREIGN KEY (`su_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `team_chk_1` CHECK (((`score` >= 0) and (`score` <= 100)))
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
