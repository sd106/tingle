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
-- Table structure for table `SnapShotTag`
--

DROP TABLE IF EXISTS `SnapShotTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SnapShotTag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hashtag_id` bigint DEFAULT NULL,
  `snapshot_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnwfdwbu9e4amer5hy6m75e07f` (`hashtag_id`),
  KEY `FKey1acdo2ucmaun4vy67plq0ld` (`snapshot_id`),
  CONSTRAINT `FKey1acdo2ucmaun4vy67plq0ld` FOREIGN KEY (`snapshot_id`) REFERENCES `SnapShotEntity` (`snapshot_id`),
  CONSTRAINT `FKnwfdwbu9e4amer5hy6m75e07f` FOREIGN KEY (`hashtag_id`) REFERENCES `HashTagEntity` (`hashtag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SnapShotTag`
--

LOCK TABLES `SnapShotTag` WRITE;
/*!40000 ALTER TABLE `SnapShotTag` DISABLE KEYS */;
INSERT INTO `SnapShotTag` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(8,8,6),(9,6,6),(10,9,7),(11,6,7),(12,10,8),(13,6,8),(14,11,9),(15,6,9),(16,12,10),(17,13,10),(18,6,10),(19,14,11),(20,6,11),(21,15,12),(22,16,12),(23,6,12),(24,17,13),(25,6,13),(26,18,14),(27,6,14),(28,15,14),(29,19,15),(30,6,15),(31,20,16),(32,5,16),(33,8,17),(34,6,17),(35,21,18),(36,22,18),(37,23,18),(38,24,19),(39,10,20),(40,6,20),(41,9,21),(42,11,22),(43,14,23),(44,6,23),(45,6,24),(46,18,25),(47,19,26),(48,25,27),(49,26,27);
/*!40000 ALTER TABLE `SnapShotTag` ENABLE KEYS */;
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

-- Dump completed on 2024-02-16 14:51:14
