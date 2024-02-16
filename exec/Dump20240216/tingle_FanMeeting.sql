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
-- Table structure for table `FanMeeting`
--

DROP TABLE IF EXISTS `FanMeeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FanMeeting` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capacity` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fanMeetingStartAt` datetime(6) DEFAULT NULL,
  `imgURL1` varchar(255) DEFAULT NULL,
  `imgURL2` varchar(255) DEFAULT NULL,
  `isFinished` bit(1) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `ticketingEndAt` datetime(6) DEFAULT NULL,
  `ticketingStartAt` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `star_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrg9s7gm714at22hrimslgdgdd` (`star_id`),
  CONSTRAINT `FKrg9s7gm714at22hrimslgdgdd` FOREIGN KEY (`star_id`) REFERENCES `star` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FanMeeting`
--

LOCK TABLES `FanMeeting` WRITE;
/*!40000 ALTER TABLE `FanMeeting` DISABLE KEYS */;
INSERT INTO `FanMeeting` VALUES (1,3,'나의 1차 1차 팬미팅!','2024-02-16 12:17:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/111-20240215181547.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/boat-20240215181547.gif',_binary '\0',100,'2024-02-16 12:17:00.000000','2024-02-16 12:15:00.000000','1차 팬미팅',1),(2,3,'나의 첫 팬미팅!','2024-02-16 12:18:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/boat-20240215181710.gif','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/anchor-20240215181711.gif',_binary '\0',4,'2024-02-16 12:18:00.000000','2024-02-16 12:16:00.000000','안녕하세요~~',1),(3,3,'스턴건 맛 좀 볼텨?','2024-02-16 03:37:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/favourite1-20240215183614.gif','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/boat-logo1-20240215183614.gif',_binary '',10000,'2024-02-16 03:37:00.000000','2024-02-16 03:36:00.000000','제1회 인동스턴건 팬미팅',3),(4,2,'ㅇㅇㅁ','2024-02-16 03:59:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/image-removebg-preview-20240215185816.png','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/boat-20240215185816.gif',_binary '',3,'2024-02-16 03:59:00.000000','2024-02-16 03:57:00.000000','ㄹㅇ류',1),(5,2,'ㅁㄴㅇㄹ','2024-02-16 05:14:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/download-20240215201301.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/2-20240215201301.txt',_binary '\0',1245124,'2024-02-16 05:14:00.000000','2024-02-16 05:12:00.000000','ㅁㄴㅇㄹ',5),(6,20,'다음주 금요일 팬미팅 엽니다','2024-02-23 14:00:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/3-20240215224235.PNG','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/2-20240215224235.PNG',_binary '\0',1000,'2024-02-23 09:00:00.000000','2024-02-16 10:00:00.000000','은아랑 놀아요!!',6),(7,3,'111','2024-02-16 09:25:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-birthday-20240216002336.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-life4cut-20240216002336.jpg',_binary '',1000,'2024-02-16 09:25:00.000000','2024-02-16 09:23:00.000000','1회 팬미팅',7),(8,5,'통합 테스트','2024-02-16 09:29:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-life4cut-20240216002902.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-birthday-20240216002902.jpg',_binary '',1000,'2024-02-16 09:29:00.000000','2024-02-16 09:28:00.000000','새로 만들어요',3),(9,3,'랄로와 함께','2024-02-16 09:44:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-product-20240216004251.png','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/basic-profile-20240216004252.png',_binary '',3,'2024-02-16 09:44:00.000000','2024-02-16 09:42:00.000000','랄로와 함께',10),(10,5,'첫 팬미팅 잘 부탁드립니다!','2024-02-16 10:02:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-birthday-20240216005958.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-free-20240216005958.jpg',_binary '',5000,'2024-02-16 10:02:00.000000','2024-02-16 10:00:00.000000','제1회 스턴건 팬미팅',3),(11,11,'1111111','2024-02-16 10:06:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A0%88%EC%98%A4-20240216010511.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A6%AC%EB%A1%9C%EC%9D%B4_%EC%8A%A4%EB%AF%B8%EC%8A%A4-20240216010512.jpg',_binary '',1111,'2024-02-16 10:06:00.000000','2024-02-16 10:04:00.000000','11111',3),(12,4,'123123','2024-02-16 10:11:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A0%88%EC%9D%B4%EB%B8%90-20240216010957.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%9E%90%EB%A7%88_%EC%95%84%EC%8A%A4%EC%B9%B4-20240216010957.jpg',_binary '',11111,'2024-02-16 10:11:00.000000','2024-02-16 10:09:00.000000','1312',3),(13,1,'11111','2024-02-16 10:15:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A0%88%EC%9D%B4%EB%B8%90-20240216011421.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%8B%88%EB%82%98_%EC%9C%8C%EB%A6%AC%EC%97%84%EC%8A%A4-20240216011421.jpg',_binary '',111,'2024-02-16 10:15:00.000000','2024-02-16 10:14:00.000000','111111',3),(14,1,'22222','2024-02-16 10:17:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A6%B4%EB%A6%AC-20240216011641.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%8B%88%EB%82%98_%EC%9C%8C%EB%A6%AC%EC%97%84%EC%8A%A4-20240216011641.jpg',_binary '',22222,'2024-02-16 10:17:00.000000','2024-02-16 10:16:00.000000','12222',3),(15,3,'333','2024-02-16 10:19:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A0%88%EC%9D%B4%EB%B8%90-20240216011813.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%9E%90%EB%A7%88_%EC%A4%80-20240216011813.jpg',_binary '',11133,'2024-02-16 10:19:00.000000','2024-02-16 10:17:00.000000','3333',3),(16,3,'첫 미팅! 잘 부탁드립니다','2024-02-16 10:40:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-life4cut-20240216012347.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-life4cut-20240216012347.jpg',_binary '',5000,'2024-02-16 10:40:00.000000','2024-02-16 10:25:00.000000','제 1회 스턴건 팬미팅',3),(17,1,'스턴건 팬미팅 잘 부탁드립니다!','2024-02-16 10:45:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-life4cut-20240216013223.jpg','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/meeting-free-20240216013223.jpg',_binary '',10000,'2024-02-16 10:45:00.000000','2024-02-16 10:31:00.000000','제1회 팬미팅입니다',3),(18,3,'땅고르기','2024-02-18 10:45:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/favourite-20240216014203.gif','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/image-removebg-preview-20240216014203.png',_binary '\0',2000,'2024-02-17 10:45:00.000000','2024-02-16 10:45:00.000000','1회 팬미팅',1),(19,3,'땅고르기','2024-02-18 10:45:00.000000','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/favourite-20240216014209.gif','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/image-removebg-preview-20240216014209.png',_binary '\0',2000,'2024-02-17 10:45:00.000000','2024-02-16 10:45:00.000000','1회 팬미팅',1);
/*!40000 ALTER TABLE `FanMeeting` ENABLE KEYS */;
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
