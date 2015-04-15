CREATE DATABASE  IF NOT EXISTS `knjiznica` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `knjiznica`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: knjiznica
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `avtor`
--

DROP TABLE IF EXISTS `avtor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avtor` (
  `ID_avtorja` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(60) DEFAULT NULL,
  `priimek` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`ID_avtorja`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avtor`
--

LOCK TABLES `avtor` WRITE;
/*!40000 ALTER TABLE `avtor` DISABLE KEYS */;
/*!40000 ALTER TABLE `avtor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradivo`
--

DROP TABLE IF EXISTS `gradivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gradivo` (
  `ID_gradiva` int(11) NOT NULL AUTO_INCREMENT,
  `naslov` varchar(50) DEFAULT NULL,
  `originalNaslov` varchar(50) DEFAULT NULL,
  `jezik` varchar(20) DEFAULT NULL,
  `letoIzida` date DEFAULT NULL,
  `ISBN` varchar(15) DEFAULT NULL,
  `opis` varchar(200) DEFAULT NULL,
  `tk_id_podrocja` int(11) DEFAULT NULL,
  `tk_id_vrste` int(11) DEFAULT NULL,
  `tk_id_zalozbe` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_gradiva`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradivo`
--

LOCK TABLES `gradivo` WRITE;
/*!40000 ALTER TABLE `gradivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `gradivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradivo_avtor`
--

DROP TABLE IF EXISTS `gradivo_avtor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gradivo_avtor` (
  `ID_ga` int(11) NOT NULL AUTO_INCREMENT,
  `tk_id_gradiva` int(11) DEFAULT NULL,
  `tk_id_avtorja` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_ga`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradivo_avtor`
--

LOCK TABLES `gradivo_avtor` WRITE;
/*!40000 ALTER TABLE `gradivo_avtor` DISABLE KEYS */;
/*!40000 ALTER TABLE `gradivo_avtor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradivo_storitev`
--

DROP TABLE IF EXISTS `gradivo_storitev`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gradivo_storitev` (
  `ID_gs` int(11) NOT NULL AUTO_INCREMENT,
  `tk_id_gradiva` int(11) DEFAULT NULL,
  `tk_id_storitve` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_gs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradivo_storitev`
--

LOCK TABLES `gradivo_storitev` WRITE;
/*!40000 ALTER TABLE `gradivo_storitev` DISABLE KEYS */;
/*!40000 ALTER TABLE `gradivo_storitev` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `naslov`
--

DROP TABLE IF EXISTS `naslov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `naslov` (
  `ID_naslova` int(11) NOT NULL AUTO_INCREMENT,
  `ulica` varchar(60) DEFAULT NULL,
  `hisnaSt` int(11) DEFAULT NULL,
  `mesto` varchar(30) DEFAULT NULL,
  `postnaSt` int(11) DEFAULT NULL,
  `drzava` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID_naslova`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `naslov`
--

LOCK TABLES `naslov` WRITE;
/*!40000 ALTER TABLE `naslov` DISABLE KEYS */;
/*!40000 ALTER TABLE `naslov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oseba`
--

DROP TABLE IF EXISTS `oseba`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oseba` (
  `ID_osebe` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(60) DEFAULT NULL,
  `priimek` varchar(60) DEFAULT NULL,
  `tipOsebe` varchar(15) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `telefon` varchar(15) DEFAULT NULL,
  `tk_id_naslova` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_osebe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oseba`
--

LOCK TABLES `oseba` WRITE;
/*!40000 ALTER TABLE `oseba` DISABLE KEYS */;
/*!40000 ALTER TABLE `oseba` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `podrocje`
--

DROP TABLE IF EXISTS `podrocje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podrocje` (
  `ID_podrocja` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID_podrocja`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podrocje`
--

LOCK TABLES `podrocje` WRITE;
/*!40000 ALTER TABLE `podrocje` DISABLE KEYS */;
/*!40000 ALTER TABLE `podrocje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prijava`
--

DROP TABLE IF EXISTS `prijava`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prijava` (
  `ID_prijave` int(11) NOT NULL AUTO_INCREMENT,
  `upIme` varchar(45) DEFAULT NULL,
  `geslo` varchar(45) DEFAULT NULL,
  `tk_id_osebe` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_prijave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prijava`
--

LOCK TABLES `prijava` WRITE;
/*!40000 ALTER TABLE `prijava` DISABLE KEYS */;
/*!40000 ALTER TABLE `prijava` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storitev`
--

DROP TABLE IF EXISTS `storitev`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storitev` (
  `ID_storitve` int(11) NOT NULL AUTO_INCREMENT,
  `datumIzposoje` date DEFAULT NULL,
  `datumVracila` date DEFAULT NULL,
  `zePodaljsano` tinyint(1) DEFAULT NULL,
  `tk_id_clana` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_storitve`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storitev`
--

LOCK TABLES `storitev` WRITE;
/*!40000 ALTER TABLE `storitev` DISABLE KEYS */;
/*!40000 ALTER TABLE `storitev` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vrstagradiva`
--

DROP TABLE IF EXISTS `vrstagradiva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vrstagradiva` (
  `ID_vrste` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID_vrste`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vrstagradiva`
--

LOCK TABLES `vrstagradiva` WRITE;
/*!40000 ALTER TABLE `vrstagradiva` DISABLE KEYS */;
/*!40000 ALTER TABLE `vrstagradiva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zalozba`
--

DROP TABLE IF EXISTS `zalozba`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zalozba` (
  `ID_zalozbe` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) DEFAULT NULL,
  `mesto` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`ID_zalozbe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zalozba`
--

LOCK TABLES `zalozba` WRITE;
/*!40000 ALTER TABLE `zalozba` DISABLE KEYS */;
/*!40000 ALTER TABLE `zalozba` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zapisnacl`
--

DROP TABLE IF EXISTS `zapisnacl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zapisnacl` (
  `ID_zapisa` int(11) NOT NULL AUTO_INCREMENT,
  `datumZapisa` date DEFAULT NULL,
  `datumIzbrisa` date DEFAULT NULL,
  `razlog` varchar(200) DEFAULT NULL,
  `tk_id_osebe` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_zapisa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zapisnacl`
--

LOCK TABLES `zapisnacl` WRITE;
/*!40000 ALTER TABLE `zapisnacl` DISABLE KEYS */;
/*!40000 ALTER TABLE `zapisnacl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-15 13:58:13
