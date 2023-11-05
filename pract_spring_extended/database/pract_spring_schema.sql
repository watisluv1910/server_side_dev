CREATE DATABASE  IF NOT EXISTS `pract_spring_database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pract_spring_database`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: pract_spring_database
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `ps_product_base`
--

DROP TABLE IF EXISTS `ps_product_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ps_product_base` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) NOT NULL,
  `product_cost_usd` int unsigned NOT NULL,
  `product_type_id` int unsigned NOT NULL,
  `seller_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ps_product_base_product_type_id_seller_id_index` (`product_type_id`,`seller_id`),
  KEY `ps_product_base_product_name_seller_id_index` (`product_name`,`seller_id`),
  KEY `ps_product_base_seller_id_foreign` (`seller_id`),
  CONSTRAINT `ps_product_base_product_type_id_foreign` FOREIGN KEY (`product_type_id`) REFERENCES `ps_product_type` (`id`),
  CONSTRAINT `ps_product_base_seller_id_foreign` FOREIGN KEY (`seller_id`) REFERENCES `ps_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ps_product_book`
--

DROP TABLE IF EXISTS `ps_product_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ps_product_book` (
  `product_base_id` int unsigned NOT NULL,
  `author_name` varchar(128) NOT NULL,
  `pages_count` smallint unsigned NOT NULL,
  PRIMARY KEY (`product_base_id`),
  CONSTRAINT `ps_product_book_product_base_id_foreign` FOREIGN KEY (`product_base_id`) REFERENCES `ps_product_base` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ps_product_phone`
--

DROP TABLE IF EXISTS `ps_product_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ps_product_phone` (
  `product_base_id` int unsigned NOT NULL,
  `manufacturer_name` varchar(128) NOT NULL,
  `model_name` varchar(128) NOT NULL,
  `battery_capacity_mah` mediumint unsigned NOT NULL,
  PRIMARY KEY (`product_base_id`),
  KEY `ps_product_phone_manufacturer_name_model_name_index` (`manufacturer_name`,`model_name`),
  CONSTRAINT `ps_product_phone_product_base_id_foreign` FOREIGN KEY (`product_base_id`) REFERENCES `ps_product_base` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ps_product_type`
--

DROP TABLE IF EXISTS `ps_product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ps_product_type` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ps_product_washing_machine`
--

DROP TABLE IF EXISTS `ps_product_washing_machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ps_product_washing_machine` (
  `product_base_id` int unsigned NOT NULL,
  `manufacturer_name` varchar(128) NOT NULL,
  `model_name` varchar(128) NOT NULL,
  `tank_volume_litre` tinyint unsigned NOT NULL,
  PRIMARY KEY (`product_base_id`),
  KEY `ps_product_washing_machine_manufacturer_name_model_name_index` (`manufacturer_name`,`model_name`),
  CONSTRAINT `ps_product_washing_machine_product_base_id_foreign` FOREIGN KEY (`product_base_id`) REFERENCES `ps_product_base` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ps_user`
--

DROP TABLE IF EXISTS `ps_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ps_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `display_name` varchar(32) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(320) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ps_user_username_unique` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ps_user_cart`
--

DROP TABLE IF EXISTS `ps_user_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ps_user_cart` (
  `user_id` int unsigned NOT NULL,
  `product_base_id` int unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`product_base_id`),
  KEY `ps_user_cart_product_base_id_foreign` (`product_base_id`),
  CONSTRAINT `ps_user_cart_product_base_id_foreign` FOREIGN KEY (`product_base_id`) REFERENCES `ps_product_base` (`id`),
  CONSTRAINT `ps_user_cart_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `ps_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ps_user_roles`
--

DROP TABLE IF EXISTS `ps_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ps_user_roles` (
  `user_id` int unsigned NOT NULL,
  `user_role_name` enum('USER','SELLER','ADMIN') NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`user_id`,`user_role_name`),
  CONSTRAINT `ps_user_roles_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `ps_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-06  0:15:39
