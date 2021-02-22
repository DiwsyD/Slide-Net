-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema slideNetDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema slideNetDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `slideNetDB` DEFAULT CHARACTER SET utf8 ;
USE `slideNetDB` ;

-- -----------------------------------------------------
-- Table `slideNetDB`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slideNetDB`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL)
    DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table `slideNetDB`.`service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slideNetDB`.`service` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) NULL UNIQUE)
    DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table `slideNetDB`.`tariff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slideNetDB`.`tariff` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `service_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(512) NOT NULL,
  `price` INT NULL,
  CONSTRAINT `fk_service_tariffs_service1`
    FOREIGN KEY (`service_id`) REFERENCES `slideNetDB`.`service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)
    DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table `slideNetDB`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slideNetDB`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NOT NULL,
  `login` NVARCHAR(20) NOT NULL,
  `password` NVARCHAR(50) NOT NULL,
  `fname` NVARCHAR(30) NOT NULL,
  `lname` NVARCHAR(30) NOT NULL,
  `sname` NVARCHAR(30) NULL,
  `address` NVARCHAR(150) NULL,
  `phone_number` NVARCHAR(45) NULL,
  `ip_address` NVARCHAR(20) NULL,
  `balance` INT NULL,
  `account_status` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `slideNetDB`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table `slideNetDB`.`account_service_tariff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slideNetDB`.`account_service_tariff` (
  `account_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  `tariff_id` INT NOT NULL,
  `activation_date` DATETIME NOT NULL,
  `enable_status` TINYINT NULL,
  `next_payment_day` DATE NULL,
  `payed` TINYINT NULL,
  `payment_amount` INT NULL,
  CONSTRAINT `fk_account_services_tariffs_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `slideNetDB`.`service` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_account_services_tariffs_service_tariff1`
    FOREIGN KEY (`tariff_id`)
    REFERENCES `slideNetDB`.`tariff` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_account_services_tariffs_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `slideNetDB`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
    DEFAULT CHARSET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Fill DB
-- -----------------------------------------------------

Insert into role (name) values ('admin');
insert into role (name) values ('user');

Insert into account (role_id, login, password, fname, lname,sname,address, phone_number, ip_address, balance, account_status)
values (1, 854652030, '474e1e45c52dccc30a340e4b4dc12f56', 'Ben ', 'Nicholson ', 'Chapman ', 'old Street, 15 Ave. Right Side', '+380910203040', '145.171.196.255', 950, true);

Insert into account (role_id, login, password, fname, lname,sname,address, phone_number, ip_address, balance, account_status)
values (2, 123456789, '3cca95088c094b795f861067e4d31bf7', 'Энтони ', 'Старк', 'Эдвард', '10660 Малибу Поинт 90210', '+000000000000', '255.255.255.255', 1000, true);

Insert into account (role_id, login, password, fname, lname,sname,address, phone_number, ip_address, balance, account_status)
values (2, 77999990, '3e9e5afe084edc6cccd2bd13b4a1a82c', 'Old', 'Man', 'Bob', 'oldest Street, 75 Ave. Ever Side', '+380123456789', '123.123.123.123', 100, false);

INSERT INTO service (name) VALUES ('Internet');
INSERT INTO service (name) VALUES ('Landline');
INSERT INTO service (name) VALUES ('Cable-TV');
INSERT INTO service (name) VALUES ('IP-TV');
INSERT INTO service (name) VALUES ('Additional');

INSERT INTO tariff (service_id, name, description, price) VALUES (1, 'Leader 100', 'Up to 100 Mbps', 180);
INSERT INTO tariff (service_id, name, description, price) VALUES (1, 'Turbo 300', 'Up to 300 Mbps', 225);
INSERT INTO tariff (service_id, name, description, price) VALUES (1, 'Lux 1G', 'Up to 1 Gbps', 270);

INSERT INTO tariff (service_id, name, description, price) VALUES (2, 'Call Ever', '300 min', 40);
INSERT INTO tariff (service_id, name, description, price) VALUES (2, 'Next-Line', '600', 80);
INSERT INTO tariff (service_id, name, description, price) VALUES (2, 'Slide-Line', '1200', 100);

INSERT INTO tariff (service_id, name, description, price) VALUES (3, 'Quick-TV', '25 Channels', 125);
INSERT INTO tariff (service_id, name, description, price) VALUES (3, 'Medium-TV', '70 Channels', 180);
INSERT INTO tariff (service_id, name, description, price) VALUES (3, 'Mega-TV', '100 Channels', 200);

INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'Basic', '22 TV-Programs', 125);
INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'Movie-Hit', '34 TV-Programs', 30);
INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'Sport', '40 TV-Programs', 25);
INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'HD', '50 TV-Programs', 25);
INSERT INTO tariff (service_id, name, description, price) VALUES (4, 'Adult*', '55 TV-Programs', 20);

INSERT INTO tariff (service_id, name, description, price) VALUES (5, 'Monthly maintenance', 'Equipment integrity check.', 120);
INSERT INTO tariff (service_id, name, description, price) VALUES (5, 'Maintence+', 'Equipment check + replacement.', 80);
INSERT INTO tariff (service_id, name, description, price) VALUES (5, 'Personal master', 'Technical support 24/7', 100);
