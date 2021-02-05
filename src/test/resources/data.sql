-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               8.0.23 - MySQL Community Server - GPL
-- Операционная система:         Win64
-- HeidiSQL Версия:              11.0.0.5919
-- --------------------------------------------------------
/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
DELETE
FROM `certificate_tag`;
/*!40000 ALTER TABLE `certificate_tag`
    DISABLE KEYS */;
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
       (20, 15),
       (24, 2),
       (24, 1),
       (25, 18),
       (25, 19),
       (30, 41),
       (30, 19);
/*!40000 ALTER TABLE `certificate_tag`
    ENABLE KEYS */;
DELETE
FROM `gift_certificate`;
/*!40000 ALTER TABLE `gift_certificate`
    DISABLE KEYS */;
INSERT INTO `gift_certificate` ( `id`, `name`, `description`, `price`, `duration`
                               , `create_date`, `last_update_date`)
VALUES ( 1, 'Сауна Тритон', 'Сертификат на бесплатное посещение сауны Тритон на Маяковского, 16', 100.00, 60
       , '2020-12-16T14:48Z', '2020-12-16T14:49Z'),
       ( 3, 'Тату салон "Лисица"', 'Бесплатная татуировка 10x10 см', 125.00, 180
       , '2020-12-16T14:51Z', '2020-12-16T14:52:Z'),
       ( 5, 'SPA центр на Старовиленской, 15', 'Весь спектр SPA процедур', 125.00, 180
       , '2020-12-17T11:49Z', '2020-12-17T11:51Z'),
       ( 6, 'Programming courses ''Java Web development''', 'Become good programmer for short period', 400.00, 90
       , '2020-12-17T12:00Z', '2020-12-17T12:01Z'),
       ( 7, 'Театр имени Янки Купалы', 'Посещение любого спектакля', 60.00, 14
       , '2020-12-18T07:11Z', '2020-12-18T10:05Z'),
       ( 12, 'SilverScreen', 'Просмотр любого кинофильма', 15.00, 45
       , '2020-12-18T09:22Z', '2020-12-18T09:25Z'),
       ( 15, 'Курсы английского языка', 'Курсы английского в online школе SkyEng', 150.00, 100
       , '2020-12-18T10:22Z', '2020-12-18T10:37Z'),
       ( 16, 'Тату салон "Imagine Dragon"', 'Бесплатная татуировка 12х12, + дизайн', 250.00, 90
       , '2020-12-21T12:21Z', '2020-12-21T12:21Z'),
       ( 19, 'Онлайн курсы C#', 'Бесплатный курс C# в школе программирования Litrex', 1222.00, 120
       , '2020-12-22T12:33Z', '2020-12-22T12:57Z'),
       ( 20, 'Курс Python Web development', 'Бесплатное прохождение курса веб разработки на Python', 900.00, 90
       , '2020-12-23T08:22Z', '2020-12-23T08:22Z'),
       ( 24, 'Pilates', 'Best pilates in whole Minsk', 225.00, 180
       , '2021-01-11T06:18Z', '2021-01-11T06:18Z'),
       ( 25, 'Circus', 'Circus visit for 2 people', 100.00, 120
       , '2021-01-11T06:20Z', '2021-01-11T06:20Z'),
       ( 30, 'Masterpiece gallery', 'Gallery visit for 2 people', 55.00, 365
       , '2021-01-12T08:44Z', '2021-01-12T08:44Z');
/*!40000 ALTER TABLE `gift_certificate`
    ENABLE KEYS */;
DELETE
FROM `order`;
/*!40000 ALTER TABLE `order`
    DISABLE KEYS */;
INSERT INTO `order` (`id`, `user_id`, `certificate_id`, `order_date`, `cost`)
VALUES (1, 1, 6, '2020-12-24T12:21Z', 250.00),
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
/*!40000 ALTER TABLE `order`
    ENABLE KEYS */;
DELETE
FROM `tag`;
/*!40000 ALTER TABLE `tag`
    DISABLE KEYS */;
INSERT INTO `tag` (`id`, `name`, `operation`, `operation_date`)
VALUES (1, 'Активность', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (2, 'Красота', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (3, 'Образование', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (6, 'Театр', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (7, 'Кино', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (8, 'Отдых', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (10, 'Искусство', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (13, 'Programming', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (14, 'IT', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (15, 'Программирование', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (18, 'Circus', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (19, 'Развлечения', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (20, 'Museum', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (25, 'Sladkii Bubaleh', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (41, 'trololo', 'INSERT', '2021-02-04T11:47:51.8445638'),
       (43, 'Drotiki', 'INSERT', '2021-02-04T11:47:51.8445638');
/*!40000 ALTER TABLE `tag`
    ENABLE KEYS */;
DELETE
FROM `user`;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user` (`id`, `name`)
VALUES (1, 'Alex'),
       (2, 'Petr'),
       (3, 'Valerii'),
       (4, 'Mihail'),
       (5, 'Artem'),
       (6, 'Dukalis');
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
