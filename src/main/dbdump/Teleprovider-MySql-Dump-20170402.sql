-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: teleprovider
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `client_id` bigint(20) NOT NULL,
  `funds` float NOT NULL DEFAULT '0',
  `active_tariff_id` bigint(20) DEFAULT NULL,
  `date_activated` date DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,10,2,'2017-04-02'),(2,0,1,'2017-04-02'),(3,1400,2,'2017-03-23'),(4,50,NULL,NULL);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `info` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Олег Шевченко','г. Киев, ул. Красноткацкая 20, кв. 47','oleg.sh.vi@gmail.com','Junior Java Developer'),(2,'Вася Пупкин','г. Бровары, ул. Пятигорская 32','vasya5678463@mail.com','Какая-то информация о клиенте........... инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо инфо.'),(3,'Donald Trump','The White House 1600 Pennsylvania Avenue NW Washington, DC 20500','bigboss@supermail1.us','The President of the United States of America'),(4,'Дмитрий Киселев','г.Москва, ул.Трепалова 1','liar-liar@yandex.ru','');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariffs`
--

DROP TABLE IF EXISTS `tariffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariffs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `cost` float NOT NULL,
  `months` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariffs`
--

LOCK TABLES `tariffs` WRITE;
/*!40000 ALTER TABLE `tariffs` DISABLE KEYS */;
INSERT INTO `tariffs` VALUES (1,'Standart',10,1),(2,'VIP',100,12);
/*!40000 ALTER TABLE `tariffs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) NOT NULL,
  `summ` float NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,10,'Manual add funds to client account','2017-03-01 16:39:34'),(2,1,-10,'Change tariff to Standart - $10.0','2017-03-01 16:41:36'),(3,2,20,'Manual add funds to client account','2017-03-02 16:43:31'),(4,2,-10,'Change tariff to Standart - $10.0','2017-03-02 16:43:38'),(5,4,50,'Manual add funds to client account','2017-03-08 16:44:35'),(6,3,1500,'Manual add funds to client account','2017-03-23 16:45:09'),(7,3,-100,'Change tariff to VIP - $100.0','2017-03-23 16:45:35'),(8,2,-10,'Withdrawing funds for tariff renewal','2017-04-02 16:57:22'),(9,1,110,'Manual add funds to client account','2017-04-02 16:58:36'),(10,1,-100,'Change tariff to VIP - $100.0','2017-04-02 16:58:44');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-02 17:27:54
