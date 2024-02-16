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
-- Table structure for table `CommentEntity`
--

DROP TABLE IF EXISTS `CommentEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CommentEntity` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `context` varchar(255) DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `isStar` bit(1) DEFAULT NULL,
  `updatedAt` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `snapshot_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKlkbsff1w4ag48g5iyqf7uoqdr` (`snapshot_id`),
  CONSTRAINT `FKlkbsff1w4ag48g5iyqf7uoqdr` FOREIGN KEY (`snapshot_id`) REFERENCES `SnapShotEntity` (`snapshot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentEntity`
--

LOCK TABLES `CommentEntity` WRITE;
/*!40000 ALTER TABLE `CommentEntity` DISABLE KEYS */;
INSERT INTO `CommentEntity` VALUES (2,'소세지 맛있어요~!!','2024-02-16 07:27:28.157579',_binary '\0','2024-02-16 07:27:28.157579','은아',4),(3,'진격거 좋아하시나요? 소통해요~','2024-02-16 07:27:53.337072',_binary '\0','2024-02-16 07:27:53.337072','은아',1),(4,'오 캐릭터 예쁘네요','2024-02-16 07:30:10.343325',_binary '\0','2024-02-16 07:30:10.343325','은아',7),(5,'멋지네요!!','2024-02-16 07:30:50.611580',_binary '\0','2024-02-16 07:30:50.611580','은아',10),(7,'건치미소 멋지세요!!','2024-02-16 09:51:53.985007',_binary '\0','2024-02-16 09:51:53.985007','은아',19),(8,'와 감사합니다!!','2024-02-16 10:39:52.518142',_binary '','2024-02-16 10:39:52.518142','황준찬',19);
/*!40000 ALTER TABLE `CommentEntity` ENABLE KEYS */;
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

-- Dump completed on 2024-02-16 14:51:12
