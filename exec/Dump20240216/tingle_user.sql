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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `providerId` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'cksghkd1234@naver.com','황찬준','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/111-20240216013031.jpg','google',NULL,'황준찬'),(2,'cksghkd1234@naver.com','황찬준','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','naver',NULL,'리신'),(3,'dbswjddud100@naver.com','윤정영','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','google',NULL,'인의동스턴건'),(4,'dltjdah25@gmail.com','JIWAN LEE','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/download-20240215201028.png','google',NULL,'삼성모'),(5,'cksghkd1234@gmail.com','황찬준','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','google',NULL,'키다리우스'),(6,'erropp88@gmail.com','구미 _4반_오은아','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','google',NULL,'은아'),(7,'dbswjddud100@naver.com','윤정영','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','naver',NULL,'효목동불주먹'),(8,'37jsbean@gmail.com','구미_1반_정수빈','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C-20240215234526.jpg','google',NULL,'웩'),(9,'37jsbean@naver.com','정수빈','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','naver',NULL,'웩웩'),(10,'hmy940424@gmail.com','유형민','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','google',NULL,'유씨입니다'),(11,'hmy940424@naver.com','유형민','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','naver',NULL,'나는나비'),(12,'dltjdah25@naver.com','이성모','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile.png','naver',NULL,'정수');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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

-- Dump completed on 2024-02-16 14:51:05
