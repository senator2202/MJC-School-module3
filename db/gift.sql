-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               8.0.21 - MySQL Community Server - GPL
-- Операционная система:         Win64
-- HeidiSQL Версия:              11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных gift
DROP DATABASE IF EXISTS `gift`;
CREATE DATABASE IF NOT EXISTS `gift` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gift`;

-- Дамп структуры для таблица gift.certificate_tag
DROP TABLE IF EXISTS `certificate_tag`;
CREATE TABLE IF NOT EXISTS `certificate_tag` (
  `gift_certificate_id` bigint DEFAULT NULL,
  `tag_id` bigint DEFAULT NULL,
  KEY `FK_certificates_tags_gift_certificate` (`gift_certificate_id`),
  KEY `FK_certificates_tags_tag` (`tag_id`),
  CONSTRAINT `FK_certificates_tags_gift_certificate` FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificate` (`id`),
  CONSTRAINT `FK_certificates_tags_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.certificate_tag: ~22 rows (приблизительно)
/*!40000 ALTER TABLE `certificate_tag` DISABLE KEYS */;
INSERT INTO `certificate_tag` (`gift_certificate_id`, `tag_id`) VALUES
	(1, 8),
	(1, 1),
	(3, 2),
	(5, 2),
	(5, 1),
	(5, 8),
	(6, 3),
	(12, 7),
	(7, 6),
	(15, 3),
	(15, 10),
	(16, 2),
	(19, 13),
	(19, 14),
	(20, 14),
	(20, 15),
	(24, 2),
	(24, 1),
	(25, 18),
	(25, 19),
	(30, 41),
	(30, 19);
/*!40000 ALTER TABLE `certificate_tag` ENABLE KEYS */;

-- Дамп структуры для таблица gift.gift_certificate
DROP TABLE IF EXISTS `gift_certificate`;
CREATE TABLE IF NOT EXISTS `gift_certificate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `create_date` varchar(50) DEFAULT NULL,
  `last_update_date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.gift_certificate: ~13 rows (приблизительно)
/*!40000 ALTER TABLE `gift_certificate` DISABLE KEYS */;
INSERT INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES
	(1, 'Сауна Тритон', 'Сертификат на бесплатное посещение сауны Тритон на Маяковского, 16', 100.00, 60, '2020-12-16T14:48Z', '2020-12-16T14:49Z'),
	(3, 'Тату салон "Лисица"', 'Бесплатная татуировка 10x10 см', 125.00, 180, '2020-12-16T14:51Z', '2020-12-16T14:52:Z'),
	(5, 'SPA центр на Старовиленской, 15', 'Весь спектр SPA процедур', 125.00, 180, '2020-12-17T11:49Z', '2020-12-17T11:51Z'),
	(6, 'Programming courses \'Java Web development\'', 'Become good programmer for short period', 400.00, 90, '2020-12-17T12:00Z', '2020-12-17T12:01Z'),
	(7, 'Театр имени Янки Купалы', 'Посещение любого спектакля', 60.00, 14, '2020-12-18T07:11Z', '2020-12-18T10:05Z'),
	(12, 'SilverScreen', 'Просмотр любого кинофильма', 15.00, 45, '2020-12-18T09:22Z', '2020-12-18T09:25Z'),
	(15, 'Курсы английского языка', 'Курсы английского в online школе SkyEng', 150.00, 100, '2020-12-18T10:22Z', '2020-12-18T10:37Z'),
	(16, 'Тату салон "Imagine Dragon"', 'Бесплатная татуировка 12х12, + дизайн', 250.00, 90, '2020-12-21T12:21Z', '2020-12-21T12:21Z'),
	(19, 'Онлайн курсы C#', 'Бесплатный курс C# в школе программирования Litrex', 1222.00, 120, '2020-12-22T12:33Z', '2020-12-22T12:57Z'),
	(20, 'Курс Python Web development', 'Бесплатное прохождение курса веб разработки на Python', 900.00, 90, '2020-12-23T08:22Z', '2020-12-23T08:22Z'),
	(24, 'Pilates', 'Best pilates in whole Minsk', 225.00, 180, '2021-01-11T06:18Z', '2021-01-11T06:18Z'),
	(25, 'Circus', 'Circus visit for 2 people', 100.00, 120, '2021-01-11T06:20Z', '2021-01-11T06:20Z'),
	(30, 'Masterpiece gallery', 'Gallery visit for 2 people', 55.00, 365, '2021-01-12T08:44Z', '2021-01-12T08:44Z');
/*!40000 ALTER TABLE `gift_certificate` ENABLE KEYS */;

-- Дамп структуры для таблица gift.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `certificate_id` bigint NOT NULL,
  `order_date` varchar(50) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_gift_certificate` (`certificate_id`),
  KEY `FK_order_user` (`user_id`),
  CONSTRAINT `FK_order_gift_certificate` FOREIGN KEY (`certificate_id`) REFERENCES `gift_certificate` (`id`),
  CONSTRAINT `FK_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.order: ~13 rows (приблизительно)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `user_id`, `certificate_id`, `order_date`, `cost`) VALUES
	(1, 1, 6, '2020-12-24T12:21Z', 250.00),
	(2, 6, 15, '2020-12-24T13:21Z', 400.00),
	(3, 3, 19, '2020-12-24T14:21Z', 999.00),
	(4, 4, 7, '2020-12-24T10:19Z', 60.00),
	(5, 3, 1, '2020-12-24T10:20Z', 100.00),
	(6, 3, 15, '2020-12-24T10:26Z', 150.00),
	(7, 3, 15, '2020-12-24T10:26Z', 150.00),
	(8, 5, 5, '2020-12-24T10:27Z', 125.00),
	(9, 5, 5, '2020-12-24T10:27Z', 125.00),
	(10, 2, 16, '2020-12-24T10:28Z', 250.00),
	(14, 1, 25, '2021-01-12T07:43Z', 100.00),
	(15, 1, 19, '2021-01-12T08:04Z', 222.00),
	(16, 1, 30, '2021-01-13T08:58Z', 55.00),
	(17, 1, 1, '2021-01-21T14:08:14.9825138', 100.00),
	(18, 1, 3, '2021-01-21T14:08:36.2681671', 125.00),
	(19, 1, 12, '2021-01-21T14:08:43.5779579', 15.00);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

-- Дамп структуры для таблица gift.tag
DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `operation` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `operation_date` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `UK1wdpsed5kna2y38hnbgrnhi5b` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.tag: ~16 rows (приблизительно)
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`id`, `name`, `operation`, `operation_date`) VALUES
	(1, 'Активность', '', ''),
	(2, 'Красота', '', ''),
	(3, 'Образование', '', ''),
	(6, 'Театр', '', ''),
	(7, 'Кино', '', ''),
	(8, 'Отдых', '', ''),
	(10, 'Искусство', '', ''),
	(13, 'Programming', '', ''),
	(14, 'IT', '', ''),
	(15, 'Программирование', '', ''),
	(18, 'Circus', '', ''),
	(19, 'Развлечения', '', ''),
	(20, 'Museum', '', ''),
	(25, 'Sladkii Bubaleh', '', ''),
	(41, 'trololo', '', ''),
	(43, 'Drotiki', 'INSERT', '2021-01-13T11:03Z');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;

-- Дамп структуры для таблица gift.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.user: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `name`) VALUES
	(1, 'Alex'),
	(2, 'Petr'),
	(3, 'Valerii'),
	(4, 'Mihail'),
	(5, 'Artem'),
	(6, 'Dukalis');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
