/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : test_case_frame

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-03-25 10:46:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for step_attr
-- ----------------------------
DROP TABLE IF EXISTS `step_attr`;
CREATE TABLE `step_attr` (
  `test_case_type` varchar(50) DEFAULT NULL COMMENT '������������',
  `test_case_title` varchar(50) DEFAULT NULL COMMENT '������������',
  `step_id` int(11) NOT NULL COMMENT '����id',
  `priority` varchar(50) DEFAULT NULL COMMENT '���ȼ�',
  `precondition` varchar(50) DEFAULT NULL COMMENT 'ǰ������',
  `manage_module` varchar(20) DEFAULT NULL COMMENT '??һ????ģ??id',
  `expected` varchar(50) DEFAULT NULL COMMENT 'Ԥ��',
  `applicable_stage` varchar(50) DEFAULT NULL COMMENT 'ʹ?ý׶?',
  PRIMARY KEY (`step_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for step_bind_module
-- ----------------------------
DROP TABLE IF EXISTS `step_bind_module`;
CREATE TABLE `step_bind_module` (
  `step_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  PRIMARY KEY (`step_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
-- Table structure for step_system
-- ----------------------------
DROP TABLE IF EXISTS `step_system`;
CREATE TABLE `step_system` (
  `system_id` int(3) NOT NULL,
  `system_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`system_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for step_system_module
-- ----------------------------
DROP TABLE IF EXISTS `step_system_module`;
CREATE TABLE `step_system_module` (
  `module_id` int(3) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `system_id` int(3) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test_case_step
-- ----------------------------
DROP TABLE IF EXISTS `test_case_step`;
CREATE TABLE `test_case_step` (
  `step_id` int(11) NOT NULL AUTO_INCREMENT,
  `step_name` varchar(50) DEFAULT NULL,
  `step_parent_id` int(11) DEFAULT '0',
  `single_step_mark_id` int(2) DEFAULT '0',
  `system_id` int(3) DEFAULT NULL,
  `last_step_mark_id` int(2) DEFAULT '0' COMMENT '最后一步步骤标记（0代表不是最后一步，1代表最后一步步骤）',
  `module_id` int(3) DEFAULT NULL,
  PRIMARY KEY (`step_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1990 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for GetCustomerLevel
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetCustomerLevel`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetCustomerLevel`(
    in  p_customerNumber int(11))
BEGIN
    DECLARE creditlim double;

    SELECT test_case_type INTO creditlim
    FROM customers
    WHERE step_id = p_customerNumber;

   

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_sql_test_case_type
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_sql_test_case_type`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_sql_test_case_type`(
    in  id int(11))
BEGIN
    DECLARE type double;

    SELECT test_case_type INTO type
    FROM customers
    WHERE step_id = id;


END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for queryChildrenStepList
-- ----------------------------
DROP FUNCTION IF EXISTS `queryChildrenStepList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `queryChildrenStepList`(stepId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp='$';
SET sTempChd = CAST(stepId AS CHAR);

WHILE sTempChd IS NOT NULL DO
SET sTemp= CONCAT(sTemp,',',sTempChd);
SELECT GROUP_CONCAT(step_id) INTO sTempChd FROM test_case_step WHERE FIND_IN_SET(step_parent_id,sTempChd)>0;
END WHILE;
RETURN sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for queryParentStepList
-- ----------------------------
DROP FUNCTION IF EXISTS `queryParentStepList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `queryParentStepList`(areaId INT) RETURNS mediumtext CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp='$';
SET sTempChd = CAST(areaId AS CHAR);
SET sTemp = CONCAT(sTemp,',',sTempChd);

SELECT step_parent_id INTO sTempChd FROM test_case_step WHERE step_id = sTempChd;
WHILE sTempChd <> 0 DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT step_parent_id INTO sTempChd FROM test_case_step WHERE step_id = sTempChd;
END WHILE;
RETURN sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for queryType
-- ----------------------------
DROP FUNCTION IF EXISTS `queryType`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `queryType`(stepId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp='$';
SET sTempChd = CAST(stepId AS CHAR);
SET sTemp = CONCAT(sTemp,',',sTempChd);

SELECT test_case_type INTO sTempChd FROM test_case_step WHERE step_id = sTempChd;
WHILE sTempChd IS NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT test_case_type INTO sTempChd FROM test_case_step WHERE step_id = sTempChd;
END WHILE;
RETURN sTempChd;
END
;;
DELIMITER ;
