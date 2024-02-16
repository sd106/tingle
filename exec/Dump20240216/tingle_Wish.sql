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
-- Table structure for table `Wish`
--

DROP TABLE IF EXISTS `Wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Wish` (
  `wish_id` bigint NOT NULL AUTO_INCREMENT,
  `contents` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `delete_time` datetime(6) DEFAULT NULL,
  `liked_count` int DEFAULT NULL,
  `points` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `star_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`wish_id`),
  KEY `FK86bdi4ku05gn7j95mhcx670tc` (`star_id`),
  KEY `FK6ddalmrlqbcovpv7l8cmquf76` (`user_id`),
  CONSTRAINT `FK6ddalmrlqbcovpv7l8cmquf76` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK86bdi4ku05gn7j95mhcx670tc` FOREIGN KEY (`star_id`) REFERENCES `star` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Wish`
--

LOCK TABLES `Wish` WRITE;
/*!40000 ALTER TABLE `Wish` DISABLE KEYS */;
INSERT INTO `Wish` VALUES (1,'로또1등기원','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',2,100073999,1,3,3),(2,'귀여운 햄스터랑 같이 팬미팅해주세요!!!','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,100,1,6,6),(3,'민초 좋아하시나요?? 알려주세요!!','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,20,0,6,6),(6,'저 다음주 생일인데 팬미팅 열어주시면 안되나요?? 꼭 좀 부탁드려요','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,500,1,6,6),(7,'제일 좋아하는 음식이 뭐예요??','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,100,0,6,6),(8,'스토어에 굿즈 출시해주세요!!','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,100,2,6,6),(9,'제 이름 기억해주세요! 제 이름은 햄찌입니당','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,50,0,6,6),(10,'커여운 햄스터 보여주세요ㅎㅎ','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,300,2,6,6),(11,'리신이랑 직접 만나서 리신 싸인받아주세요!!','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,1000,0,2,2),(12,'리신 소시지 출시해주세요!! 잔뜩 기대중입니다','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',1,100,0,2,2),(13,'소시지 존맛탱구리 추천해주세요','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',1,30,0,2,2),(14,'리신님 프로필 보니까 배고파요 대신 먹방해주세요','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,70,0,2,2),(15,'운동하는 모습이 멋지세요~\n스토어에 운동굿즈 있으면 사고싶네요ㅎㅎ','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',1,100,0,3,3),(16,'집에서 10분만에 가능한 홈트 가르쳐주세요!!','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',2,4200,0,3,3),(17,'격투기 싸움도 가능하세요?','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',2,7300,0,3,3),(18,'삼성에 다니시나요??','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,200,0,4,4),(19,'성모 마리아 별명 가져본 적 있나요?? 궁금합니다','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',1,100,0,4,4),(20,'삼성모님은 취미가 뭐예요?','2024-02-15 00:00:00.000000','2024-02-15 00:00:00.000000',0,10,0,4,4),(21,'대박 초대형 거인 너무 멋져요 한번 근육자랑 해주세요!!','2024-02-16 00:00:00.000000','2024-02-16 00:00:00.000000',1,100,0,1,1),(22,'팬미팅 때 다 같이 진격의 거인 보면 좋겠어요!!','2024-02-16 00:00:00.000000','2024-02-16 00:00:00.000000',0,300,0,1,1),(23,'병사 한명을 이겨주세요','2024-02-16 00:00:00.000000','2024-02-16 00:00:00.000000',0,1000,1,1,1),(24,'보고 계신 풍경을 공유해주세요','2024-02-16 00:00:00.000000','2024-02-16 00:00:00.000000',0,500,0,1,1),(25,'바텀듀오','2024-02-16 00:00:00.000000','2024-02-16 00:00:00.000000',2,103000,0,3,3);
/*!40000 ALTER TABLE `Wish` ENABLE KEYS */;
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
