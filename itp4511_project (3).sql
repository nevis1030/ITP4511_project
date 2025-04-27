-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 27, 2025 at 04:22 AM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `itp4511_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

DROP TABLE IF EXISTS `borrow`;
CREATE TABLE IF NOT EXISTS `borrow` (
  `borrow_id` varchar(12) COLLATE utf8mb4_general_ci NOT NULL,
  `from_shop` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `to_shop` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `fruit_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `quantity` int NOT NULL,
  `status` int NOT NULL COMMENT '{0: ordered,\r\n1: approved,\r\n2: denied,\r\n3: complete}\r\n{0: ordered,\r\n1: approved,\r\n2: denied,\r\n3: complete}',
  `date` date NOT NULL,
  PRIMARY KEY (`borrow_id`),
  KEY `borrow_from_shop_fk` (`from_shop`),
  KEY `borrow_to_shop_fk` (`to_shop`),
  KEY `borrow_fruit_fk` (`fruit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`borrow_id`, `from_shop`, `to_shop`, `fruit_id`, `quantity`, `status`, `date`) VALUES
('b003', 's001', 's009', 'f001', 20, 1, '2025-04-23'),
('b004', 's001', 's009', 'f001', 20, 1, '2025-04-24');

-- --------------------------------------------------------

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
CREATE TABLE IF NOT EXISTS `city` (
  `city_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `city_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `region_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`city_id`),
  KEY `city_region_fk` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `city`
--

INSERT INTO `city` (`city_id`, `city_name`, `region_id`) VALUES
('c001', 'Sha Tin', 'r001'),
('c002', 'Kowloon', 'r001'),
('c003', 'Central', 'r001'),
('c004', 'New York', 'r002'),
('c005', 'Los Angeles', 'r002'),
('c006', 'Chicago', 'r002'),
('c007', 'Tokyo', 'r003'),
('c008', 'Kyoto', 'r003'),
('c009', 'Osaka', 'r003');

-- --------------------------------------------------------

--
-- Table structure for table `consumption`
--

