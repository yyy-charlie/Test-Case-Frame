/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : test_case_frame

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-04-23 16:17:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for step_module
-- ----------------------------
DROP TABLE IF EXISTS `step_module`;
CREATE TABLE `step_module` (
  `module_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ģ??id',
  `module_name` varchar(50) DEFAULT NULL COMMENT 'ģ������',
  `first_step_id` int(11) DEFAULT NULL COMMENT '??һ???Ĳ???id',
  `system_id` int(2) DEFAULT NULL,
  `system_module_id` int(2) DEFAULT NULL COMMENT '系统模块id',
  PRIMARY KEY (`module_id`)
) ENGINE=MyISAM AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of step_module
-- ----------------------------
INSERT INTO `step_module` VALUES ('68', '2312', '1792', '1', '1');
INSERT INTO `step_module` VALUES ('64', '开通', '1734', '1', '2');
INSERT INTO `step_module` VALUES ('66', '登录', '1761', '1', '2');
INSERT INTO `step_module` VALUES ('80', '权限组编码后续步骤', '1988', '2', '4');
