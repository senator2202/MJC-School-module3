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
-- Дамп данных таблицы gift.certificate_tag: ~16 rows (приблизительно)
/*!40000 ALTER TABLE `certificate_tag` DISABLE KEYS */;
INSERT INTO `certificate_tag` (`gift_certificate_id`, `tag_id`)
VALUES (1, 8),
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
       (20, 15);
/*!40000 ALTER TABLE `certificate_tag` ENABLE KEYS */;
-- Дамп данных таблицы gift.gift_certificate: ~10 rows (приблизительно)
/*!40000 ALTER TABLE `gift_certificate` DISABLE KEYS */;
INSERT INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`
                               , `create_date`, `last_update_date`)
VALUES (1, 'Сауна Тритон', 'Сертификат на бесплатное посещение сауны Тритон на Маяковского, 16', 100, 60
       , '2020-12-16T14:48Z', '2020-12-16T14:49Z'),
       (3, 'Тату салон "Лисица"', 'Бесплатная татуировка 10x10 см', 125, 180
       , '2020-12-16T14:51Z', '2020-12-16T14:52:Z'),
       (5, 'SPA центр на Старовиленской, 15', 'Весь спектр SPA процедур', 125, 180
       , '2020-12-17T11:49Z', '2020-12-17T11:51Z'),
       (6, 'Programming courses ''Java Web development''', 'Become good programmer for short period', 400, 90
       , '2020-12-17T12:00Z', '2020-12-17T12:01Z'),
       (7, 'Театр имени Янки Купалы', 'Посещение любого спектакля', 60, 14
       , '2020-12-18T07:11Z', '2020-12-18T10:05Z'),
       (12, 'SilverScreen', 'Просмотр любого кинофильма', 15, 45
       , '2020-12-18T09:22Z', '2020-12-18T09:25Z'),
       (15, 'Курсы английского языка', 'Курсы английского в online школе SkyEng', 150, 100
       , '2020-12-18T10:22Z', '2020-12-18T10:37Z'),
       (16, 'Тату салон "Imagine Dragon"', 'Бесплатная татуировка 12х12, + дизайн', 250, 90
       , '2020-12-21T12:21Z', '2020-12-21T12:21Z'),
       (19, 'Онлайн курсы C#', 'Бесплатный курс C# в школе программирования Litrex', 1222, 120
       , '2020-12-22T12:33Z', '2020-12-22T12:57Z'),
       (20, 'Курс Python Web development', 'Бесплатное прохождение курса веб разработки на Python', 900, 90
       , '2020-12-23T08:22Z', '2020-12-23T08:22Z');
/*!40000 ALTER TABLE `gift_certificate` ENABLE KEYS */;
-- Дамп данных таблицы gift.order: ~10 rows (приблизительно)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `user_id`, `certificate_id`, `order_date`, `cost`)
VALUES (1, 1, 6, '2020-12-24T12:21Z', 250),
       (2, 6, 15, '2020-12-24T13:21Z', 400),
       (3, 3, 19, '2020-12-24T14:21Z', 999),
       (4, 4, 7, '2020-12-24T10:19Z', 60),
       (5, 3, 1, '2020-12-24T10:20Z', 100),
       (6, 3, 15, '2020-12-24T10:26Z', 150),
       (7, 3, 15, '2020-12-24T10:26Z', 200),
       (8, 5, 5, '2020-12-24T10:27Z', 125),
       (9, 5, 5, '2020-12-24T10:27Z', 125),
       (10, 2, 16, '2020-12-24T10:28Z', 250);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
-- Дамп данных таблицы gift.tag: ~10 rows (приблизительно)
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`id`, `name`)
VALUES (14, 'IT'),
       (13, 'Programming'),
       (1, 'Активность'),
       (10, 'Искусство'),
       (7, 'Кино'),
       (2, 'Красота'),
       (3, 'Образование'),
       (8, 'Отдых'),
       (15, 'Программирование'),
       (6, 'Театр');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
-- Дамп данных таблицы gift.user: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `name`)
VALUES (1, 'Alex'),
       (2, 'Petr'),
       (3, 'Valerii'),
       (4, 'Mihail'),
       (5, 'Artem'),
       (6, 'Dukalis');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
