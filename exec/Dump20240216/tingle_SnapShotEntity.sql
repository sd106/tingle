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
-- Table structure for table `SnapShotEntity`
--

DROP TABLE IF EXISTS `SnapShotEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SnapShotEntity` (
  `snapshot_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  `isStarLike` bit(1) NOT NULL,
  `updatedAt` datetime(6) DEFAULT NULL,
  `star_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`snapshot_id`),
  KEY `FKqsvc0w1n93jh4vpg7phn4e757` (`star_id`),
  KEY `FKf0c4al6d9m8oer29n60li8jpw` (`user_id`),
  CONSTRAINT `FKf0c4al6d9m8oer29n60li8jpw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqsvc0w1n93jh4vpg7phn4e757` FOREIGN KEY (`star_id`) REFERENCES `star` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SnapShotEntity`
--

LOCK TABLES `SnapShotEntity` WRITE;
/*!40000 ALTER TABLE `SnapShotEntity` DISABLE KEYS */;
INSERT INTO `SnapShotEntity` VALUES (1,'귀여운 사진~','2024-02-16 03:14:11.151449','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/111_20240215181410.jpg',_binary '\0','2024-02-16 03:14:11.151449',2,2),(4,'소세지','2024-02-16 07:25:27.362812','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/11_20240215222527.PNG',_binary '\0','2024-02-16 07:25:27.362812',2,6),(6,'철권8 진 멋있다!','2024-02-16 07:29:11.920489','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%A7%84_20240215222911.jpg',_binary '\0','2024-02-16 07:29:11.920489',3,3),(7,'릴리 ','2024-02-16 07:29:28.539344','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A6%B4%EB%A6%AC_20240215222928.jpg',_binary '\0','2024-02-16 07:29:28.539344',3,3),(8,'링링링','2024-02-16 07:29:43.819148','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A7%81_20240215222943.jpg',_binary '\0','2024-02-16 07:29:43.819148',3,3),(9,'레이븐','2024-02-16 07:29:56.931789','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A0%88%EC%9D%B4%EB%B8%90_20240215222956.jpg',_binary '\0','2024-02-16 07:29:56.931789',3,3),(10,'사오유 어떤가요','2024-02-16 07:30:40.998648','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A6%AC%EB%A1%9C%EC%9D%B4_%EC%8A%A4%EB%AF%B8%EC%8A%A4_20240215223040.jpg',_binary '\0','2024-02-16 07:30:40.998648',3,3),(11,'레오가 진리','2024-02-16 07:31:29.096933','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A0%88%EC%98%A4_20240215223128.jpg',_binary '\0','2024-02-16 07:31:29.096933',3,3),(12,'아!스!카','2024-02-16 07:32:14.463736','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%9E%90%EB%A7%88_%EC%95%84%EC%8A%A4%EC%B9%B4_20240215223214.jpg',_binary '\0','2024-02-16 07:32:14.463736',3,3),(13,'니나 좋아요','2024-02-16 07:32:36.071487','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%8B%88%EB%82%98_%EC%9C%8C%EB%A6%AC%EC%97%84%EC%8A%A4_20240215223235.jpg',_binary '\0','2024-02-16 07:32:36.071487',3,3),(14,'카자마준','2024-02-16 07:33:08.660433','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%9E%90%EB%A7%88_%EC%A4%80_20240215223308.jpg',_binary '\0','2024-02-16 07:33:08.660433',3,3),(15,'카즈야 버려?','2024-02-16 07:33:27.344247','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%A6%88%EC%95%BC_20240215223327.jpg',_binary '\0','2024-02-16 07:33:27.344247',3,3),(16,'드라구..뭐시기?','2024-02-16 07:34:15.675569','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%93%9C%EB%9D%BC%EA%B5%AC%EB%85%B8%ED%94%84_20240215223415.jpg',_binary '\0','2024-02-16 07:34:15.675569',3,3),(17,'진 그려볼게요','2024-02-16 09:40:34.910699','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%A7%84_20240216004034.jpg',_binary '\0','2024-02-16 09:40:34.910699',7,7),(18,'멋진 모습','2024-02-16 09:45:05.229603','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/download_20240216004505.jpg',_binary '\0','2024-02-16 09:45:05.229603',1,4),(19,'옆모습','2024-02-16 09:46:09.614763','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/download_20240216004609.jpg',_binary '\0','2024-02-16 09:46:09.614763',1,4),(20,'링 그림 봐줘요!','2024-02-16 09:52:28.868562','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A7%81_20240216005228.jpg',_binary '\0','2024-02-16 09:52:28.868562',7,3),(21,'릴리펀치!','2024-02-16 09:52:49.503717','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A6%B4%EB%A6%AC_20240216005249.jpg',_binary '\0','2024-02-16 09:52:49.503717',7,3),(22,'ㄹㅇㅂ!!!','2024-02-16 09:53:19.524820','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A0%88%EC%9D%B4%EB%B8%90_20240216005319.jpg',_binary '\0','2024-02-16 09:53:19.524820',7,3),(23,'이거 그려주세여!','2024-02-16 09:53:32.592916','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A0%88%EC%98%A4_20240216005332.jpg',_binary '\0','2024-02-16 09:53:32.592916',7,3),(24,'아스카 그려줘..!','2024-02-16 09:53:54.652042','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%9E%90%EB%A7%88_%EC%95%84%EC%8A%A4%EC%B9%B4_20240216005354.jpg',_binary '\0','2024-02-16 09:53:54.652042',7,3),(25,'생방때 나온 그림','2024-02-16 09:54:22.779889','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%9E%90%EB%A7%88_%EC%A4%80_20240216005422.jpg',_binary '\0','2024-02-16 09:54:22.779889',7,3),(26,'제가 그린 카즈야 평가해주세요!','2024-02-16 09:54:47.633543','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%A6%88%EC%95%BC_20240216005447.jpg',_binary '\0','2024-02-16 09:54:47.633543',7,3),(27,'룰루로해버려','2024-02-16 09:55:08.924390','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/asdf_20240216005508.jfif',_binary '','2024-02-16 10:03:46.968728',3,10),(28,'루시안','2024-02-16 09:56:32.893586','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%EB%A3%A8%EC%8B%9C%EC%95%88_20240216005632.jpg',_binary '\0','2024-02-16 09:56:32.893586',3,10),(29,'','2024-02-16 10:10:41.287731','https://tingle-s3.s3.ap-northeast-2.amazonaws.com/%ED%95%98%ED%8A%B8_20240216011041.jpg',_binary '\0','2024-02-16 10:10:41.287731',6,6);
/*!40000 ALTER TABLE `SnapShotEntity` ENABLE KEYS */;
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

-- Dump completed on 2024-02-16 14:51:13
