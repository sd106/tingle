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
-- Table structure for table `FanMeeting_availableFanMeetingTypes`
--

DROP TABLE IF EXISTS `FanMeeting_availableFanMeetingTypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FanMeeting_availableFanMeetingTypes` (
  `FanMeeting_id` bigint NOT NULL,
  `availableFanMeetingTypes_id` bigint NOT NULL,
  KEY `FKoudp67u0om829nm5oluv5ogf6` (`availableFanMeetingTypes_id`),
  KEY `FKlka4u8at5htjjvh3uredreoqv` (`FanMeeting_id`),
  CONSTRAINT `FKlka4u8at5htjjvh3uredreoqv` FOREIGN KEY (`FanMeeting_id`) REFERENCES `FanMeeting` (`id`),
  CONSTRAINT `FKoudp67u0om829nm5oluv5ogf6` FOREIGN KEY (`availableFanMeetingTypes_id`) REFERENCES `FanMeetingType` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FanMeeting_availableFanMeetingTypes`
--

LOCK TABLES `FanMeeting_availableFanMeetingTypes` WRITE;
/*!40000 ALTER TABLE `FanMeeting_availableFanMeetingTypes` DISABLE KEYS */;
INSERT INTO `FanMeeting_availableFanMeetingTypes` VALUES (3,1),(3,2),(3,3),(4,1),(4,2),(5,1),(5,2),(5,3),(6,1),(7,1),(7,2),(8,1),(9,1),(9,2),(9,3),(10,1),(10,2),(10,3),(11,1),(12,2),(12,3),(13,2),(14,3),(15,1),(16,2),(17,2),(18,1),(18,2),(18,3),(19,1),(19,2),(19,3);
/*!40000 ALTER TABLE `FanMeeting_availableFanMeetingTypes` ENABLE KEYS */;
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

-- Dump completed on 2024-02-16 14:51:16
