CREATE DATABASE  IF NOT EXISTS `cinemaboot_field` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cinemaboot_field`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: cinemaboot_field
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `field`
--

DROP TABLE IF EXISTS `field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `movie_id` int NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='场次信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field`
--

LOCK TABLES `field` WRITE;
/*!40000 ALTER TABLE `field` DISABLE KEYS */;
INSERT INTO `field` VALUES (1,'1号厅',1,'2020-05-31 09:30:00','2020-05-31 11:30:00',50.00),(2,'1号厅',2,'2020-05-31 12:30:00','2020-05-31 14:30:00',60.00),(3,'1号厅',3,'2020-05-31 15:30:00','2020-05-31 17:30:00',40.00),(4,'2号厅',1,'2020-06-03 10:20:00','2020-06-03 12:25:00',60.00),(5,'2号厅',2,'2020-06-01 08:30:00','2020-06-01 10:30:00',70.00),(6,'2号厅',3,'2020-06-01 13:30:00','2020-06-01 15:30:00',40.00),(7,'3号厅',3,'2021-06-06 19:30:00','2021-06-06 21:30:00',50.00);
/*!40000 ALTER TABLE `field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat`
--

DROP TABLE IF EXISTS `seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `field_id` int NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='座位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat`
--

LOCK TABLES `seat` WRITE;
/*!40000 ALTER TABLE `seat` DISABLE KEYS */;
INSERT INTO `seat` VALUES (1,1,'1',0),(2,1,'2',0),(3,1,'3',0),(4,1,'4',0),(5,1,'5',0),(6,1,'6',0),(7,1,'7',0),(8,1,'8',0),(9,1,'9',0),(10,1,'10',0),(11,2,'1',0),(12,2,'2',0),(13,2,'3',0),(14,2,'4',0),(15,2,'5',0),(16,2,'6',0),(17,2,'7',0),(18,2,'8',0),(19,2,'9',0),(20,2,'10',0),(21,3,'1',0),(22,3,'2',0),(23,3,'3',0),(24,3,'4',0),(25,3,'5',0),(26,3,'6',0),(27,3,'7',0),(28,3,'8',0),(29,3,'9',0),(30,3,'10',0),(31,4,'1',1),(32,4,'2',0),(33,4,'3',1),(34,4,'4',1),(35,4,'5',1),(36,4,'6',1),(37,4,'7',1),(38,4,'8',1),(39,4,'9',1),(40,4,'10',1),(41,5,'1',0),(42,5,'2',0),(43,5,'3',0),(44,5,'4',0),(45,5,'5',0),(46,5,'6',0),(47,5,'7',0),(48,5,'8',0),(49,5,'9',0),(50,5,'10',0),(51,6,'1',0),(52,6,'2',0),(53,6,'3',0),(54,6,'4',0),(55,6,'5',0),(56,6,'6',0),(57,6,'7',0),(58,6,'8',0),(59,6,'9',0),(60,6,'10',0),(62,7,'1',1),(63,7,'2',1),(64,7,'3',1),(65,7,'4',1),(66,7,'5',1),(67,7,'6',1),(68,7,'7',1),(69,7,'8',1),(70,7,'9',1),(71,7,'10',1),(72,7,'11',1),(73,7,'12',1),(74,7,'13',1),(75,7,'14',1),(76,7,'15',1),(77,7,'16',1),(78,7,'17',1),(79,7,'18',1),(80,7,'19',1),(81,7,'20',1),(82,7,'21',1),(83,7,'22',1),(84,7,'23',1),(85,7,'24',1),(86,7,'25',1),(87,7,'26',1),(88,7,'27',1),(89,7,'28',1),(90,7,'29',1),(91,7,'30',1),(92,7,'31',1),(93,7,'32',1),(94,7,'33',1),(95,7,'34',1),(96,7,'35',1),(97,7,'36',1),(98,7,'37',1),(99,7,'38',1),(100,7,'39',1),(101,7,'40',1),(102,7,'41',1),(103,7,'42',1),(104,7,'43',1),(105,7,'44',1),(106,7,'45',1),(107,7,'46',1),(108,7,'47',1),(109,7,'48',1),(110,7,'49',1),(111,7,'50',1),(112,7,'51',1),(113,7,'52',1),(114,7,'53',1),(115,7,'54',1),(116,7,'55',0),(117,7,'56',0),(118,7,'57',0),(119,7,'58',0),(120,7,'59',0);
/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_log`
--

DROP TABLE IF EXISTS `seat_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `field_id` int NOT NULL,
  `seat_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='座位流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_log`
