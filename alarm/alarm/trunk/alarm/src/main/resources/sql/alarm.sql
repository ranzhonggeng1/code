/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : alarm

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2017-10-20 16:38:14
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `contact_id` VARCHAR(255) NOT NULL,
  `name`       VARCHAR(25) DEFAULT NULL,
  `email`      VARCHAR(40) DEFAULT NULL,
  `phone`      VARCHAR(11) DEFAULT NULL,
  `wechat`     VARCHAR(25) DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `contact_group`;
CREATE TABLE `contact_group` (
  `group_id`   VARCHAR(25) NOT NULL,
  `group_name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`group_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for group_contact
-- ----------------------------
DROP TABLE IF EXISTS `group_rel_contact`;
CREATE TABLE `group_rel_contact` (
  `group_id`   VARCHAR(25) NOT NULL,
  `contact_id` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`group_id`, `contact_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for receiver
-- ----------------------------
DROP TABLE IF EXISTS `receiver`;
CREATE TABLE `receiver` (
  `group_name` VARCHAR(25) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for alarm_resource
-- ----------------------------
DROP TABLE IF EXISTS `alarm_resource`;
CREATE TABLE `alarm_resource` (
  `id` VARCHAR(255) NOT NULL,
  `alarm_name` VARCHAR(255) NOT NULL,
  `component_name` VARCHAR(255) NOT NULL,
  `level` VARCHAR(255) NOT NULL,
  `host` VARCHAR(255) NOT NULL,
  `date_time` DATETIME(6) NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  `trace_stack` VARCHAR(255) NOT NULL,
  `exception` VARCHAR(255) NOT NULL,
  `status` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

  -- ----------------------------
  -- Table structure for optlog
  -- ----------------------------
  DROP TABLE IF EXISTS `optlog`;
  CREATE TABLE `alarm_resource` (
    `id` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `device_host` VARCHAR(255) NOT NULL,
    `service_host` VARCHAR(255) NOT NULL,
    `type` VARCHAR(255) NOT NULL,
    `date_time` DATETIME(6) NOT NULL,
    `user_name` VARCHAR(255) NOT NULL,
    `object_name` VARCHAR(255) NOT NULL,
    `object_id` VARCHAR(255) NOT NULL,
    `content` VARCHAR(255) NOT NULL,
    `result` VARCHAR(255) NOT NULL,
    `user_id` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
  )
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
