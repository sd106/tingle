-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: database-2.cdtjyhbp1uqs.ap-northeast-2.rds.amazonaws.com    Database: tingle
-- ------------------------------------------------------
-- Server version	8.0.35

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `star`
--

DROP TABLE IF EXISTS `star`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `star` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `banner` varchar(255) DEFAULT NULL,
  `category` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `providerId` varchar(255) DEFAULT NULL,
  `snsUrl` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lllfkrwtxixxs114xh8976230` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `star`
--

LOCK TABLES `star` WRITE;
/*!40000 ALTER TABLE `star` DISABLE KEYS */;
INSERT INTO `star` VALUES (1,NULL,1,'cksghkd1234@naver.com','황찬준',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/111-20240215181606.jpg','google',NULL,'','황준찬'),(2,NULL,0,'cksghkd1234@naver.com','황찬준',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/20210922113927.1754942-20240215191454.jpg','naver',NULL,NULL,'리신'),(3,NULL,2,'dbswjddud100@naver.com','윤정영',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%A7%84-20240215223512.jpg','google',NULL,'','인의동스턴건'),(4,NULL,0,'dltjdah25@gmail.com','JIWAN LEE',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/download-20240216005402.png','google',NULL,NULL,'삼성모'),(5,NULL,2,'cksghkd1234@gmail.com','황찬준',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/download-20240215200953.jpg','google',NULL,'','키다리우스'),(6,NULL,0,'erropp88@gmail.com','구미 _4반_오은아',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/ham-20240215215847.jpeg','google',NULL,NULL,'은아'),(7,NULL,3,'dbswjddud100@naver.com','윤정영',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%9D%B4%EC%B9%98%EA%B3%A0%202-20240215223854.PNG','naver',NULL,'','효목동불주먹'),(8,NULL,0,'37jsbean@gmail.com','구미_1반_정수빈',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%A6%84%EC%9D%B4%20%281%29-20240215234429.jpg','google',NULL,NULL,'웩'),(9,NULL,0,'37jsbean@naver.com','정수빈',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','naver',NULL,NULL,'웩웩'),(10,NULL,0,'hmy940424@gmail.com','유형민',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/thumb-81bd96a33b45d95ef888ccd362a18ae1_1692342941_3364_820x680-20240216003911.jpg','google',NULL,'','유씨입니다'),(11,NULL,0,'hmy940424@naver.com','유형민',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/asdf-20240216005330.jfif','naver',NULL,NULL,'나는나비'),(12,NULL,0,'dltjdah25@naver.com','이성모',NULL,'https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%A0%9C%EB%AA%A9%20%EC%97%86%EC%9D%8C-20240216010009.png','naver',NULL,NULL,'정수');
/*!40000 ALTER TABLE `star` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-16 14:51:17