--

LOCK TABLES `seat_log` WRITE;
/*!40000 ALTER TABLE `seat_log` DISABLE KEYS */;
INSERT INTO `seat_log` VALUES (1,'2020060316092104391',7,'1','confirmed'),(2,'2020060316092813632',7,'2','confirmed'),(3,'2020060316165391044',7,'3','confirmed'),(4,'2020060316165822953',7,'4','confirmed'),(5,'2020060316434141786',7,'7','confirmed'),(6,'2020060316434695207',7,'5','confirmed'),(7,'2020060316435582837',7,'6','confirmed'),(8,'2020060316461096624',7,'8','confirmed'),(9,'2020060316462580075',7,'9','confirmed'),(10,'2020060316470672765',7,'10','confirmed'),(11,'2020060317160549625',7,'11','confirmed'),(12,'2020060317233236518',7,'11','confirmed'),(13,'2020060317320234990',7,'14','confirmed'),(14,'2020060317343070339',7,'11','confirmed'),(15,'2020060317394660940',7,'12','confirmed'),(16,'2020060317395551337',7,'13','confirmed'),(17,'2020060317400418630',7,'15','confirmed'),(18,'2020060321360526800',7,'16','confirmed'),(19,'2020060321361043904',7,'17','confirmed'),(20,'2020060321362654292',7,'18','confirmed'),(21,'2020060321414856430',7,'19','confirmed'),(22,'2020060321453722319',7,'20','confirmed'),(23,'2020060321582169549',7,'21','confirmed'),(24,'2020060322050928590',7,'22','confirmed'),(25,'2020060322053472906',7,'23','confirmed'),(26,'2020060522305590403',7,'24','confirmed'),(27,'2020060522484826837',7,'25','confirmed'),(28,'2020060522501847431',7,'26','confirmed'),(29,'2020060522503051737',7,'27','cancel'),(30,'2020060522513093531',7,'27','cancel'),(31,'2020060522542594751',7,'27','cancel'),(32,'2020060522542895639',7,'27','cancel'),(33,'2020060522543190867',7,'27','cancel'),(34,'2020060522545156837',7,'27','confirmed'),(35,'2020060522545705239',7,'28','confirmed'),(36,'2020060522550257771',7,'29','confirmed'),(37,'2020060522564633970',7,'30','confirmed'),(38,'2020060608373901908',7,'31','confirmed'),(39,'2020060608412288040',7,'32','confirmed'),(40,'2020060608412844347',7,'33','confirmed'),(41,'2020060608414543683',7,'34','confirmed'),(42,'2020060608503956854',7,'35','confirmed'),(43,'2020060609043916375',7,'36','confirmed'),(44,'2020060609252104483',7,'37','confirmed'),(45,'2020060609252532186',7,'38','confirmed'),(46,'2020060609252971068',7,'39','confirmed'),(47,'2020060610575978127',7,'40','draft'),(48,'2020060611030226214',7,'41','draft'),(49,'2020060611434989851',7,'42','draft'),(50,'2020060614375489262',7,'43','confirmed'),(51,'2020060614381915645',7,'44','confirmed'),(52,'2020060614465813239',7,'45','confirmed'),(53,'2020060614481068261',7,'46','confirmed'),(54,'2020060616241677712',7,'47','confirmed'),(55,'2020060616242542614',7,'48','confirmed'),(56,'2020060616243153145',7,'49','confirmed'),(57,'2020060616251085262',7,'50','cancel'),(58,'2020060616270318793',7,'50','confirmed'),(59,'2020060616271292735',7,'51','cancel'),(60,'2020060616271754271',7,'51','cancel'),(61,'2020060616300002217',7,'51','confirmed'),(62,'2020060616303625503',7,'52','cancel'),(63,'2020060616550691435',7,'52','confirmed'),(64,'2020060616554145090',7,'53','cancel'),(65,'2020060616574623524',7,'53','confirmed'),(66,'2020060617001944670',7,'54','confirmed');
/*!40000 ALTER TABLE `seat_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-06 17:08:07
CREATE DATABASE  IF NOT EXISTS `cinemaboot_order` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cinemaboot_order`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: cinemaboot_order
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_info` (
  `id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `user_id` int NOT NULL,
  `movie_id` int NOT NULL,
  `field_id` int NOT NULL,
  `seat_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `cost` decimal(10,2) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` VALUES ('2020060316092104391',5,3,7,'1',50.00,'2020-06-03 00:09:21','confirmed'),('2020060316092813632',5,3,7,'2',50.00,'2020-06-03 00:09:28','confirmed'),('2020060316165391044',5,3,7,'3',50.00,'2020-06-03 00:16:54','confirmed'),('2020060316165822953',5,3,7,'4',50.00,'2020-06-03 00:16:59','confirmed'),('2020060316434141786',2,3,7,'7',50.00,'2020-06-03 00:43:41','confirmed'),('2020060316434695207',2,3,7,'5',50.00,'2020-06-03 00:43:46','confirmed'),('2020060316435582837',2,3,7,'6',50.00,'2020-06-03 00:43:56','confirmed'),('2020060316461096624',2,3,7,'8',50.00,'2020-06-03 00:46:10','confirmed'),('2020060316462580075',2,3,7,'9',50.00,'2020-06-03 00:46:25','confirmed'),('2020060316470672765',2,3,7,'10',50.00,'2020-06-03 00:47:07','confirmed'),('2020060317160549625',2,3,7,'11',50.00,'2020-06-03 01:16:06','confirmed'),('2020060317233236518',2,3,7,'11',50.00,'2020-06-03 01:23:33','confirmed'),('2020060317320234990',2,3,7,'14',50.00,'2020-06-03 01:32:03','confirmed'),('2020060317343070339',2,3,7,'11',50.00,'2020-06-03 01:34:30','confirmed'),('2020060317394660940',2,3,7,'12',50.00,'2020-06-03 01:39:46','confirmed'),('2020060317395551337',2,3,7,'13',50.00,'2020-06-03 01:39:55','confirmed'),('2020060317400418630',2,3,7,'15',50.00,'2020-06-03 01:40:04','confirmed'),('2020060321360526800',2,3,7,'16',50.00,'2020-06-03 05:36:05','confirmed'),('2020060321361043904',2,3,7,'17',50.00,'2020-06-03 05:36:11','confirmed'),('2020060321362654292',2,3,7,'18',50.00,'2020-06-03 05:36:26','confirmed'),('2020060321414856430',2,3,7,'19',50.00,'2020-06-03 05:41:48','confirmed'),('2020060321453722319',2,3,7,'20',50.00,'2020-06-03 05:45:38','confirmed'),('2020060321582169549',2,3,7,'21',50.00,'2020-06-03 05:58:22','confirmed'),('2020060322050928590',2,3,7,'22',50.00,'2020-06-03 06:05:09','confirmed'),('2020060322053472906',2,3,7,'23',50.00,'2020-06-03 06:05:35','confirmed'),('2020060522305590403',2,3,7,'24',50.00,'2020-06-05 06:30:55','confirmed'),('2020060522484826837',1,3,7,'25',50.00,'2020-06-05 06:48:49','confirmed'),('2020060522501847431',1,3,7,'26',50.00,'2020-06-05 06:50:19','confirmed'),('2020060522503051737',1,3,7,'27',50.00,'2020-06-05 06:50:31','cancel'),('2020060522513093531',1,3,7,'27',50.00,'2020-06-05 06:51:30','cancel'),('2020060522542594751',1,3,7,'27',50.00,'2020-06-05 06:54:25','cancel'),('2020060522542895639',1,3,7,'27',50.00,'2020-06-05 06:54:29','cancel'),('2020060522543190867',1,3,7,'27',50.00,'2020-06-05 06:54:31','cancel'),('2020060522545156837',2,3,7,'27',50.00,'2020-06-05 06:54:52','confirmed'),('2020060522545705239',2,3,7,'28',50.00,'2020-06-05 06:54:57','confirmed'),('2020060522550257771',2,3,7,'29',50.00,'2020-06-05 06:55:03','confirmed'),('2020060522564633970',2,3,7,'30',50.00,'2020-06-05 06:56:47','confirmed'),('2020060608373901908',2,3,7,'31',50.00,'2020-06-05 16:37:40','confirmed'),('2020060608412288040',2,3,7,'32',50.00,'2020-06-05 16:41:22','confirmed'),('2020060608412844347',2,3,7,'33',50.00,'2020-06-05 16:41:28','confirmed'),('2020060608414543683',2,3,7,'34',50.00,'2020-06-05 16:41:46','confirmed'),('2020060608503956854',2,3,7,'35',50.00,'2020-06-05 16:50:39','confirmed'),('2020060609043916375',2,3,7,'36',50.00,'2020-06-05 17:04:40','confirmed'),('2020060609252104483',2,3,7,'37',50.00,'2020-06-05 17:25:21','confirmed'),('2020060609252532186',2,3,7,'38',50.00,'2020-06-05 17:25:25','confirmed'),('2020060609252971068',2,3,7,'39',50.00,'2020-06-05 17:25:30','confirmed'),('2020060610575978127',2,3,7,'40',50.00,'2020-06-05 18:58:00','paying'),('2020060611030226214',2,3,7,'41',50.00,'2020-06-05 19:03:03','paying'),('2020060611434989851',2,3,7,'42',50.00,'2020-06-05 19:43:50','paying'),('2020060614375489262',2,3,7,'43',50.00,'2020-06-05 22:37:55','confirmed'),('2020060614381915645',2,3,7,'44',50.00,'2020-06-05 22:38:19','confirmed'),('2020060614465813239',2,3,7,'45',50.00,'2020-06-05 22:46:58','confirmed'),('2020060614481068261',2,3,7,'46',50.00,'2020-06-05 22:48:10','confirmed'),('2020060616241677712',2,3,7,'47',50.00,'2020-06-06 00:24:16','confirmed'),('2020060616242542614',2,3,7,'48',50.00,'2020-06-06 00:24:25','confirmed'),('2020060616243153145',2,3,7,'49',50.00,'2020-06-06 00:24:32','confirmed'),('2020060616251085262',1,3,7,'50',50.00,'2020-06-06 00:25:11','cancel'),('2020060616270318793',1,3,7,'50',50.00,'2020-06-06 00:27:04','confirmed'),('2020060616271292735',1,3,7,'51',50.00,'2020-06-06 00:27:13','cancel'),('2020060616271754271',1,3,7,'51',50.00,'2020-06-06 00:27:17','cancel'),('2020060616300002217',1,3,7,'51',50.00,'2020-06-06 00:30:00','confirmed'),('2020060616303625503',1,3,7,'52',50.00,'2020-06-06 00:30:36','cancel'),('2020060616550691435',1,3,7,'52',50.00,'2020-06-06 08:55:06','confirmed'),('2020060616554145090',1,3,7,'53',50.00,'2020-06-06 08:55:41','cancel'),('2020060616574623524',1,3,7,'53',50.00,'2020-06-06 08:57:47','confirmed'),('2020060617001944670',1,3,7,'54',50.00,'2020-06-06 09:00:19','confirmed');
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-06 17:08:08
CREATE DATABASE  IF NOT EXISTS `cinemaboot_movie` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cinemaboot_movie`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: cinemaboot_movie
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `director` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `actors` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `detail` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `img_url` varchar(800) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `sales` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='电影信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'唐人街探案','陈思诚','王宝强、刘昊然','该片讲述了两名华人在泰国陷入了刑案风暴，在短短三天内找到下落不明的黄金及查明杀人真凶，洗刷冤屈的故事。','http://p4.img.cctvpic.com/photoworkspace/contentimg/2020/01/20/2020012009025730598.png',1121),(2,'西游记之大圣归来','田晓鹏','张磊、张欣','本片改编自明朝小说家吴承恩的小说《西游记》，设定于孙悟空“大闹天宫”五百年之后，围绕着小和尚江流儿与孙悟空等人展开：江流儿因盲打误撞地解除封印，将压在五行山下的孙悟空救出，而此时孙悟空只想过平静的生活，只因封印所限才打算护送江流儿回城，最终在拯救被妖王绑架女孩的过程中，孙悟空解破封印，恢复“齐天大圣”之身。','https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1479694995,2525906423&fm=26&gp=0.jpg',101),(3,'战狼','吴京','吴京、余男','在一次与公安部联合捣毁制毒窝点的行动中，冷锋违反命令将国际犯罪组织的骨干武吉狙杀，导致被关禁闭，后得到战狼中队指挥官龙小云赏识，保释他并让他加入战狼中队参与军事演习。','https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=803768292,3750057323&fm=26&gp=0.jpg',257);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_log`
--

DROP TABLE IF EXISTS `sales_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `movie_id` int NOT NULL,
  `sale` int NOT NULL,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销量流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_log`
--

LOCK TABLES `sales_log` WRITE;
/*!40000 ALTER TABLE `sales_log` DISABLE KEYS */;
INSERT INTO `sales_log` VALUES (1,'2020060316092104391',3,1,'confirmed'),(2,'2020060316092813632',3,1,'confirmed'),(3,'2020060316165391044',3,1,'confirmed'),(4,'2020060316165822953',3,1,'confirmed'),(5,'2020060316434141786',3,1,'confirmed'),(6,'2020060316434695207',3,1,'confirmed'),(7,'2020060316435582837',3,1,'confirmed'),(8,'2020060316461096624',3,1,'confirmed'),(9,'2020060316462580075',3,1,'confirmed'),(10,'2020060316470672765',3,1,'confirmed'),(11,'2020060317160549625',3,1,'confirmed'),(12,'2020060317233236518',3,1,'confirmed'),(13,'2020060317320234990',3,1,'confirmed'),(14,'2020060317343070339',3,1,'confirmed'),(15,'2020060317394660940',3,1,'confirmed'),(16,'2020060317395551337',3,1,'confirmed'),(17,'2020060317400418630',3,1,'confirmed'),(18,'2020060321360526800',3,1,'confirmed'),(19,'2020060321361043904',3,1,'confirmed'),(20,'2020060321362654292',3,1,'confirmed'),(21,'2020060321414856430',3,1,'confirmed'),(22,'2020060321453722319',3,1,'confirmed'),(23,'2020060321582169549',3,1,'confirmed'),(24,'2020060322050928590',3,1,'confirmed'),(25,'2020060322053472906',3,1,'confirmed'),(26,'2020060522305590403',3,1,'confirmed'),(27,'2020060522484826837',3,1,'confirmed'),(28,'2020060522501847431',3,1,'confirmed'),(29,'2020060522545156837',3,1,'confirmed'),(30,'2020060522545705239',3,1,'confirmed'),(31,'2020060522550257771',3,1,'confirmed'),(32,'2020060522564633970',3,1,'confirmed'),(33,'2020060608373901908',3,1,'confirmed'),(34,'2020060608412288040',3,1,'confirmed'),(35,'2020060608412844347',3,1,'confirmed'),(36,'2020060608414543683',3,1,'confirmed'),(37,'2020060608503956854',3,1,'confirmed'),(38,'2020060609043916375',3,1,'confirmed'),(39,'2020060609252104483',3,1,'confirmed'),(40,'2020060609252532186',3,1,'confirmed'),(41,'2020060609252971068',3,1,'confirmed'),(42,'2020060610575978127',3,1,'draft'),(43,'2020060611030226214',3,1,'draft'),(44,'2020060611434989851',3,1,'draft'),(45,'2020060614375489262',3,1,'confirmed'),(46,'2020060614381915645',3,1,'confirmed'),(47,'2020060614465813239',3,1,'confirmed'),(48,'2020060614481068261',3,1,'confirmed'),(49,'2020060616241677712',3,1,'confirmed'),(50,'2020060616242542614',3,1,'confirmed'),(51,'2020060616243153145',3,1,'confirmed'),(52,'2020060616270318793',3,1,'confirmed'),(53,'2020060616300002217',3,1,'confirmed'),(54,'2020060616550691435',3,1,'confirmed'),(55,'2020060616574623524',3,1,'confirmed'),(56,'2020060617001944670',3,1,'confirmed');
/*!40000 ALTER TABLE `sales_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-06 17:08:08
CREATE DATABASE  IF NOT EXISTS `cinemaboot_user` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cinemaboot_user`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: cinemaboot_user
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `telephone` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `wallet` decimal(10,2) NOT NULL DEFAULT '1000.00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`username`,`telephone`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','4QrcOUm6Wau+VuBX8g+IPg==','18580558663',70.00),(2,'www','4QrcOUm6Wau+VuBX8g+IPg==','12311111111',9936490.00),(3,'ddd','4QrcOUm6Wau+VuBX8g+IPg==','13997863436',3000.00),(4,'飞翔的企鹅','bETlzRfwAZxksELkp0VBKg==','13215976358',4000.00),(5,'hello','4QrcOUm6Wau+VuBX8g+IPg==','14512354651',800.00);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_log`
--

DROP TABLE IF EXISTS `wallet_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `user_id` int NOT NULL,
  `wallet` decimal(10,2) NOT NULL,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='钱包流水信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_log`
--

LOCK TABLES `wallet_log` WRITE;
/*!40000 ALTER TABLE `wallet_log` DISABLE KEYS */;
INSERT INTO `wallet_log` VALUES (1,'2020060316092104391',5,50.00,'confirmed'),(2,'2020060316092813632',5,50.00,'confirmed'),(3,'2020060316165391044',5,50.00,'confirmed'),(4,'2020060316165822953',5,50.00,'confirmed'),(5,'2020060316434141786',2,50.00,'confirmed'),(6,'2020060316434695207',2,50.00,'confirmed'),(7,'2020060316435582837',2,50.00,'confirmed'),(8,'2020060316461096624',2,50.00,'confirmed'),(9,'2020060316462580075',2,50.00,'confirmed'),(10,'2020060316470672765',2,50.00,'confirmed'),(11,'2020060317160549625',2,50.00,'confirmed'),(12,'2020060317233236518',2,50.00,'confirmed'),(13,'2020060317320234990',2,50.00,'confirmed'),(14,'2020060317343070339',2,50.00,'confirmed'),(15,'2020060317394660940',2,50.00,'confirmed'),(16,'2020060317395551337',2,50.00,'confirmed'),(17,'2020060317400418630',2,50.00,'confirmed'),(18,'2020060321360526800',2,50.00,'confirmed'),(19,'2020060321361043904',2,50.00,'confirmed'),(20,'2020060321362654292',2,50.00,'confirmed'),(21,'2020060321414856430',2,50.00,'confirmed'),(22,'2020060321453722319',2,50.00,'confirmed'),(23,'2020060321582169549',2,50.00,'confirmed'),(24,'2020060322050928590',2,50.00,'confirmed'),(25,'2020060322053472906',2,50.00,'confirmed'),(26,'2020060522305590403',2,50.00,'confirmed'),(27,'2020060522484826837',1,50.00,'confirmed'),(28,'2020060522501847431',1,50.00,'confirmed'),(34,'2020060522545156837',2,50.00,'confirmed'),(35,'2020060522545705239',2,50.00,'confirmed'),(36,'2020060522550257771',2,50.00,'confirmed'),(37,'2020060522564633970',2,50.00,'confirmed'),(38,'2020060608373901908',2,50.00,'confirmed'),(39,'2020060608412288040',2,50.00,'confirmed'),(40,'2020060608412844347',2,50.00,'confirmed'),(41,'2020060608414543683',2,50.00,'confirmed'),(42,'2020060608503956854',2,50.00,'confirmed'),(43,'2020060609043916375',2,50.00,'confirmed'),(44,'2020060609252104483',2,50.00,'confirmed'),(45,'2020060609252532186',2,50.00,'confirmed'),(46,'2020060609252971068',2,50.00,'confirmed'),(47,'2020060610575978127',2,50.00,'draft'),(48,'2020060611030226214',2,50.00,'draft'),(49,'2020060611434989851',2,50.00,'draft'),(50,'2020060614375489262',2,50.00,'confirmed'),(51,'2020060614381915645',2,50.00,'confirmed'),(52,'2020060614465813239',2,50.00,'confirmed'),(53,'2020060614481068261',2,50.00,'confirmed'),(54,'2020060616241677712',2,50.00,'confirmed'),(55,'2020060616242542614',2,50.00,'confirmed'),(56,'2020060616243153145',2,50.00,'confirmed'),(58,'2020060616251085262',1,50.00,'cancel'),(59,'2020060616270318793',1,50.00,'confirmed'),(61,'2020060616271292735',1,50.00,'cancel'),(63,'2020060616271754271',1,50.00,'cancel'),(64,'2020060616300002217',1,50.00,'confirmed'),(66,'2020060616303625503',1,50.00,'cancel'),(67,'2020060616550691435',1,50.00,'confirmed'),(69,'2020060616554145090',1,50.00,'cancel'),(70,'2020060616574623524',1,50.00,'confirmed'),(71,'2020060617001944670',1,50.00,'confirmed');
/*!40000 ALTER TABLE `wallet_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-06 17:08:08
