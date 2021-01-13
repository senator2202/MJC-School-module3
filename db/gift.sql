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

-- Дамп данных таблицы gift.certificate_tag: ~20 rows (приблизительно)
/*!40000 ALTER TABLE `certificate_tag` DISABLE KEYS */;
REPLACE INTO `certificate_tag` (`gift_certificate_id`, `tag_id`) VALUES
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
	(30, 19);
/*!40000 ALTER TABLE `certificate_tag` ENABLE KEYS */;

-- Дамп структуры для таблица gift.gift_certificate
DROP TABLE IF EXISTS `gift_certificate`;
CREATE TABLE IF NOT EXISTS `gift_certificate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` int DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `create_date` varchar(50) DEFAULT NULL,
  `last_update_date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.gift_certificate: ~14 rows (приблизительно)
/*!40000 ALTER TABLE `gift_certificate` DISABLE KEYS */;
REPLACE INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES
	(1, 'Сауна Тритон', 'Сертификат на бесплатное посещение сауны Тритон на Маяковского, 16', 100, 60, '2020-12-16T14:48Z', '2020-12-16T14:49Z'),
	(3, 'Тату салон "Лисица"', 'Бесплатная татуировка 10x10 см', 125, 180, '2020-12-16T14:51Z', '2020-12-16T14:52:Z'),
	(5, 'SPA центр на Старовиленской, 15', 'Весь спектр SPA процедур', 125, 180, '2020-12-17T11:49Z', '2020-12-17T11:51Z'),
	(6, 'Programming courses \'Java Web development\'', 'Become good programmer for short period', 400, 90, '2020-12-17T12:00Z', '2020-12-17T12:01Z'),
	(7, 'Театр имени Янки Купалы', 'Посещение любого спектакля', 60, 14, '2020-12-18T07:11Z', '2020-12-18T10:05Z'),
	(12, 'SilverScreen', 'Просмотр любого кинофильма', 15, 45, '2020-12-18T09:22Z', '2020-12-18T09:25Z'),
	(15, 'Курсы английского языка', 'Курсы английского в online школе SkyEng', 150, 100, '2020-12-18T10:22Z', '2020-12-18T10:37Z'),
	(16, 'Тату салон "Imagine Dragon"', 'Бесплатная татуировка 12х12, + дизайн', 250, 90, '2020-12-21T12:21Z', '2020-12-21T12:21Z'),
	(19, 'Онлайн курсы C#', 'Бесплатный курс C# в школе программирования Litrex', 1222, 120, '2020-12-22T12:33Z', '2020-12-22T12:57Z'),
	(20, 'Курс Python Web development', 'Бесплатное прохождение курса веб разработки на Python', 900, 90, '2020-12-23T08:22Z', '2020-12-23T08:22Z'),
	(24, 'Pilates', 'Best pilates in whole Minsk', 225, 180, '2021-01-11T06:18Z', '2021-01-11T06:18Z'),
	(25, 'Circus', 'Circus visit for 2 people', 100, 120, '2021-01-11T06:20Z', '2021-01-11T06:20Z'),
	(30, 'Masterpiece gallery', 'Gallery visit for 2 people', 55, 120, '2021-01-12T08:44Z', '2021-01-12T08:44Z');
/*!40000 ALTER TABLE `gift_certificate` ENABLE KEYS */;

-- Дамп структуры для таблица gift.orderDTO
DROP TABLE IF EXISTS `orderDTO`;
CREATE TABLE IF NOT EXISTS `orderDTO` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `certificate_id` bigint NOT NULL,
  `order_date` varchar(50) DEFAULT NULL,
  `cost` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_gift_certificate` (`certificate_id`),
  KEY `FK_order_user` (`user_id`),
  CONSTRAINT `FK_order_gift_certificate` FOREIGN KEY (`certificate_id`) REFERENCES `gift_certificate` (`id`),
  CONSTRAINT `FK_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.orderDTO: ~10 rows (приблизительно)
/*!40000 ALTER TABLE `orderDTO` DISABLE KEYS */;
REPLACE INTO `orderDTO` (`id`, `user_id`, `certificate_id`, `order_date`, `cost`) VALUES
	(1, 1, 6, '2020-12-24T12:21Z', 250),
	(2, 6, 15, '2020-12-24T13:21Z', 400),
	(3, 3, 19, '2020-12-24T14:21Z', 999),
	(4, 4, 7, '2020-12-24T10:19Z', 60),
	(5, 3, 1, '2020-12-24T10:20Z', 100),
	(6, 3, 15, '2020-12-24T10:26Z', 150),
	(7, 3, 15, '2020-12-24T10:26Z', 150),
	(8, 5, 5, '2020-12-24T10:27Z', 125),
	(9, 5, 5, '2020-12-24T10:27Z', 125),
	(10, 2, 16, '2020-12-24T10:28Z', 250),
	(14, 1, 25, '2021-01-12T07:43Z', 100),
	(15, 1, 19, '2021-01-12T08:04Z', 222);
/*!40000 ALTER TABLE `orderDTO` ENABLE KEYS */;

-- Дамп структуры для таблица gift.tag
DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `UK1wdpsed5kna2y38hnbgrnhi5b` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.tag: ~14 rows (приблизительно)
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
REPLACE INTO `tag` (`id`, `name`) VALUES
	(18, 'Circus'),
	(14, 'IT'),
	(20, 'Museum'),
	(13, 'Programming'),
	(25, 'Sladkii Bubaleh'),
	(1, 'Активность'),
	(10, 'Искусство'),
	(7, 'Кино'),
	(2, 'Красота'),
	(3, 'Образование'),
	(8, 'Отдых'),
	(15, 'Программирование'),
	(19, 'Развлечения'),
	(6, 'Театр');
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
REPLACE INTO `user` (`id`, `name`) VALUES
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
