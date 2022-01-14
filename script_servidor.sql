-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-03-2019 a las 21:44:25
-- Versión del servidor: 10.1.37-MariaDB
-- Versión de PHP: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `finalobjetos`
--
CREATE DATABASE IF NOT EXISTS `finalobjetos` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `finalobjetos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calle`
--

CREATE TABLE `calle` (
  `latitud` decimal(10,8) NOT NULL,
  `longitud` decimal(10,8) NOT NULL,
  `vacio` tinyint(1) NOT NULL,
  `calle1` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `calle2` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `calle3` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `idCuadra` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `calle`
--

INSERT INTO `calle` (`latitud`, `longitud`, `vacio`, `calle1`, `calle2`, `calle3`, `idCuadra`) VALUES
('-37.32531400', '-59.13027900', 1, 'Pinto', 'Av. Santamarina', 'General paz', 1),
('-37.32579400', '-59.13156100', 0, 'Pinto', 'AV. Santamarina', 'General paz', 1),
('-37.32586200', '-59.13171500', 0, 'Pinto', 'General paz', 'Alem', 2),
('-37.32636400', '-59.13301000', 0, 'Pinto', 'General paz', 'Alem', 2),
('-37.32643000', '-59.13317200', 0, 'Pinto', 'Alem', '9 de julio', 3),
('-37.32691500', '-59.13445100', 0, 'Pinto', 'Alem', '9 de julio', 3),
('-37.32701100', '-59.13470600', 0, 'Pinto', '9 de julio', 'Rodriguez', 4),
('-37.32747500', '-59.13590900', 0, 'Pinto', '9 de julio', 'Rodriguez', 4),
('-37.32754100', '-59.13607800', 0, 'Pinto', 'Rodriguez', 'Yrigoyen', 5),
('-37.32799500', '-59.13724200', 0, 'Pinto', 'Rodriguez', 'Yrigoyen', 5),
('-37.32805300', '-59.13740800', 0, 'Pinto', 'Yrigoyen', 'Chacabuco', 6),
('-37.32846900', '-59.13841900', 0, 'Pinto', 'Yrigoyen', 'Chacabuco', 6),
('-37.32856300', '-59.13866500', 0, 'Pinto', 'Chacabuco', '14 de julio', 7),
('-37.32901700', '-59.13990200', 0, 'Pinto', 'Chacabuco', '14 de julio', 7),
('-37.32907300', '-59.14005600', 0, 'Pinto', '14 de julio', 'San Lorenzo', 8),
('-37.32956300', '-59.14133100', 0, 'Pinto', '14 de julio', 'San Lorenzo', 8),
('-37.32963100', '-59.14149400', 0, 'Pinto', 'San Lorenzo', 'Alberdi', 9),
('-37.33016100', '-59.14282400', 0, 'Pinto', 'San Lorenzo', 'Alberdi', 9),
('-37.33020700', '-59.14299000', 0, 'Pinto', 'Alberdi', 'Av. Rivadavia', 10),
('-37.33068500', '-59.14419300', 0, 'Pinto', 'Alberdi', 'AV. Rivadavia', 10),
('-37.32107000', '-59.13445400', 0, 'General paz', 'Av.espania', 'Mitre', 11),
('-37.32228800', '-59.13375600', 0, 'General paz', 'Av.espania', 'Mitre', 11),
('-37.32237400', '-59.13373400', 0, 'General paz', 'Mitre', 'Sarmiento', 12),
('-37.32341200', '-59.13311200', 0, 'General paz', 'Mitre', 'Sarmiento', 12),
('-37.32357400', '-59.13295900', 0, 'General paz', 'Sarmiento', 'San martin', 13),
('-37.32691500', '-59.13242200', 0, 'General paz', 'Sarmiento', 'San martin', 13),
('-37.32472600', '-59.13227200', 0, 'General paz', 'San martin', 'Pinto', 1),
('-37.32566400', '-59.13170400', 0, 'General paz', 'San martin', 'Pinto', 1),
('-37.32593700', '-59.13156400', 0, 'General paz', 'Pinto', 'Belgrano', 14),
('-37.32711400', '-59.13084500', 0, 'General paz', 'Pinto', 'Belgrano', 14),
('-37.32734500', '-59.13070600', 0, 'General paz', 'Belgrano', 'Maipu', 15),
('-37.32830000', '-59.13016900', 0, 'General paz', 'Belgrano', 'Maipu', 15),
('-37.32852200', '-59.13007300', 0, 'General paz', 'Maipu', '25 de mayo', 16),
('-37.32946100', '-59.12947200', 0, 'General paz', 'Maipu', '25 de mayo', 16),
('-37.32967000', '-59.12936100', 0, 'General paz', '25 de mayo', 'Constitucion', 17),
('-37.33061400', '-59.12881200', 0, 'General paz', '25 de mayo', 'Constitucion', 17),
('-37.33088800', '-59.12864000', 0, 'General paz', 'Constitucion', 'Av. Avellaneda', 18),
('-37.33183100', '-59.12805300', 0, 'General paz', 'Constitucion', 'Av. Avellaneda', 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuadra`
--

CREATE TABLE `cuadra` (
  `idCuadra` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `Descripcion` varchar(100) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `cuadra`
--

INSERT INTO `cuadra` (`idCuadra`, `cantidad`, `Descripcion`) VALUES
(1, 2, 'Pinto entre Santamarina y General paz'),
(2, 4, 'Pinto entre General paz y Alem'),
(3, 4, 'Pinto entre Alem y 9 de julio'),
(4, 4, 'Pinto entre 9 de julio y Rodriguez'),
(5, 4, 'Pinto entre Rodriguez e Yrigoyen'),
(6, 4, 'Pinto entre Yrigoyen y Chacabuco'),
(7, 4, 'Pinto entre Chacabuco y 14 de julio'),
(8, 4, 'Pinto entre 14 de julio y San lorenzo'),
(9, 4, 'Pinto entre San lorenzo y Alberdi'),
(10, 4, 'Pinto entre Alberdi y Rivadavia'),
(11, 4, 'General paz entre España y Mitre'),
(12, 4, 'General paz entre Mitre y Sarmiento'),
(13, 4, 'General paz entre Sarmiento y San Martin'),
(14, 4, 'General paz entre Pinto y belgrano'),
(15, 4, 'General paz entre Belgrano y Maipu'),
(16, 4, 'General paz entre Maipu y 25 de mayo'),
(17, 4, 'General paz entre 25 de mayo y Constitucion'),
(18, 4, 'General paz entre Constuticion y Avellaneda');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cuadra`
--
ALTER TABLE `cuadra`
  ADD PRIMARY KEY (`idCuadra`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cuadra`
--
ALTER TABLE `cuadra`
  MODIFY `idCuadra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
