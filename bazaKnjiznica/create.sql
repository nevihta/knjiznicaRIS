

CREATE TABLE `knjiznica`.`avtor` (
  `ID_avtorja` INT NOT NULL AUTO_INCREMENT,
  `ime` VARCHAR(60) NULL,
  `priimek` VARCHAR(60) NULL,
  PRIMARY KEY (`ID_avtorja`));

CREATE TABLE `knjiznica`.`naslov` (
  `ID_naslova` INT NOT NULL AUTO_INCREMENT,
  `ulica` VARCHAR(60) NULL,
  `hisnaSt` VARCHAR (15) NULL,
  `mesto` VARCHAR (30) NULL,
  `postnaSt` INT NULL,
  `drzava` VARCHAR(20) NULL,
  PRIMARY KEY (`ID_naslova`));


CREATE TABLE `knjiznica`.`oseba` (
  `ID_osebe` INT NOT NULL AUTO_INCREMENT,
  `ime` VARCHAR(60) NULL,
  `priimek` VARCHAR(60) NULL,
  `tipOsebe` VARCHAR (15) NULL,
  `email` VARCHAR(45) NULL,
  `telefon` VARCHAR(15) NULL,
  `tk_id_naslova` INT NULL,
  PRIMARY KEY (`ID_osebe`));

CREATE TABLE `knjiznica`.`prijava` (
  `ID_prijave` INT NOT NULL AUTO_INCREMENT,
  `upIme` VARCHAR(45) NULL,
  `geslo` VARCHAR (45)  NULL,
  `tk_id_osebe` INT NULL,
  PRIMARY KEY (`ID_prijave`));

CREATE TABLE `knjiznica`.`zapisNaCl` (
  `ID_zapisa` INT NOT NULL AUTO_INCREMENT,
  `datumZapisa` DATE NULL,
  `datumIzbrisa` DATE NULL,
  `razlog` VARCHAR (200) NULL,
  `tk_id_osebe` INT NULL,
  PRIMARY KEY (`ID_zapisa`));

CREATE TABLE `knjiznica`.`zalozba` (
  `ID_zalozbe` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR (100) NULL,
  `mesto` VARCHAR (70) NULL,
  PRIMARY KEY (`ID_zalozbe`));

CREATE TABLE `knjiznica`.`vrstaGradiva` (
  `ID_vrste` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR (45) NULL,
  PRIMARY KEY (`ID_vrste`));

CREATE TABLE `knjiznica`.`podrocje` (
  `ID_podrocja` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR (45) NULL,
  PRIMARY KEY (`ID_podrocja`));

CREATE TABLE `knjiznica`.`gradivo` (
  `ID_gradiva` INT NOT NULL AUTO_INCREMENT,
  `naslov` VARCHAR (50) NULL,
  `originalNaslov` VARCHAR (50) NULL,
  `jezik` VARCHAR (20) NULL,
  `letoIzida` DATE NULL,
  `ISBN` VARCHAR (15) NULL,
  `opis` VARCHAR (200) NULL,
  `tk_id_podrocja` INT NULL,
  `tk_id_vrste` INT NULL,
  `tk_id_zalozbe` INT NULL,
  PRIMARY KEY (`ID_gradiva`));

CREATE TABLE `knjiznica`.`storitev` (
  `ID_storitve` INT NOT NULL AUTO_INCREMENT,
  `datumIzposoje` DATE NULL,
  `datumVracila` DATE NULL,
  `zePodaljsano` BOOLEAN NULL,
  `tk_id_clana` INT NULL,
  `tk_id_knjiznicarja` INT NULL,
  PRIMARY KEY (`ID_storitve`));

CREATE TABLE `knjiznica`.`gradivo_storitev` (
  `ID_gs` INT NOT NULL AUTO_INCREMENT,
  `tk_id_gradiva` INT NULL,
  `tk_id_storitve` INT NULL,
  PRIMARY KEY (`ID_gs`));

CREATE TABLE `knjiznica`.`gradivo_avtor` (
  `ID_ga` INT NOT NULL AUTO_INCREMENT,
  `tk_id_gradiva` INT NULL,
  `tk_id_avtorja` INT NULL,
  PRIMARY KEY (`ID_ga`));