DROP TABLE IF EXISTS `consumption`;
CREATE TABLE IF NOT EXISTS `consumption` (
  `consumption_id` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `fruit_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `region_id` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `quantity` int NOT NULL,
  `season` int NOT NULL COMMENT '{0: Spring,\r\n1: Summer,\r\n2: Fall,\r\n4, Winter}',
  PRIMARY KEY (`consumption_id`),
  KEY `consumption_fruit_fk` (`fruit_id`),
  KEY `consumption_region_fk` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `consumption`
--

INSERT INTO `consumption` (`consumption_id`, `fruit_id`, `region_id`, `quantity`, `season`) VALUES
('m001', 'f001', 'r001', 20, 1),
('m002', 'f002', 'r001', 10, 2);

-- --------------------------------------------------------

--
-- Table structure for table `fruit`
--

DROP TABLE IF EXISTS `fruit`;
CREATE TABLE IF NOT EXISTS `fruit` (
  `fruit_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `source_city_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `fruit_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`fruit_id`),
  KEY `fruit_city_fk` (`source_city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fruit`
--

INSERT INTO `fruit` (`fruit_id`, `source_city_id`, `fruit_name`) VALUES
('f001', 'c001', 'Hong Kong Apple'),
('f002', 'c004', 'USA fruit'),
('f003', 'c007', 'Japan fruit');

-- --------------------------------------------------------

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
CREATE TABLE IF NOT EXISTS `region` (
  `region_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `region_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `region`
--

INSERT INTO `region` (`region_id`, `region_name`) VALUES
('r001', 'Hong Kong'),
('r002', 'USA'),
('r003', 'Japan');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `reservation_id` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `shop_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `fruit_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `quantity` int NOT NULL,
  `status` int NOT NULL COMMENT '{0: ordered,\r\n1: approved,\r\n2: denied,\r\n3: complete}',
  `order_date` date NOT NULL,
  `end_date` date NOT NULL,
  PRIMARY KEY (`reservation_id`),
  KEY `reservation_fruit_fk` (`fruit_id`),
  KEY `reservation_shop_fk` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`reservation_id`, `shop_id`, `fruit_id`, `quantity`, `status`, `order_date`, `end_date`) VALUES
('v001', 's001', 'f001', 50, 1, '2025-04-23', '2025-05-07'),
('v002', 's001', 'f002', 100, 0, '2025-04-23', '2025-05-07'),
('v003', 's004', 'f001', 100, 1, '2025-04-23', '2025-05-07');

-- --------------------------------------------------------

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
CREATE TABLE IF NOT EXISTS `shop` (
  `shop_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `city_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `shop_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `city_id_fk` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shop`
--

INSERT INTO `shop` (`shop_id`, `city_id`, `shop_name`) VALUES
('s001', 'c001', 'HK shop Sha Tin'),
('s002', 'c002', 'HK shop Kowloon'),
('s003', 'c004', 'USA shop New York'),
('s004', 'c005', 'USA shop Los Angeles'),
('s005', 'c007', 'Japan shop Tokyo'),
('s006', 'c008', 'Japan shop Kyoto'),
('s009', 'c001', 'HK shop Sha Tin 2');

-- --------------------------------------------------------

--
-- Table structure for table `stocklevel`
--

DROP TABLE IF EXISTS `stocklevel`;
CREATE TABLE IF NOT EXISTS `stocklevel` (
  `stock_id` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `fruit_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `shop_id` varchar(4) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `warehouse_id` varchar(8) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`stock_id`),
  KEY `stocklevel_fruit_fk` (`fruit_id`),
  KEY `stocklevel_shop_fk` (`shop_id`),
  KEY `stocklevel_warehouse_fk` (`warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stocklevel`
--

INSERT INTO `stocklevel` (`stock_id`, `fruit_id`, `shop_id`, `warehouse_id`, `quantity`) VALUES
('s001f001', 'f001', 's001', NULL, 100),
('s001f002', 'f002', 's001', NULL, 150),
('s003f002', 'f002', 's003', NULL, 10),
('s004f002', 'f002', 's004', NULL, 15),
('s005f003', 'f003', 's005', NULL, 20),
('s006f003', 'f003', 's006', NULL, 15),
('w001f001', 'f001', NULL, 'cw001', 100),
('w001f002', 'f002', NULL, 'cw001', 500),
('w002f002', 'f002', NULL, 'w002', 100),
('w003f003', 'f003', NULL, 'w003', 250);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `shop_id` varchar(4) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `warehouse_id` varchar(8) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `display_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `role` int NOT NULL COMMENT '{0: shopStaff, \r\n1: warehouseStaff, \r\n2: seniorManager\r\n}',
  PRIMARY KEY (`user_id`),
  KEY `user_shop_fk` (`shop_id`),
  KEY `user_warehouse_fk` (`warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `shop_id`, `warehouse_id`, `display_name`, `username`, `password`, `role`) VALUES
('u001', 's001', NULL, 'HK shop staff 1', 'hkss1', 'hkss1', 0),
('u002', 's002', NULL, 'HK shop staff 2', 'hkss2', 'hkss2', 0),
('u003', NULL, 'cw001', 'HK warehouse staff 1', 'hkwhs1', 'hkwhs1', 1),
('u004', NULL, 'w002', 'HK warehouse staff 2', 'hkwhs2', 'hkwhs2', 1),
('u005', 's003', NULL, 'USA shop staff 1', 'usass1', 'usass1', 0),
('u006', 's004', NULL, 'USA shop staff 2', 'usass2', 'usass2', 0),
('u007', NULL, 'w004', 'USA warehouse staff 1', 'usaws1', 'usaws1', 1),
('u008', NULL, 'w005', 'USA warehouse staff 2', 'usaws2', 'usaws2', 1),
('u009', 's005', NULL, 'Japan shop staff 1', 'jpss1', 'jpss1', 0),
('u010', 's006', NULL, 'Japan shop staff 2', 'jpss2', 'jpss2', 0),
('u011', NULL, 'w009', 'Japan warehouse staff 1', 'jpwhs1', 'jpwhs1', 1),
('u012', NULL, 'w008', 'Japan warehouse staff 2', 'jpwhs2', 'jpwhs2', 1),
('u013', NULL, NULL, 'Senior manager 1', 'sm1', 'sm1', 2),
('u014', NULL, NULL, 'Senior manager 2', 'sm2', 'sm2', 2);

-- --------------------------------------------------------

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE IF NOT EXISTS `warehouse` (
  `warehouse_id` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `city_id` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `warehouse_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`warehouse_id`),
  KEY `warehouse_city_fk` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `warehouse`
--

INSERT INTO `warehouse` (`warehouse_id`, `city_id`, `warehouse_name`) VALUES
('cw001', 'c001', 'Hong Kong central warehouse 1'),
('cw002', 'c004', 'USA central warehouse 1'),
('cw003', 'c007', 'Japan central warehouse 1'),
('w002', 'c002', 'Hong Kong  warehouse 2'),
('w003', 'c003', 'Hong Kong  warehouse 3'),
('w004', 'c004', 'USA central warehouse 1'),
('w005', 'c005', 'USA warehouse 2'),
('w006', 'c006', 'USA warehouse 3'),
('w008', 'c008', 'Japan  warehouse 2'),
('w009', 'c009', 'Japan  warehouse 3');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `borrow_from_shop_fk` FOREIGN KEY (`from_shop`) REFERENCES `shop` (`shop_id`),
  ADD CONSTRAINT `borrow_fruit_fk` FOREIGN KEY (`fruit_id`) REFERENCES `fruit` (`fruit_id`),
  ADD CONSTRAINT `borrow_to_shop_fk` FOREIGN KEY (`to_shop`) REFERENCES `shop` (`shop_id`);

--
-- Constraints for table `city`
--
ALTER TABLE `city`
  ADD CONSTRAINT `city_region_fk` FOREIGN KEY (`region_id`) REFERENCES `region` (`region_id`);

--
-- Constraints for table `consumption`
--
ALTER TABLE `consumption`
  ADD CONSTRAINT `consumption_fruit_fk` FOREIGN KEY (`fruit_id`) REFERENCES `fruit` (`fruit_id`),
  ADD CONSTRAINT `consumption_region_fk` FOREIGN KEY (`region_id`) REFERENCES `region` (`region_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `fruit`
--
ALTER TABLE `fruit`
  ADD CONSTRAINT `fruit_city_fk` FOREIGN KEY (`source_city_id`) REFERENCES `city` (`city_id`);

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_fruit_fk` FOREIGN KEY (`fruit_id`) REFERENCES `fruit` (`fruit_id`),
  ADD CONSTRAINT `reservation_shop_fk` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`);

--
-- Constraints for table `shop`
--
ALTER TABLE `shop`
  ADD CONSTRAINT `city_id_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`);

--
-- Constraints for table `stocklevel`
--
ALTER TABLE `stocklevel`
  ADD CONSTRAINT `stocklevel_fruit_fk` FOREIGN KEY (`fruit_id`) REFERENCES `fruit` (`fruit_id`),
  ADD CONSTRAINT `stocklevel_shop_fk` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`),
  ADD CONSTRAINT `stocklevel_warehouse_fk` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_shop_fk` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`),
  ADD CONSTRAINT `user_warehouse_fk` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`);

--
-- Constraints for table `warehouse`
--
ALTER TABLE `warehouse`
  ADD CONSTRAINT `warehouse_city_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
