-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema depreciation
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema depreciation
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `depreciation` DEFAULT CHARACTER SET utf8 ;
USE `depreciation` ;

-- -----------------------------------------------------
-- Table `depreciation`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `depreciation`.`contact` (
  `contact_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`contact_id`),
  UNIQUE INDEX `contact_id_UNIQUE` (`contact_id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `depreciation`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `depreciation`.`company` (
  `company_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `foundation_date` DATETIME NULL,
  `business_scope` VARCHAR(45) NULL,
  `contact_contact_id` INT NOT NULL,
  PRIMARY KEY (`company_id`),
  INDEX `fk_company_contact_idx` (`contact_contact_id` ASC),
  CONSTRAINT `fk_company_contact`
    FOREIGN KEY (`contact_contact_id`)
    REFERENCES `depreciation`.`contact` (`contact_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `depreciation`.`equipment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `depreciation`.`equipment` (
  `equipment_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `price` DECIMAL(10,2) NULL,
  `exploitation_period_in_month` INT NULL,
  `exploitation_start_date` DATETIME NULL,
  `company_company_id` INT NOT NULL,
  PRIMARY KEY (`equipment_id`),
  INDEX `fk_equipment_company1_idx` (`company_company_id` ASC),
  CONSTRAINT `fk_equipment_company1`
    FOREIGN KEY (`company_company_id`)
    REFERENCES `depreciation`.`company` (`company_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `depreciation`.`contact`
-- -----------------------------------------------------
START TRANSACTION;
USE `depreciation`;
INSERT INTO `depreciation`.`contact` (`contact_id`, `first_name`, `last_name`, `email`, `password`, `role`) VALUES (1, 'Ivan', 'Ivanov', 'ivan@mail.ru', '1111', 'ADMIN');

COMMIT;


-- -----------------------------------------------------
-- Data for table `depreciation`.`company`
-- -----------------------------------------------------
START TRANSACTION;
USE `depreciation`;
INSERT INTO `depreciation`.`company` (`company_id`, `title`, `foundation_date`, `business_scope`, `contact_contact_id`) VALUES (1, 'MAZ', '2010-12-18 00:00:00', 'auto', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `depreciation`.`equipment`
-- -----------------------------------------------------
START TRANSACTION;
USE `depreciation`;
INSERT INTO `depreciation`.`equipment` (`equipment_id`, `title`, `price`, `exploitation_period_in_month`, `exploitation_start_date`, `company_company_id`) VALUES (1, 'stanok1', 2400, 24, '2019-01-10 00:00:00', 1);
INSERT INTO `depreciation`.`equipment` (`equipment_id`, `title`, `price`, `exploitation_period_in_month`, `exploitation_start_date`, `company_company_id`) VALUES (2, 'stanok2', 3600, 12, '2018-01-10 00:00:00', 1);

COMMIT;

