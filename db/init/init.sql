-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: financetrackerschema
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `type` enum('CHECKING','CREDIT_CARD','SAVINGS') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5vyi6yktkuwkfot8hctl2e75` (`user_id`),
  CONSTRAINT `FKf5vyi6yktkuwkfot8hctl2e75` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,9500,'CHECKING',1),(2,2500,'CREDIT_CARD',5),(3,3200.75,'SAVINGS',6),(4,1000,'CHECKING',4),(8,5000,'SAVINGS',1),(10,1200.5,'CHECKING',11),(11,5000,'SAVINGS',11),(12,1500.75,'CREDIT_CARD',11),(13,3000.2,'CHECKING',12),(14,10000,'SAVINGS',12),(15,2500,'CREDIT_CARD',12),(16,4500.5,'CHECKING',13),(17,8000,'SAVINGS',13),(18,3500.25,'CREDIT_CARD',13);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,NULL,'ROLE_ADMIN'),(2,NULL,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK20w7wsg13u9srbq3bd7chfxdh` (`account_id`),
  CONSTRAINT `FK20w7wsg13u9srbq3bd7chfxdh` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,100,'2024-09-20 14:35:00.000000','Grocery shopping',1,NULL),(2,250.75,'2024-09-21 16:00:00.000000','Car repair',1,NULL),(3,1500,'2024-09-22 10:20:00.000000','Credit card payment',2,NULL),(4,300.5,'2024-09-23 09:15:00.000000','Online shopping',2,NULL),(5,500,'2024-09-24 11:30:00.000000','Deposit',3,NULL),(6,750.25,'2024-09-25 13:45:00.000000','Rent payment',3,NULL),(7,150,'2024-01-15 10:30:00.000000','Grocery Store Purchase',10,'2024-10-18 16:26:27.000000'),(8,1200.5,'2024-02-10 14:45:00.000000','Salary Deposit',11,'2024-10-18 16:26:27.000000'),(9,-200,'2024-02-18 09:30:00.000000','Credit Card Payment',12,'2024-10-18 16:26:27.000000'),(10,50,'2024-03-01 13:00:00.000000','Gas Station Purchase',10,'2024-10-18 16:26:27.000000'),(11,500,'2024-01-10 09:00:00.000000','Online Purchase',13,'2024-10-18 16:26:27.000000'),(12,3000,'2024-01-20 16:00:00.000000','Salary Deposit',14,'2024-10-18 16:26:27.000000'),(13,-350.75,'2024-02-05 18:45:00.000000','Credit Card Payment',15,'2024-10-18 16:26:27.000000'),(14,75,'2024-03-10 10:15:00.000000','Utility Bill Payment',13,'2024-10-18 16:26:27.000000'),(15,250,'2024-02-01 11:30:00.000000','Restaurant',16,'2024-10-18 16:26:27.000000'),(16,8000,'2024-02-12 12:30:00.000000','Salary Deposit',17,'2024-10-18 16:26:27.000000'),(17,-1500,'2024-02-20 15:00:00.000000','Credit Card Payment',18,'2024-10-18 16:26:27.000000'),(18,45,'2024-03-02 14:30:00.000000','Parking Fee',16,'2024-10-18 16:26:27.000000');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `failed_attempts` int NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user1@example.com','$2a$10$examplehashedpassword1','user1',_binary '\0',0,_binary '\0',_binary '\0'),(2,'user2@example.com','$2a$10$examplehashedpassword2','user2',_binary '\0',0,_binary '\0',_binary '\0'),(3,'user3@example.com','$2a$10$examplehashedpassword3','user3',_binary '\0',0,_binary '\0',_binary '\0'),(4,'user4@example.com','password4','user4',_binary '\0',0,_binary '\0',_binary '\0'),(5,'user5@example.com','password5','user5',_binary '\0',0,_binary '\0',_binary '\0'),(6,'user6@example.com','password6','user6',_binary '\0',0,_binary '\0',_binary '\0'),(7,'admin@example.com','$2a$10$7JWjNzVWKK0XqewB3AETou7g85O5HaAQxhuqQuDyB9TZ5RJwExKv2','admin',_binary '\0',0,_binary '',_binary '\0'),(8,'postman@example.com','$2a$10$Wbenw16bonIb77h5Zh/a8.pHEwVWrimQrpV8tUzak1PaR3nxttmPi','postmanuser',_binary '\0',0,_binary '',_binary '\0'),(10,'new_admin@example.com','$2a$10$pH/ymniMM2G1ega1wrA/xOTG.Ad5VP/HylNolPOvKLg9bqpJcCtyu','new_admin',_binary '\0',0,_binary '',_binary '\0'),(11,'testreg@example.com','$2a$10$J0CwFzBLGC.bYu9GWXAfdOoJBKTIT8wzzJ35lc5gqf5/tl3wmPgtC','testreg',_binary '\0',0,_binary '',_binary '\0'),(12,'testreg@example.com','$2a$10$9VRtiQyN5rFBPLFQGpEDg.gag.3pYGZwiYgAmPUFWj1MMgtrT6tPm','testreg1',_binary '\0',0,_binary '',_binary '\0'),(13,'testreg@example.com','$2a$10$johx0u1tM8AGhX/O.8rrWe2rzq0tOFdJoSSaNIFiD5q6zB3u.23mq','testreg2',_binary '\0',0,_binary '',_binary '\0'),(14,NULL,'$2a$10$rOjhSHsdZC7w7CdWGyyhTepV909bcdZgPtAOeIM5Ywc0n/rNOoR42','frontendtest',_binary '\0',0,_binary '',_binary '\0'),(15,'frontend@frontend.com','$2a$10$Z2PiysefNwYJqQbfL9uFkefkwvmZUEaPemLFfDRWPbXyBnqnonO2e','frontend',_binary '\0',0,_binary '',_binary '\0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (10,1),(11,2),(12,2),(13,2),(14,2),(15,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-22 16:38:20
