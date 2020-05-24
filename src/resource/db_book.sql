/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.28-log : Database - db_book
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_book` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_book`;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;

/*Data for the table `administrator` */

LOCK TABLES `administrator` WRITE;

insert  into `administrator`(`id`,`password`) values
(123,'123');

UNLOCK TABLES;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '图书序号',
  `name` varchar(255) NOT NULL COMMENT '书名',
  `price` double DEFAULT NULL COMMENT '价格',
  `count` int(255) DEFAULT NULL COMMENT '图书总量',
  `type` varchar(255) DEFAULT NULL COMMENT '图书类型',
  `author` varchar(255) DEFAULT NULL COMMENT '图书作者',
  `discount` int(255) DEFAULT NULL COMMENT '总借阅次数',
  `hasLended` int(255) DEFAULT NULL COMMENT '已借出数量',
  `address` varchar(255) DEFAULT NULL COMMENT '藏书地点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `book` */

LOCK TABLES `book` WRITE;

insert  into `book`(`id`,`name`,`price`,`count`,`type`,`author`,`discount`,`hasLended`,`address`) values
(1,'C语言程序设计',40.5,29,'IT','何钦铭',3,0,'四楼'),
(2,'计算机网络',49,7,'IT','谢希仁',5,2,'四楼'),
(3,'Java面向对象程序设计',59,15,'IT','苏守宝',7,0,'五楼'),
(4,'计算机系统基础',59,16,'IT','袁春风',15,0,'六楼'),
(5,'悲惨世界',29.8,21,'文学','雨果',1,0,'二楼'),
(6,'瓦尔登湖',32.8,32,'文学','梭罗',7,1,'三楼'),
(7,'数据结构',35,45,'IT','严蔚敏',12,2,'四楼'),
(8,'金融学',56,8,'经济','博迪',1,1,'二楼'),
(9,'傲慢与偏见',28,14,'文学','简奥斯丁',11,1,'三楼'),
(10,'Python编程从入门到实践',89,6,'IT','Matthes',3,0,'四楼'),
(11,'数据库原理与应用',45,42,'IT','贾振华',8,0,'五楼'),
(12,'月亮与六便士',47.6,27,'文学','毛姆',15,1,'二楼'),
(13,'离散数学',33,72,'IT','耿素云',24,1,'三楼'),
(14,'人间失格',18.8,13,'文学','太宰治',4,1,'六楼'),
(15,'百年孤独',39.8,4,'文学','马尔加斯',3,0,'三楼'),
(16,'三体',93,17,'科幻','刘慈欣',9,0,'二楼');

UNLOCK TABLES;

/*Table structure for table `record` */

DROP TABLE IF EXISTS `record`;

CREATE TABLE `record` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '记录序号',
  `uid` int(255) DEFAULT NULL COMMENT '用户名',
  `bid` int(255) DEFAULT NULL COMMENT '图书序号',
  `lendTime` varchar(255) DEFAULT NULL COMMENT '借书时间',
  `returnTime` varchar(255) DEFAULT NULL COMMENT '还书时间',
  PRIMARY KEY (`id`),
  KEY `FK_uid` (`uid`),
  KEY `FK_bid` (`bid`),
  CONSTRAINT `FK_bid` FOREIGN KEY (`bid`) REFERENCES `book` (`id`),
  CONSTRAINT `FK_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `record` */

LOCK TABLES `record` WRITE;

insert  into `record`(`id`,`uid`,`bid`,`lendTime`,`returnTime`) values
(1,12345,7,'2020-05-19','2020-05-20'),
(2,1813041021,2,'2020-05-20','未还'),
(3,1813041021,5,'2020-05-02','2020-05-14'),
(4,1111,12,'2020-05-14','未还'),
(5,1111,14,'2020-04-25','2020-05-13'),
(6,2222,1,'2020-04-26','2020-05-20'),
(7,3333,6,'2020-02-16','未还'),
(8,12345,8,'2020-02-07','2020-02-25'),
(9,4444,13,'2020-04-30','未还'),
(10,4444,14,'2020-05-05','未还'),
(11,4444,9,'2020-05-16','未还'),
(12,5555,3,'2020-03-12','2020-05-04'),
(13,4444,8,'2020-05-15','未还'),
(14,2222,5,'2020-01-09','2020-01-10'),
(15,12345,7,'2020-05-22','未还'),
(16,3333,4,'2020-05-18','2020-05-26'),
(17,2222,2,'2020-05-22','未还');

UNLOCK TABLES;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(255) NOT NULL COMMENT '用户名',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

LOCK TABLES `user` WRITE;

insert  into `user`(`id`,`name`,`password`) values
(1111,'user1','1111'),
(2222,'user2','2222'),
(3333,'user3','3333'),
(4444,'user4','4444'),
(5555,'user5','5555'),
(6666,'user6','6666'),
(12345,'test','12345'),
(1813041021,'shl','123456');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
