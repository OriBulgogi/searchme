-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 21-12-21 18:19
-- 서버 버전: 10.1.13-MariaDB
-- PHP 버전: 7.3.1p1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `searchme1`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `allergy_type`
--

CREATE TABLE `allergy_type` (
  `user_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `땅콩` int(1) NOT NULL DEFAULT '0',
  `호두` int(1) NOT NULL DEFAULT '0',
  `연어` int(1) NOT NULL DEFAULT '0',
  `새우` int(1) NOT NULL DEFAULT '0',
  `밀` int(1) NOT NULL DEFAULT '0',
  `우유` int(1) NOT NULL DEFAULT '0',
  `갑각류` int(1) NOT NULL DEFAULT '0',
  `복숭아` int(1) NOT NULL DEFAULT '0',
  `옻` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 테이블의 덤프 데이터 `allergy_type`
--

INSERT INTO `allergy_type` (`user_id`, `땅콩`, `호두`, `연어`, `새우`, `밀`, `우유`, `갑각류`, `복숭아`, `옻`) VALUES
('new', 1, 1, 0, 1, 1, 0, 0, 0, 0),
('fk', 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- 테이블 구조 `preference`
--

CREATE TABLE `preference` (
  `user_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `끓이기` int(11) NOT NULL DEFAULT '0',
  `튀기기` int(11) NOT NULL DEFAULT '0',
  `굽기` int(11) NOT NULL DEFAULT '0',
  `찌기` int(11) NOT NULL DEFAULT '0',
  `볶기` int(1) DEFAULT '0',
  `기타` int(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 테이블의 덤프 데이터 `preference`
--

INSERT INTO `preference` (`user_id`, `끓이기`, `튀기기`, `굽기`, `찌기`, `볶기`, `기타`) VALUES
('new', 1, 1, 0, 0, 1, 0),
('fk', 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- 테이블 구조 `user`
--

CREATE TABLE `user` (
  `user_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(15) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 테이블의 덤프 데이터 `user`
--

INSERT INTO `user` (`user_id`, `password`) VALUES
('fk', 'fk'),
('new', 'newpw');

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `allergy_type`
--
ALTER TABLE `allergy_type`
  ADD KEY `forg_user_allergy` (`user_id`);

--
-- 테이블의 인덱스 `preference`
--
ALTER TABLE `preference`
  ADD KEY `forg_user` (`user_id`);

--
-- 테이블의 인덱스 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- 덤프된 테이블의 제약사항
--

--
-- 테이블의 제약사항 `allergy_type`
--
ALTER TABLE `allergy_type`
  ADD CONSTRAINT `forg_user_allergy` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `preference`
--
ALTER TABLE `preference`
  ADD CONSTRAINT `forg_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
