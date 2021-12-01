/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : zhazongchat

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2021-12-01 19:42:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zha_information
-- ----------------------------
DROP TABLE IF EXISTS `zha_information`;
CREATE TABLE `zha_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hostername` varchar(255) DEFAULT NULL,
  `friendname` varchar(255) DEFAULT NULL,
  `content` text,
  `senddatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zha_information
-- ----------------------------
INSERT INTO `zha_information` VALUES ('1', 'ccc', 'aa(10002)', '2021-11-29 16:35:09——ccc说:\naa\n', null);
INSERT INTO `zha_information` VALUES ('2', 'aa', 'ccc(10004)', '2021-11-29 16:35:16——aa说:\n啊啊啊\n', null);
INSERT INTO `zha_information` VALUES ('3', 'ccc', 'aa(10002)', '2021-11-29 16:35:18——ccc说:\n试试\n', null);
INSERT INTO `zha_information` VALUES ('4', 'aa', 'ccc(10004)', '2021-11-29 16:45:48——aa说:\naa\n', null);

-- ----------------------------
-- Table structure for zha_user
-- ----------------------------
DROP TABLE IF EXISTS `zha_user`;
CREATE TABLE `zha_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `pwd` blob NOT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `isonline` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10028 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zha_user
-- ----------------------------
INSERT INTO `zha_user` VALUES ('10001', 'admin', 0x35628323D1, 'img/q2.png', '0');
INSERT INTO `zha_user` VALUES ('10002', 'aa', 0x35A4, 'img/q1.png', '0');
INSERT INTO `zha_user` VALUES ('10003', 'qqq', 0x0D8CCD, 'img/q3.png', '0');
INSERT INTO `zha_user` VALUES ('10004', 'ccc', 0xD84B18, 'img/q4.png', '0');
INSERT INTO `zha_user` VALUES ('10005', 'qwe', 0x0DA32A, 'img/q3.png', '0');
INSERT INTO `zha_user` VALUES ('10006', 'ff', 0xBE28, 'img/q1.png', '0');
INSERT INTO `zha_user` VALUES ('10007', 'zz', 0x64EE, 'img/q3.png', '0');
INSERT INTO `zha_user` VALUES ('10008', 'mm', 0x46DB, 'img/q1.png', '0');
INSERT INTO `zha_user` VALUES ('10009', 'll', 0xE67A, 'img/q1.png', '0');
INSERT INTO `zha_user` VALUES ('10010', 'kk', 0x118A, 'img/q1.png', '0');
INSERT INTO `zha_user` VALUES ('10011', 'test', 0xC9685775, 'img/q4.png', '0');
INSERT INTO `zha_user` VALUES ('10012', '', '', 'img/q1.png', '0');
INSERT INTO `zha_user` VALUES ('10013', 'ww', 0x22A5, 'img/q1.png', null);
INSERT INTO `zha_user` VALUES ('10014', '11', 0x70B1, 'img/q2.png', null);
INSERT INTO `zha_user` VALUES ('10015', 'ffff', 0xBE287E06, 'img/q4.png', null);
INSERT INTO `zha_user` VALUES ('10016', 'rr', 0xB634, 'img/q4.png', null);
INSERT INTO `zha_user` VALUES ('10017', 'ss', 0x8605, 'img/q1.png', null);
INSERT INTO `zha_user` VALUES ('10018', 'gg', 0x43D4, 'img/q1.png', null);
INSERT INTO `zha_user` VALUES ('10019', 'tt', 0xC94D, 'img/q3.png', null);
INSERT INTO `zha_user` VALUES ('10020', '44', 0xAB6F, 'img/q1.png', null);
INSERT INTO `zha_user` VALUES ('10021', 'hh', 0x73EB, 'img/q1.png', null);
INSERT INTO `zha_user` VALUES ('10022', '555', 0x945154, 'img/q3.png', null);
INSERT INTO `zha_user` VALUES ('10023', 'asd', 0x351721, 'img/q3.png', null);
INSERT INTO `zha_user` VALUES ('10024', '..', 0xD608, 'img/q1.png', null);

-- ----------------------------
-- Procedure structure for get_user_list
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_user_list`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_list`(in my_friendname varchar(255))
BEGIN
select max(t1.id),t1.username,max(profile),max(isonline),if(sum(if(t2.friendname = my_friendname,1,0))>0,1,0)
from zha_user t1 left join zha_information t2 on t1.username = t2.hostername group by t1.username;
end
;;
DELIMITER ;
