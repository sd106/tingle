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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `isRealMember` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `socialId` varchar(255) DEFAULT NULL,
  `socialType` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gc3jmn7c2abyo3wf6syln5t2i` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'cksghkd1234@naver.com',_binary '','황찬준',NULL,'103841711837987474416','google',NULL),(2,'cksghkd1234@naver.com',_binary '','황찬준',NULL,'Jky_3IcVw860bQbE4OHqAWAM7PmBSrF_RQILbVOsNx8','naver',NULL),(3,'dbswjddud100@naver.com',_binary '','윤정영',NULL,'104219284500233690019','google',NULL),(4,'dltjdah25@gmail.com',_binary '','JIWAN LEE',NULL,'109030295944537687649','google',NULL),(5,'cksghkd1234@gmail.com',_binary '','황찬준',NULL,'107880952137052565128','google',NULL),(6,'erropp88@gmail.com',_binary '','구미 _4반_오은아',NULL,'105745765916345471109','google',NULL),(7,'dbswjddud100@naver.com',_binary '','윤정영',NULL,'MkLUrsY1ZiWNHo85E7mzKPKFkzOl1ttf0G7-aZP3UYs','naver',NULL),(8,'37jsbean@gmail.com',_binary '','구미_1반_정수빈',NULL,'102238745954562187505','google',NULL),(9,'37jsbean@naver.com',_binary '','정수빈',NULL,'I2H02hfA-l5ktKukYz6NulJlrb8Bh13V1yfFs4nHg2M','naver',NULL),(10,'hmy940424@gmail.com',_binary '','유형민',NULL,'105361585730160190172','google',NULL),(11,'hmy940424@naver.com',_binary '','유형민',NULL,'QOwyEJZhdcIMifYMh887lrrLUX_ccwMur6NMyCwHkE4','naver',NULL),(12,'dltjdah25@naver.com',_binary '','이성모',NULL,'7hatsU7mFhzZkLOttpG3u5IgG-8sIszaMFsSt626Zyk','naver',NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
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

-- Dump completed on 2024-02-16 14:51:04
