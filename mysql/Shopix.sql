CREATE DATABASE  IF NOT EXISTS `shopix` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `shopix`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shopix
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `street` varchar(255) NOT NULL,
  `city` varchar(120) NOT NULL,
  `zip_code` varchar(32) DEFAULT NULL,
  `country` varchar(120) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  PRIMARY KEY (`id`),
  KEY `idx_addresses_user` (`user_id`),
  CONSTRAINT `fk_addresses_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,5,'Szárcsa utca 19.','Kecskemét','6000','Magyarország','2025-09-19 08:43:29.000000'),(2,7,'Gát utca 20.','Kecskemét','6000','Magyarország','2025-09-19 12:46:14.000000'),(3,8,'Úrihegy utca 20.','Kecskemét','6000','Magyarország','2025-09-20 13:34:12.000000'),(4,9,'Add utca 19','Kecskemét','6000','Magyarország','2025-09-20 16:05:25.000000'),(5,10,'Bodócs gyula utca 30.','Kecskemét','6044','Magyarország','2025-09-20 16:55:22.000000');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cart_id` bigint(20) unsigned NOT NULL,
  `product_id` bigint(20) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `unit_price_snapshot` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_cart_items_cart_product` (`cart_id`,`product_id`),
  KEY `idx_cart_items_product` (`product_id`),
  CONSTRAINT `fk_cart_items_cart` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_cart_items_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (4,5,1,3,599990.00),(5,6,1,4,599990.00),(6,7,1,4,599990.00),(7,8,1,10,599990.00),(8,9,1,2,599990.00),(9,10,1,1,599990.00),(10,11,1,2,599990.00),(11,12,1,1,599990.00),(12,13,4,1,499990.00),(22,14,17,1,499990.00),(27,18,10,1,2990.00);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `status` varchar(32) NOT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT current_timestamp(6) ON UPDATE current_timestamp(6),
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  PRIMARY KEY (`id`),
  KEY `idx_carts_user` (`user_id`),
  KEY `idx_carts_status` (`status`),
  CONSTRAINT `fk_carts_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (2,1,'OPEN','2025-09-12 09:45:47.000000','2025-09-12 09:45:47.000000'),(5,2,'OPEN','2025-09-16 13:41:43.000000','2025-09-16 13:41:43.000000'),(6,3,'CLOSED','2025-09-17 08:54:04.000000','2025-09-17 08:53:13.000000'),(7,4,'CLOSED','2025-09-18 13:08:32.000000','2025-09-18 13:07:49.000000'),(8,5,'CLOSED','2025-09-18 13:15:06.000000','2025-09-18 13:12:27.000000'),(9,5,'CLOSED','2025-09-19 08:44:54.000000','2025-09-19 08:44:13.000000'),(10,5,'CLOSED','2025-09-19 09:56:03.000000','2025-09-19 09:55:20.000000'),(11,6,'CLOSED','2025-09-19 12:28:37.000000','2025-09-19 12:27:05.000000'),(12,7,'CLOSED','2025-09-19 12:47:18.000000','2025-09-19 12:45:08.000000'),(13,8,'CLOSED','2025-09-20 13:34:54.000000','2025-09-20 13:32:44.000000'),(14,9,'OPEN','2025-09-20 15:19:17.000000','2025-09-20 15:19:17.000000'),(15,10,'CLOSED','2025-09-20 16:56:46.000000','2025-09-20 16:53:01.000000'),(16,10,'CLOSED','2025-09-22 09:05:50.000000','2025-09-22 08:59:42.000000'),(17,10,'CLOSED','2025-09-22 09:06:47.000000','2025-09-22 09:06:44.000000'),(18,10,'CLOSED','2025-09-22 09:47:05.000000','2025-09-22 09:26:51.000000');
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `parent_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_categories_name` (`name`),
  KEY `idx_categories_parent` (`parent_id`),
  CONSTRAINT `fk_categories_parent` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (13,'Otthon és kert',NULL),(14,'Szépség és egészség',NULL),(15,'Sport és szabadidő',NULL),(19,'Mobiltelefonok',NULL),(20,'Laptopok',NULL),(21,'TV-k',NULL),(22,'Férfi ruházat',NULL),(23,'Női ruházat',NULL),(24,'Könyvek',NULL),(25,'Játékok',NULL);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inventory_product` (`product_id`),
  CONSTRAINT `fk_inventory_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL,
  `product_id` bigint(20) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `unit_price` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_items_order` (`order_id`),
  KEY `idx_order_items_product` (`product_id`),
  CONSTRAINT `fk_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_order_items_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,1,4,599990.00),(2,2,1,4,599990.00),(3,3,1,10,599990.00),(4,4,1,2,599990.00),(5,5,1,1,599990.00),(6,6,1,2,599990.00),(7,7,1,1,599990.00),(8,8,4,1,499990.00),(9,9,9,1,4990.00),(10,10,6,1,14990.00),(11,11,7,1,8990.00),(12,12,10,1,2990.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `total_gross` decimal(19,2) NOT NULL,
  `status` varchar(32) NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  `shipping_street` varchar(255) DEFAULT NULL,
  `shipping_city` varchar(120) DEFAULT NULL,
  `shipping_zip` varchar(32) DEFAULT NULL,
  `shipping_country` varchar(120) DEFAULT NULL,
  `billing_street` varchar(255) DEFAULT NULL,
  `billing_city` varchar(120) DEFAULT NULL,
  `billing_zip` varchar(32) DEFAULT NULL,
  `billing_country` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_orders_user` (`user_id`),
  KEY `idx_orders_status` (`status`),
  CONSTRAINT `fk_orders_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,3,2399960.00,'CREATED','2025-09-17 08:54:04.000000',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,4,2399960.00,'CREATED','2025-09-18 13:08:32.000000',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,5,5999900.00,'CREATED','2025-09-18 13:15:06.000000',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,5,1199980.00,'CREATED','2025-09-19 08:44:54.000000',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,5,599990.00,'CREATED','2025-09-19 09:56:03.000000','Szárcsa utca 19.','Kecskemét','6000','Magyarország','Szárcsa utca 19.','Kecskemét','6000','Magyarország'),(6,6,1199980.00,'PAID','2025-09-19 12:28:37.000000',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,7,599990.00,'PAID','2025-09-19 12:47:18.000000','Gát utca 20.','Kecskemét','6000','Magyarország','Gát utca 20.','Kecskemét','6000','Magyarország'),(8,8,499990.00,'PAID','2025-09-20 13:34:54.000000','Úrihegy utca 20.','Kecskemét','6000','Magyarország','Úrihegy utca 20.','Kecskemét','6000','Magyarország'),(9,10,4990.00,'PAID','2025-09-20 16:56:46.000000','Bodócs gyula utca 30.','Kecskemét','6044','Magyarország','Bodócs gyula utca 30.','Kecskemét','6044','Magyarország'),(10,10,14990.00,'CREATED','2025-09-22 09:05:50.000000','Bodócs gyula utca 30.','Kecskemét','6044','Magyarország','Bodócs gyula utca 30.','Kecskemét','6044','Magyarország'),(11,10,8990.00,'CREATED','2025-09-22 09:06:47.000000','Bodócs gyula utca 30.','Kecskemét','6044','Magyarország','Bodócs gyula utca 30.','Kecskemét','6044','Magyarország'),(12,10,2990.00,'PAID','2025-09-22 09:47:05.000000','Bodócs gyula utca 30.','Kecskemét','6044','Magyarország','Bodócs gyula utca 30.','Kecskemét','6044','Magyarország');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL,
  `status` varchar(32) NOT NULL,
  `provider_ref` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payments_order` (`order_id`),
  CONSTRAINT `fk_payments_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,3,'SUCCESS','MOCK-3','2025-09-18 13:16:12.000000'),(2,4,'SUCCESS','MOCK-4','2025-09-19 09:07:56.000000'),(3,5,'SUCCESS','MOCK-5','2025-09-19 09:57:35.000000'),(4,6,'SUCCESS','MOCK-6','2025-09-19 12:30:58.000000'),(5,7,'SUCCESS','MOCK-7','2025-09-19 12:48:03.000000'),(6,8,'SUCCESS','MOCK-8','2025-09-20 13:35:55.000000'),(7,9,'SUCCESS','MOCK-9','2025-09-20 16:58:05.000000'),(8,12,'SUCCESS','MOCK-12','2025-09-22 09:47:11.000000');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` longtext DEFAULT NULL,
  `price` decimal(19,2) NOT NULL,
  `category_id` bigint(20) unsigned NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  PRIMARY KEY (`id`),
  KEY `idx_products_category` (`category_id`),
  KEY `idx_products_active` (`active`),
  FULLTEXT KEY `ftx_products_name_desc` (`name`,`description`),
  CONSTRAINT `fk_products_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'iPhone 15 Pro Max','Apple iPhone 15 Pro Max 256GB, titán ház, csúcsteljesítmény',599990.00,19,1,'2025-09-11 13:52:27.000000'),(2,'Samsung Galaxy S24','Samsung Galaxy S24 128GB, AI funkciókkal',399990.00,19,1,'2025-09-11 13:52:27.000000'),(3,'Dell XPS 13','Dell XPS 13 ultrabook, Intel i7, 16GB RAM, 512GB SSD',649990.00,20,1,'2025-09-11 13:52:27.000000'),(4,'LG OLED TV 55\"','55 colos LG OLED TV, 4K HDR támogatással',499990.00,21,1,'2025-09-11 13:52:27.000000'),(5,'Férfi sportcipő','Kényelmes, légáteresztő futócipő',29990.00,22,1,'2025-09-11 13:52:27.000000'),(6,'Kerti szék','Időtálló, kényelmes kerti szék',14990.00,13,1,'2025-09-19 15:38:49.000000'),(7,'Asztali lámpa','Modern asztali lámpa, LED fényforrással',8990.00,13,1,'2025-09-19 15:38:49.000000'),(8,'Kávéfőző','Automata eszpresszó kávéfőző gép',59990.00,13,1,'2025-09-19 15:38:49.000000'),(9,'Arckrém','Hidratáló arckrém, minden bőrtípusra',4990.00,14,1,'2025-09-19 15:39:13.000000'),(10,'Vitamin C tabletta','1000 mg C-vitamin tabletta, 60 db',2990.00,14,1,'2025-09-19 15:39:13.000000'),(11,'Hajszárító','Professzionális hajszárító, ionos technológiával',12990.00,14,1,'2025-09-19 15:39:13.000000'),(12,'Futócipő','Könnyű futócipő mindennapi edzéshez',24990.00,15,1,'2025-09-19 15:39:22.000000'),(13,'Jóga szőnyeg','Csúszásmentes jógaszőnyeg',5990.00,15,1,'2025-09-19 15:39:22.000000'),(14,'Kerékpár sisak','Biztonságos kerékpáros sisak',15990.00,15,1,'2025-09-19 15:39:22.000000'),(15,'Xiaomi 13','Xiaomi 13 okostelefon, 256GB',299990.00,19,1,'2025-09-19 15:39:35.000000'),(16,'Google Pixel 8','Google Pixel 8 AI funkciókkal',349990.00,19,1,'2025-09-19 15:39:35.000000'),(17,'MacBook Air M2','Apple MacBook Air, 13 col, 512GB SSD',499990.00,20,1,'2025-09-19 15:39:39.000000'),(18,'Lenovo ThinkPad X1','Lenovo ThinkPad X1 Carbon, i7, 16GB RAM',579990.00,20,1,'2025-09-19 15:39:39.000000'),(19,'Samsung QLED 65\"','65 colos QLED televízió',699990.00,21,1,'2025-09-19 15:39:47.000000'),(20,'Sony Bravia 50\"','Sony Bravia 50 col, 4K UHD',399990.00,21,1,'2025-09-19 15:39:47.000000'),(21,'Férfi póló','Pamut póló, különböző méretekben',5990.00,22,1,'2025-09-19 15:39:51.000000'),(22,'Férfi farmer','Kényelmes slim fit farmer',19990.00,22,1,'2025-09-19 15:39:51.000000'),(23,'Női blúz','Elegáns fehér blúz',7990.00,23,1,'2025-09-19 15:39:57.000000'),(24,'Női szoknya','Nyári szoknya, virágmintás',12990.00,23,1,'2025-09-19 15:39:57.000000'),(25,'Női kabát','Téli meleg kabát',34990.00,23,1,'2025-09-19 15:39:57.000000'),(26,'Harry Potter és a bölcsek köve','JK Rowling klasszikus regénye',5990.00,24,1,'2025-09-19 15:40:00.000000'),(27,'A Gyűrűk Ura','JRR Tolkien fantasy regény',7990.00,24,1,'2025-09-19 15:40:00.000000'),(28,'Clean Code','Robert C. Martin – kódminőség javítása',11990.00,24,1,'2025-09-19 15:40:00.000000'),(29,'LEGO City készlet','Építőkészlet 500 darabbal',19990.00,25,1,'2025-09-19 15:40:05.000000'),(30,'Monopoly társasjáték','Klasszikus családi társasjáték',9990.00,25,1,'2025-09-19 15:40:05.000000'),(31,'PlayStation 5 Controller','DualSense vezeték nélküli kontroller',24990.00,25,1,'2025-09-19 15:40:05.000000');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` varchar(32) NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'testuser@example.com','hashedpassword','USER','2025-09-12 09:45:35.000000'),(2,'kata03@gmail.com','$2a$10$0zpfs0mY.nX7LDznPEe3nOu.dt2Qp0OPE0GXEb22V4mcrvURFQTE2','USER','2025-09-15 10:25:09.000000'),(3,'tesztelek03@gmail.com','$2a$10$CH71dKq1FjfE2QIc7/RZueI8pDOSvo7.czQGUxxsbKMf10f5FYg1y','USER','2025-09-17 08:44:39.000000'),(4,'tesztelek04@gmail.com','$2a$10$2x6st2lLQZq5X3jglAyZ1erFG3awGjXq64PrNRNehaJcM6yyhcj6O','USER','2025-09-18 13:06:18.000000'),(5,'madaraszmark461@gmail.com','$2a$10$bKlydivrHVVKVRPESlwakeLOksXaSjs7/RKLfLAxgTZpzzx65jGTO','USER','2025-09-18 13:11:25.000000'),(6,'finalteszt@gmail.com','$2a$10$Q.cbQqqh5jObaOLsECmqUOwFGo4nH3iED1ORUuR8xMCP3.F6b0xD6','USER','2025-09-19 12:24:20.000000'),(7,'finalteszt2@gmail.com','$2a$10$MdSuaSqb.LKUUZaU0r3EYO5b83/h4sFMTrrNA/c6H4S4UzKe5YzGC','USER','2025-09-19 12:43:55.000000'),(8,'vignora@gmail.com','$2a$10$60jPNNFTX4LSekv6C8lHqObEbt71sqY6eujtbKI8/uzWL7rSGP1t.','USER','2025-09-20 13:31:21.000000'),(9,'noravig02@gmail.com','$2a$10$aNWlVMqjawQXG1mgCCW2ZuR7iYRx2P7tl6C1ko8QuCNp.qGozZOsK','USER','2025-09-20 14:08:11.000000'),(10,'noravig03@gmail.com','$2a$10$lqdrnvW01vt9gVzKqNOQpuR.1kl6kQ.qJ7aEJQenxem61w87V2j4.','USER','2025-09-20 16:49:25.000000');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-22 10:00:05
