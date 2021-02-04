-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               8.0.23 - MySQL Community Server - GPL
-- Операционная система:         Win64
-- HeidiSQL Версия:              11.0.0.5919
-- --------------------------------------------------------
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Дамп структуры для таблица gift.certificate_tag
CREATE TABLE IF NOT EXISTS `certificate_tag` (
                                                 `gift_certificate_id` bigint DEFAULT NULL,
                                                 `tag_id` bigint DEFAULT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.gift_certificate
CREATE TABLE IF NOT EXISTS `gift_certificate` (
                                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                                  `name` varchar(50) NOT NULL,
                                                  `description` varchar(250) DEFAULT NULL,
                                                  `price` decimal(10, 2) DEFAULT NULL,
                                                  `duration` int DEFAULT NULL,
                                                  `create_date` varchar(50) DEFAULT NULL,
                                                  `last_update_date` varchar(50) DEFAULT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.order
CREATE TABLE IF NOT EXISTS `order` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `user_id` bigint NOT NULL,
                                       `certificate_id` bigint NOT NULL,
                                       `order_date` varchar(50) DEFAULT NULL,
                                       `cost` decimal(10, 2) DEFAULT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.tag
CREATE TABLE IF NOT EXISTS `tag` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `name` varchar(50) NOT NULL,
                                     `operation` varchar(10) NOT NULL,
                                     `operation_date` varchar(50) NOT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.user
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `name` varchar(50) NOT NULL
);
-- Экспортируемые данные не выделены.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
