/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.4.32-MariaDB : Database - bd_easytime
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `calificacion` */

DROP TABLE IF EXISTS `calificacion`;

CREATE TABLE `calificacion` (
  `ID_CALIFICACION` varchar(45) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `SERVICIO_ID_SERVICIO` int(11) NOT NULL,
  PRIMARY KEY (`ID_CALIFICACION`),
  KEY `calificacion_ibfk_1` (`SERVICIO_ID_SERVICIO`),
  CONSTRAINT `calificacion_ibfk_1` FOREIGN KEY (`SERVICIO_ID_SERVICIO`) REFERENCES `servicio` (`ID_SERVICIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `calificacion` */

/*Table structure for table `cita` */

DROP TABLE IF EXISTS `cita`;

CREATE TABLE `cita` (
  `ID_CITA` int(11) NOT NULL AUTO_INCREMENT,
  `EST_CITA` tinyint(4) NOT NULL DEFAULT 1,
  `FECHA_CITA` datetime NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `UPDATE_AT` datetime NOT NULL,
  `USUARIO_ID_USUARIO` int(11) NOT NULL,
  `SERVICIO_ID_SERVICIO` int(11) NOT NULL,
  PRIMARY KEY (`ID_CITA`),
  KEY `USUARIO_ID_USUARIO` (`USUARIO_ID_USUARIO`),
  KEY `SERVICIO_ID_SERVICIO` (`SERVICIO_ID_SERVICIO`),
  CONSTRAINT `cita_ibfk_2` FOREIGN KEY (`SERVICIO_ID_SERVICIO`) REFERENCES `servicio` (`ID_SERVICIO`),
  CONSTRAINT `cita_ibfk_3` FOREIGN KEY (`USUARIO_ID_USUARIO`) REFERENCES `usuario` (`ID_USER`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `cita` */

insert  into `cita`(`ID_CITA`,`EST_CITA`,`FECHA_CITA`,`CREATED_AT`,`UPDATE_AT`,`USUARIO_ID_USUARIO`,`SERVICIO_ID_SERVICIO`) values (1,1,'2025-06-20 18:50:16','0000-00-00 00:00:00','0000-00-00 00:00:00',0,1001),(2,1,'2025-07-10 18:50:20','0000-00-00 00:00:00','0000-00-00 00:00:00',4,1002),(3,1,'2025-06-05 18:50:25','0000-00-00 00:00:00','0000-00-00 00:00:00',6,1001),(4,0,'2025-06-03 18:50:34','0000-00-00 00:00:00','0000-00-00 00:00:00',5,1003);

/*Table structure for table `detalle_pedido` */

DROP TABLE IF EXISTS `detalle_pedido`;

CREATE TABLE `detalle_pedido` (
  `ID_ITEM` varchar(45) NOT NULL,
  `CANTIDAD` int(11) NOT NULL,
  `PEDIDO_ID_PEDIDO` int(11) NOT NULL,
  `PRODUCTOS_ID_PRODUCTO` int(11) NOT NULL,
  PRIMARY KEY (`ID_ITEM`),
  KEY `PEDIDO_ID_PEDIDO` (`PEDIDO_ID_PEDIDO`),
  KEY `PRODUCTOS_ID_PRODUCTO` (`PRODUCTOS_ID_PRODUCTO`),
  CONSTRAINT `detalle_pedido_ibfk_1` FOREIGN KEY (`PEDIDO_ID_PEDIDO`) REFERENCES `pedido` (`ID_PEDIDO`),
  CONSTRAINT `detalle_pedido_ibfk_2` FOREIGN KEY (`PRODUCTOS_ID_PRODUCTO`) REFERENCES `productos` (`ID_PRODUCTO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `detalle_pedido` */

/*Table structure for table `facturacion` */

DROP TABLE IF EXISTS `facturacion`;

CREATE TABLE `facturacion` (
  `ID_FACTURA` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA_VENCIMIENTO` datetime NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `UPDATE_AT` datetime NOT NULL,
  `SERVICIO_ID_SERVICIO` int(11) NOT NULL,
  `DETALLEPEDIDO_ID_DETALLEPEDIDO` int(11) NOT NULL,
  `USUARIO_ID_USUARIO` int(11) NOT NULL,
  PRIMARY KEY (`ID_FACTURA`),
  KEY `SERVICIO_ID_SERVICIO` (`SERVICIO_ID_SERVICIO`),
  KEY `CARRITO_ID_CARRITO` (`DETALLEPEDIDO_ID_DETALLEPEDIDO`),
  KEY `USUARIO_ID_UDIARIO` (`USUARIO_ID_USUARIO`),
  CONSTRAINT `facturacion_ibfk_1` FOREIGN KEY (`SERVICIO_ID_SERVICIO`) REFERENCES `servicio` (`ID_SERVICIO`),
  CONSTRAINT `facturacion_ibfk_4` FOREIGN KEY (`DETALLEPEDIDO_ID_DETALLEPEDIDO`) REFERENCES `detalle_pedido` (`PEDIDO_ID_PEDIDO`),
  CONSTRAINT `facturacion_ibfk_5` FOREIGN KEY (`USUARIO_ID_USUARIO`) REFERENCES `usuario` (`ID_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `facturacion` */

/*Table structure for table `inventario` */

DROP TABLE IF EXISTS `inventario`;

CREATE TABLE `inventario` (
  `ID_INVENTARIO` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_PROD_INVE` varchar(45) NOT NULL,
  `CANTIDAD` varchar(45) NOT NULL,
  `UPDATE_AT` datetime NOT NULL,
  `PROVEEDOR_ID_PROVEDOR` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_INVENTARIO`),
  KEY `PROVEEDOR_ID_PROVEDOR` (`PROVEEDOR_ID_PROVEDOR`),
  CONSTRAINT `inventario_ibfk_1` FOREIGN KEY (`PROVEEDOR_ID_PROVEDOR`) REFERENCES `proveedor` (`ID_PROVEDOR`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `inventario` */

insert  into `inventario`(`ID_INVENTARIO`,`NOMBRE_PROD_INVE`,`CANTIDAD`,`UPDATE_AT`,`PROVEEDOR_ID_PROVEDOR`) values (13,'Cera Brillante PUFF','200','2025-06-17 17:36:37',90909090),(14,'Cera Brillante Autoschic','90','2025-06-17 17:36:37',90978390),(15,'ambientadores','270','2025-06-17 17:36:37',90909090),(16,'limpiaespejos','100','2025-06-17 17:36:37',90911789);

/*Table structure for table `notificapago` */

DROP TABLE IF EXISTS `notificapago`;

CREATE TABLE `notificapago` (
  `ID_NOTIFICA_PAGO` int(11) NOT NULL AUTO_INCREMENT,
  `PAGO_ID_PAGO` int(11) NOT NULL,
  PRIMARY KEY (`ID_NOTIFICA_PAGO`),
  KEY `PAGO_ID_PAGO` (`PAGO_ID_PAGO`),
  CONSTRAINT `notificapago_ibfk_1` FOREIGN KEY (`PAGO_ID_PAGO`) REFERENCES `pago` (`ID_PAGO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `notificapago` */

/*Table structure for table `notificapqr` */

DROP TABLE IF EXISTS `notificapqr`;

CREATE TABLE `notificapqr` (
  `ID_NOTIFIACION_PQR` int(11) NOT NULL AUTO_INCREMENT,
  `EST_NOT` tinyint(4) NOT NULL,
  `FECHA_NOT` datetime NOT NULL,
  `MSJ_NOT` varchar(120) NOT NULL,
  `PQR_ID_PQR` int(11) NOT NULL,
  PRIMARY KEY (`ID_NOTIFIACION_PQR`),
  KEY `PQR_ID_PQR` (`PQR_ID_PQR`),
  CONSTRAINT `notificapqr_ibfk_1` FOREIGN KEY (`PQR_ID_PQR`) REFERENCES `pqr` (`ID_PQR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `notificapqr` */

/*Table structure for table `pago` */

DROP TABLE IF EXISTS `pago`;

CREATE TABLE `pago` (
  `ID_PAGO` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA_PAGO` datetime NOT NULL,
  `MONTO_PAGO` varchar(45) NOT NULL,
  `METODO_PAGO` varchar(45) NOT NULL,
  `FACTURACION_ID_FACTURA` int(11) NOT NULL,
  PRIMARY KEY (`ID_PAGO`),
  KEY `FACTURACION_ID_FACTURA` (`FACTURACION_ID_FACTURA`),
  CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`FACTURACION_ID_FACTURA`) REFERENCES `facturacion` (`ID_FACTURA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `pago` */

/*Table structure for table `pedido` */

DROP TABLE IF EXISTS `pedido`;

CREATE TABLE `pedido` (
  `ID_PEDIDO` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA_PED` datetime NOT NULL,
  `USUARIO_ID_USUARIO` int(11) NOT NULL,
  PRIMARY KEY (`ID_PEDIDO`),
  KEY `USUARIO_ID_USUARIO` (`USUARIO_ID_USUARIO`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`USUARIO_ID_USUARIO`) REFERENCES `usuario` (`ID_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `pedido` */

/*Table structure for table `permisos` */

DROP TABLE IF EXISTS `permisos`;

CREATE TABLE `permisos` (
  `ID_PERMISO` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPCION` varchar(80) NOT NULL,
  PRIMARY KEY (`ID_PERMISO`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `permisos` */

insert  into `permisos`(`ID_PERMISO`,`DESCRIPCION`) values (1,'SELECT'),(2,'UPDATE'),(3,'INSERT'),(4,'DELETE');

/*Table structure for table `permisos_has_rol` */

DROP TABLE IF EXISTS `permisos_has_rol`;

CREATE TABLE `permisos_has_rol` (
  `ID_ROL_HAS_PERMISO` int(11) NOT NULL AUTO_INCREMENT,
  `ROL_ID_ROL` int(11) NOT NULL,
  `PERMISOS_ID_PERMISO` int(11) NOT NULL,
  PRIMARY KEY (`ID_ROL_HAS_PERMISO`),
  KEY `ROL_ID_ROL` (`ROL_ID_ROL`),
  KEY `PERMISOS_ID_PERMISO` (`PERMISOS_ID_PERMISO`),
  CONSTRAINT `permisos_has_rol_ibfk_1` FOREIGN KEY (`ROL_ID_ROL`) REFERENCES `rol` (`ID_ROL`),
  CONSTRAINT `permisos_has_rol_ibfk_2` FOREIGN KEY (`PERMISOS_ID_PERMISO`) REFERENCES `permisos` (`ID_PERMISO`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `permisos_has_rol` */

insert  into `permisos_has_rol`(`ID_ROL_HAS_PERMISO`,`ROL_ID_ROL`,`PERMISOS_ID_PERMISO`) values (1,2,1),(2,2,2),(3,2,3),(4,2,4),(5,1,1),(6,1,3),(7,3,1);

/*Table structure for table `pqr` */

DROP TABLE IF EXISTS `pqr`;

CREATE TABLE `pqr` (
  `ID_PQR` int(11) NOT NULL AUTO_INCREMENT,
  `EST_PRQ` tinyint(4) NOT NULL,
  `FECHA_PQR` datetime NOT NULL,
  `CATEGORIA_PQR` varchar(45) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `UPDATE_AT` varchar(45) NOT NULL,
  `USUARIO_ID_USUARIO` int(11) NOT NULL,
  PRIMARY KEY (`ID_PQR`),
  KEY `USUARIO_ID_USUARIO` (`USUARIO_ID_USUARIO`),
  CONSTRAINT `pqr_ibfk_1` FOREIGN KEY (`USUARIO_ID_USUARIO`) REFERENCES `usuario` (`ID_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `pqr` */

/*Table structure for table `productos` */

DROP TABLE IF EXISTS `productos`;

CREATE TABLE `productos` (
  `ID_PRODUCTO` int(11) NOT NULL AUTO_INCREMENT,
  `COD_PROD` varchar(50) NOT NULL,
  `NOM_PROD` varchar(45) NOT NULL,
  `DESCRIPCION_PROD` varchar(45) NOT NULL,
  `CADUCIDAD_PROD` varchar(25) NOT NULL,
  `PRECIO_PROD` varchar(45) NOT NULL,
  `CANTIDAD_PROD` varchar(45) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `UPDATE_AT` datetime NOT NULL,
  `INVENTARIO_ID_INVENTARIO` int(11) NOT NULL,
  PRIMARY KEY (`ID_PRODUCTO`),
  UNIQUE KEY `INVENTARIO_ID_INVENTARIO` (`COD_PROD`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`INVENTARIO_ID_INVENTARIO`) REFERENCES `inventario` (`ID_INVENTARIO`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `productos` */

insert  into `productos`(`ID_PRODUCTO`,`COD_PROD`,`NOM_PROD`,`DESCRIPCION_PROD`,`CADUCIDAD_PROD`,`PRECIO_PROD`,`CANTIDAD_PROD`,`CREATED_AT`,`UPDATE_AT`,`INVENTARIO_ID_INVENTARIO`) values (1,'0997843','Cera Brillante PUFF',' 350 ml','30/12/2026','$33000','50','2025-06-16 18:51:21','2025-06-16 18:51:21',13),(2,'8479390','Cera Brillante Autoschic',' 550 ml','12/12/2026','$45000','50','2025-06-16 18:51:21','2025-06-16 18:51:21',14),(3,'9485953','Cera protectora ACDC',' 350 ml','30/10/2026','$38000','30','2025-06-16 18:51:21','2025-06-16 18:51:21',14),(4,'8704394','Ambientador canela',' 12ml','11/08/2026','$8000','20','2025-06-16 18:51:21','2025-06-16 18:51:21',15),(5,'','limpiaespejos 2000',' 200 ml','30/12/2026','$40000','10','2025-06-16 18:51:21','2025-06-16 18:51:21',16);

/*Table structure for table `promociones` */

DROP TABLE IF EXISTS `promociones`;

CREATE TABLE `promociones` (
  `ID_PROMOCIONES` int(11) NOT NULL AUTO_INCREMENT,
  `TIPO_PROMO` varchar(45) NOT NULL,
  `NOM_PROMO` varchar(45) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `UPDATE_AT` datetime NOT NULL,
  `VIGENCIA` datetime NOT NULL,
  `PRODUCTOS_ID_PRODUCTO` int(11) NOT NULL,
  PRIMARY KEY (`ID_PROMOCIONES`),
  KEY `PRODUCTOS_ID_PRODUCTO` (`PRODUCTOS_ID_PRODUCTO`),
  CONSTRAINT `promociones_ibfk_1` FOREIGN KEY (`PRODUCTOS_ID_PRODUCTO`) REFERENCES `productos` (`ID_PRODUCTO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `promociones` */

/*Table structure for table `proveedor` */

DROP TABLE IF EXISTS `proveedor`;

CREATE TABLE `proveedor` (
  `ID_PROVEDOR` int(11) NOT NULL AUTO_INCREMENT,
  `TIPO_DOC` enum('NIT','Cedula Ciudadania','Pasaporte') NOT NULL,
  `NOM_PROV` varchar(45) NOT NULL,
  `TEL_PROV` varchar(45) NOT NULL,
  `EST_PROV` tinyint(4) NOT NULL DEFAULT 1,
  `CREATED_AT` datetime NOT NULL,
  `UPDATE_AT` datetime NOT NULL,
  PRIMARY KEY (`ID_PROVEDOR`)
) ENGINE=InnoDB AUTO_INCREMENT=90978391 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `proveedor` */

insert  into `proveedor`(`ID_PROVEDOR`,`TIPO_DOC`,`NOM_PROV`,`TEL_PROV`,`EST_PROV`,`CREATED_AT`,`UPDATE_AT`) values (90099999,'NIT','Autopartes LTDA','3155135131',1,'2025-06-17 17:24:59','2025-06-17 17:24:59'),(90909090,'NIT','Aseo SAS','3215648909',1,'2025-06-17 17:24:59','2025-06-17 17:24:59'),(90911789,'NIT','CarClean SAS','3227886788',1,'2025-06-17 17:24:59','2025-06-17 17:24:59'),(90978390,'NIT','Petrobrass LTDA','3058778906',1,'2025-06-17 17:24:59','2025-06-17 17:24:59');

/*Table structure for table `rol` */

DROP TABLE IF EXISTS `rol`;

CREATE TABLE `rol` (
  `ID_ROL` int(11) NOT NULL AUTO_INCREMENT,
  `NOM_ROL` varchar(45) NOT NULL,
  PRIMARY KEY (`ID_ROL`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `rol` */

insert  into `rol`(`ID_ROL`,`NOM_ROL`) values (1,'Cliente'),(2,'Administrador'),(3,'Jefe de patio');

/*Table structure for table `rol_has_usuario` */

DROP TABLE IF EXISTS `rol_has_usuario`;

CREATE TABLE `rol_has_usuario` (
  `ROL_ID_ROL` int(11) NOT NULL,
  `USUARIO_ID_USUARIO` int(11) NOT NULL,
  KEY `ROL_ID_ROL` (`ROL_ID_ROL`),
  KEY `USUARIO_ID_USUARIO` (`USUARIO_ID_USUARIO`),
  CONSTRAINT `rol_has_usuario_ibfk_1` FOREIGN KEY (`ROL_ID_ROL`) REFERENCES `rol` (`ID_ROL`),
  CONSTRAINT `rol_has_usuario_ibfk_2` FOREIGN KEY (`USUARIO_ID_USUARIO`) REFERENCES `usuario` (`ID_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `rol_has_usuario` */

/*Table structure for table `servicio` */

DROP TABLE IF EXISTS `servicio`;

CREATE TABLE `servicio` (
  `ID_SERVICIO` int(11) NOT NULL AUTO_INCREMENT,
  `NOM_SERV` varchar(45) NOT NULL,
  `DURACION_SERV` varchar(45) NOT NULL,
  `EST_SERV` tinyint(4) NOT NULL DEFAULT 1,
  `DESCP_SERV` varchar(45) NOT NULL,
  `PRECIO_SERV` varchar(45) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `UPDATE_AT` datetime NOT NULL,
  PRIMARY KEY (`ID_SERVICIO`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `servicio` */

insert  into `servicio`(`ID_SERVICIO`,`NOM_SERV`,`DURACION_SERV`,`EST_SERV`,`DESCP_SERV`,`PRECIO_SERV`,`CREATED_AT`,`UPDATE_AT`) values (1001,'LAVADO NORMAL','1 HORA',1,'Lavado basico solo por encima del vh','$13000','0000-00-00 00:00:00','2025-06-16 18:51:29'),(1002,'LAVADO ESPECIAL','2 HORAS',1,'Lavado con shampoo y esponjas especiales','$45000','0000-00-00 00:00:00','2025-06-16 18:51:29'),(1003,'LAVADO PREMIUM','2 HORAS',1,' Lavado Completo a tapiceria y alfombrado del','$138000','0000-00-00 00:00:00','2025-06-16 18:51:29');

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `ID_USER` int(12) NOT NULL AUTO_INCREMENT,
  `NUMERO_DOC` int(20) NOT NULL,
  `TIPO_DOC` enum('Cedula Ciudadania','Tarjeta Identidad','Permiso de Proteccion Temporal','Cedula Extranjeria','Pasaporte','NIT') NOT NULL,
  `NOM_USER` varchar(15) NOT NULL,
  `APE_USER` varchar(20) NOT NULL,
  `TEL_USER` varchar(15) NOT NULL,
  `CORREO_USER` varchar(45) NOT NULL,
  `ROL_USER` enum('Cliente','Administrador','Jefe de patio') NOT NULL,
  `EST_USER` tinyint(4) NOT NULL DEFAULT 1,
  `CREATED_AT` datetime NOT NULL,
  `UPDATE_AT` datetime NOT NULL,
  `ID_ROL_USER` int(20) DEFAULT NULL,
  PRIMARY KEY (`ID_USER`),
  UNIQUE KEY `ID_ROL_USER` (`NUMERO_DOC`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`ID_ROL_USER`) REFERENCES `rol` (`ID_ROL`)
) ENGINE=InnoDB AUTO_INCREMENT=1012413774 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `usuario` */

insert  into `usuario`(`ID_USER`,`NUMERO_DOC`,`TIPO_DOC`,`NOM_USER`,`APE_USER`,`TEL_USER`,`CORREO_USER`,`ROL_USER`,`EST_USER`,`CREATED_AT`,`UPDATE_AT`,`ID_ROL_USER`) values (0,7688181,'Cedula Ciudadania','Dayana','Rincon','3112223344','isladaya@gmail.com','Cliente',1,'2025-06-16 18:43:48','2025-06-16 18:43:48',0),(1,10101010,'Permiso de Proteccion Temporal','Javier','Arraiz','3123344232','javisala@gmail.com','Jefe de patio',1,'2025-06-16 18:43:48','2025-06-16 18:43:48',0),(2,10107890,'Cedula Ciudadania','Samir','Bahoque','3013026655','Andresbaq@gmail.com','Jefe de patio',1,'2025-06-16 18:43:48','2025-06-16 18:43:48',0),(3,79646778,'Cedula Ciudadania','Paula','Carrillo','3211231212','paucarrillo@gmail.com','Administrador',1,'2025-06-16 18:43:48','2025-06-16 18:43:48',0),(4,20202020,'Cedula Ciudadania','Neider','Mendoza','3013023301','neymen@gmail.com','Cliente',1,'2025-06-16 18:43:48','2025-06-16 18:43:48',0),(5,87654321,'Cedula Ciudadania','Duvan','Uribe','3216758800','uribedav@gmail.com','Cliente',1,'2025-06-16 18:43:48','2025-06-16 18:43:48',0),(6,90807889,'Cedula Ciudadania','Jovanhi','Rico','3135557878','Yovahni@gmail.com','Cliente',1,'2025-06-16 18:43:48','2025-06-16 18:43:48',0),(7,1012413771,'Cedula Ciudadania','Gerson','Rubio','3125843702','gersrubio@gmail.com','Administrador',1,'2025-06-16 18:43:48','2025-06-16 18:43:48',0);

/* Procedure structure for procedure `obtenerClientesActivos` */

/*!50003 DROP PROCEDURE IF EXISTS  `obtenerClientesActivos` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `obtenerClientesActivos`()
BEGIN
    SELECT * FROM clientes WHERE estado = 'activo';
END */$$
DELIMITER ;

/* Procedure structure for procedure `obtenerUsuariosActivos` */

/*!50003 DROP PROCEDURE IF EXISTS  `obtenerUsuariosActivos` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `obtenerUsuariosActivos`()
BEGIN
    SELECT * FROM usuario WHERE estado = 'activo';
END */$$
DELIMITER ;

/*Table structure for table `v_admin` */

DROP TABLE IF EXISTS `v_admin`;

/*!50001 DROP VIEW IF EXISTS `v_admin` */;
/*!50001 DROP TABLE IF EXISTS `v_admin` */;

/*!50001 CREATE TABLE  `v_admin`(
 `ID_USER` int(12) ,
 `NUMERO_DOC` int(20) ,
 `TIPO_DOC` enum('Cedula Ciudadania','Tarjeta Identidad','Permiso de Proteccion Temporal','Cedula Extranjeria','Pasaporte','NIT') ,
 `NOM_USER` varchar(15) ,
 `APE_USER` varchar(20) ,
 `TEL_USER` varchar(15) ,
 `CORREO_USER` varchar(45) ,
 `ROL_USER` enum('Cliente','Administrador','Jefe de patio') ,
 `EST_USER` tinyint(4) ,
 `CREATED_AT` datetime ,
 `UPDATE_AT` datetime ,
 `ID_ROL_USER` int(20) ,
 `ID_ROL` int(11) ,
 `NOM_ROL` varchar(45) 
)*/;

/*Table structure for table `v_citas` */

DROP TABLE IF EXISTS `v_citas`;

/*!50001 DROP VIEW IF EXISTS `v_citas` */;
/*!50001 DROP TABLE IF EXISTS `v_citas` */;

/*!50001 CREATE TABLE  `v_citas`(
 `NOM_SERV` varchar(45) ,
 `DESCP_SERV` varchar(45) ,
 `ID_SERVICIO` int(11) ,
 `ID_CITA` int(11) ,
 `EST_CITA` tinyint(4) ,
 `FECHA_CITA` datetime ,
 `NOM_USER` varchar(15) ,
 `APE_USER` varchar(20) ,
 `NUMERO_DOC` int(20) ,
 `ROL_USER` enum('Cliente','Administrador','Jefe de patio') 
)*/;

/*Table structure for table `v_clientes` */

DROP TABLE IF EXISTS `v_clientes`;

/*!50001 DROP VIEW IF EXISTS `v_clientes` */;
/*!50001 DROP TABLE IF EXISTS `v_clientes` */;

/*!50001 CREATE TABLE  `v_clientes`(
 `ID_USER` int(12) ,
 `NUMERO_DOC` int(20) ,
 `TIPO_DOC` enum('Cedula Ciudadania','Tarjeta Identidad','Permiso de Proteccion Temporal','Cedula Extranjeria','Pasaporte','NIT') ,
 `NOM_USER` varchar(15) ,
 `APE_USER` varchar(20) ,
 `TEL_USER` varchar(15) ,
 `CORREO_USER` varchar(45) ,
 `ROL_USER` enum('Cliente','Administrador','Jefe de patio') ,
 `EST_USER` tinyint(4) ,
 `CREATED_AT` datetime ,
 `UPDATE_AT` datetime ,
 `ID_ROL_USER` int(20) ,
 `ID_ROL` int(11) ,
 `NOM_ROL` varchar(45) 
)*/;

/*Table structure for table `v_inventarioproveedor` */

DROP TABLE IF EXISTS `v_inventarioproveedor`;

/*!50001 DROP VIEW IF EXISTS `v_inventarioproveedor` */;
/*!50001 DROP TABLE IF EXISTS `v_inventarioproveedor` */;

/*!50001 CREATE TABLE  `v_inventarioproveedor`(
 `ID_INVENTARIO` int(11) ,
 `NOMBRE_PROD_INVE` varchar(45) ,
 `PROVEEDOR_ID_PROVEDOR` int(11) ,
 `NOM_PROV` varchar(45) ,
 `EST_PROV` tinyint(4) 
)*/;

/*Table structure for table `v_jefes` */

DROP TABLE IF EXISTS `v_jefes`;

/*!50001 DROP VIEW IF EXISTS `v_jefes` */;
/*!50001 DROP TABLE IF EXISTS `v_jefes` */;

/*!50001 CREATE TABLE  `v_jefes`(
 `ID_USER` int(12) ,
 `NUMERO_DOC` int(20) ,
 `TIPO_DOC` enum('Cedula Ciudadania','Tarjeta Identidad','Permiso de Proteccion Temporal','Cedula Extranjeria','Pasaporte','NIT') ,
 `NOM_USER` varchar(15) ,
 `APE_USER` varchar(20) ,
 `TEL_USER` varchar(15) ,
 `CORREO_USER` varchar(45) ,
 `ROL_USER` enum('Cliente','Administrador','Jefe de patio') ,
 `EST_USER` tinyint(4) ,
 `CREATED_AT` datetime ,
 `UPDATE_AT` datetime ,
 `ID_ROL_USER` int(20) ,
 `ID_ROL` int(11) ,
 `NOM_ROL` varchar(45) 
)*/;

/*Table structure for table `v_productosinv` */

DROP TABLE IF EXISTS `v_productosinv`;

/*!50001 DROP VIEW IF EXISTS `v_productosinv` */;
/*!50001 DROP TABLE IF EXISTS `v_productosinv` */;

/*!50001 CREATE TABLE  `v_productosinv`(
 `ID_PRODUCTO` int(11) ,
 `NOM_PROD` varchar(45) ,
 `DESCRIPCION_PROD` varchar(45) ,
 `ID_INVENTARIO` int(11) ,
 `NOMBRE_PROD_INVE` varchar(45) ,
 `NOM_PROV` varchar(45) ,
 `ID_PROVEDOR` int(11) 
)*/;

/*Table structure for table `v_roluser` */

DROP TABLE IF EXISTS `v_roluser`;

/*!50001 DROP VIEW IF EXISTS `v_roluser` */;
/*!50001 DROP TABLE IF EXISTS `v_roluser` */;

/*!50001 CREATE TABLE  `v_roluser`(
 `ID_USER` int(12) ,
 `NUMERO_DOC` int(20) ,
 `TIPO_DOC` enum('Cedula Ciudadania','Tarjeta Identidad','Permiso de Proteccion Temporal','Cedula Extranjeria','Pasaporte','NIT') ,
 `NOM_USER` varchar(15) ,
 `APE_USER` varchar(20) ,
 `EST_USER` tinyint(4) ,
 `NOM_ROL` varchar(45) ,
 `DESCRIPCION` varchar(80) 
)*/;

/*View structure for view v_admin */

/*!50001 DROP TABLE IF EXISTS `v_admin` */;
/*!50001 DROP VIEW IF EXISTS `v_admin` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_admin` AS (select `usuario`.`ID_USER` AS `ID_USER`,`usuario`.`NUMERO_DOC` AS `NUMERO_DOC`,`usuario`.`TIPO_DOC` AS `TIPO_DOC`,`usuario`.`NOM_USER` AS `NOM_USER`,`usuario`.`APE_USER` AS `APE_USER`,`usuario`.`TEL_USER` AS `TEL_USER`,`usuario`.`CORREO_USER` AS `CORREO_USER`,`usuario`.`ROL_USER` AS `ROL_USER`,`usuario`.`EST_USER` AS `EST_USER`,`usuario`.`CREATED_AT` AS `CREATED_AT`,`usuario`.`UPDATE_AT` AS `UPDATE_AT`,`usuario`.`ID_ROL_USER` AS `ID_ROL_USER`,`rol`.`ID_ROL` AS `ID_ROL`,`rol`.`NOM_ROL` AS `NOM_ROL` from (`usuario` join `rol` on(`usuario`.`ROL_USER` = `rol`.`NOM_ROL`)) where `rol`.`ID_ROL` = '2') */;

/*View structure for view v_citas */

/*!50001 DROP TABLE IF EXISTS `v_citas` */;
/*!50001 DROP VIEW IF EXISTS `v_citas` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_citas` AS (select `servicio`.`NOM_SERV` AS `NOM_SERV`,`servicio`.`DESCP_SERV` AS `DESCP_SERV`,`servicio`.`ID_SERVICIO` AS `ID_SERVICIO`,`cita`.`ID_CITA` AS `ID_CITA`,`cita`.`EST_CITA` AS `EST_CITA`,`cita`.`FECHA_CITA` AS `FECHA_CITA`,`usuario`.`NOM_USER` AS `NOM_USER`,`usuario`.`APE_USER` AS `APE_USER`,`usuario`.`NUMERO_DOC` AS `NUMERO_DOC`,`usuario`.`ROL_USER` AS `ROL_USER` from ((`servicio` join `cita` on(`servicio`.`ID_SERVICIO` = `cita`.`SERVICIO_ID_SERVICIO`)) join `usuario` on(`cita`.`USUARIO_ID_USUARIO` = `usuario`.`ID_USER`))) */;

/*View structure for view v_clientes */

/*!50001 DROP TABLE IF EXISTS `v_clientes` */;
/*!50001 DROP VIEW IF EXISTS `v_clientes` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_clientes` AS (select `usuario`.`ID_USER` AS `ID_USER`,`usuario`.`NUMERO_DOC` AS `NUMERO_DOC`,`usuario`.`TIPO_DOC` AS `TIPO_DOC`,`usuario`.`NOM_USER` AS `NOM_USER`,`usuario`.`APE_USER` AS `APE_USER`,`usuario`.`TEL_USER` AS `TEL_USER`,`usuario`.`CORREO_USER` AS `CORREO_USER`,`usuario`.`ROL_USER` AS `ROL_USER`,`usuario`.`EST_USER` AS `EST_USER`,`usuario`.`CREATED_AT` AS `CREATED_AT`,`usuario`.`UPDATE_AT` AS `UPDATE_AT`,`usuario`.`ID_ROL_USER` AS `ID_ROL_USER`,`rol`.`ID_ROL` AS `ID_ROL`,`rol`.`NOM_ROL` AS `NOM_ROL` from (`usuario` join `rol` on(`usuario`.`ROL_USER` = `rol`.`NOM_ROL`)) where `rol`.`ID_ROL` = '1') */;

/*View structure for view v_inventarioproveedor */

/*!50001 DROP TABLE IF EXISTS `v_inventarioproveedor` */;
/*!50001 DROP VIEW IF EXISTS `v_inventarioproveedor` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_inventarioproveedor` AS (select `inventario`.`ID_INVENTARIO` AS `ID_INVENTARIO`,`inventario`.`NOMBRE_PROD_INVE` AS `NOMBRE_PROD_INVE`,`inventario`.`PROVEEDOR_ID_PROVEDOR` AS `PROVEEDOR_ID_PROVEDOR`,`proveedor`.`NOM_PROV` AS `NOM_PROV`,`proveedor`.`EST_PROV` AS `EST_PROV` from (`inventario` join `proveedor` on(`inventario`.`PROVEEDOR_ID_PROVEDOR` = `proveedor`.`ID_PROVEDOR`))) */;

/*View structure for view v_jefes */

/*!50001 DROP TABLE IF EXISTS `v_jefes` */;
/*!50001 DROP VIEW IF EXISTS `v_jefes` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_jefes` AS (select `usuario`.`ID_USER` AS `ID_USER`,`usuario`.`NUMERO_DOC` AS `NUMERO_DOC`,`usuario`.`TIPO_DOC` AS `TIPO_DOC`,`usuario`.`NOM_USER` AS `NOM_USER`,`usuario`.`APE_USER` AS `APE_USER`,`usuario`.`TEL_USER` AS `TEL_USER`,`usuario`.`CORREO_USER` AS `CORREO_USER`,`usuario`.`ROL_USER` AS `ROL_USER`,`usuario`.`EST_USER` AS `EST_USER`,`usuario`.`CREATED_AT` AS `CREATED_AT`,`usuario`.`UPDATE_AT` AS `UPDATE_AT`,`usuario`.`ID_ROL_USER` AS `ID_ROL_USER`,`rol`.`ID_ROL` AS `ID_ROL`,`rol`.`NOM_ROL` AS `NOM_ROL` from (`usuario` join `rol` on(`usuario`.`ROL_USER` = `rol`.`NOM_ROL`)) where `rol`.`ID_ROL` = '3') */;

/*View structure for view v_productosinv */

/*!50001 DROP TABLE IF EXISTS `v_productosinv` */;
/*!50001 DROP VIEW IF EXISTS `v_productosinv` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_productosinv` AS (select `productos`.`ID_PRODUCTO` AS `ID_PRODUCTO`,`productos`.`NOM_PROD` AS `NOM_PROD`,`productos`.`DESCRIPCION_PROD` AS `DESCRIPCION_PROD`,`inventario`.`ID_INVENTARIO` AS `ID_INVENTARIO`,`inventario`.`NOMBRE_PROD_INVE` AS `NOMBRE_PROD_INVE`,`proveedor`.`NOM_PROV` AS `NOM_PROV`,`proveedor`.`ID_PROVEDOR` AS `ID_PROVEDOR` from ((`productos` join `inventario` on(`productos`.`INVENTARIO_ID_INVENTARIO` = `inventario`.`ID_INVENTARIO`)) join `proveedor` on(`inventario`.`PROVEEDOR_ID_PROVEDOR` = `proveedor`.`ID_PROVEDOR`)) order by `proveedor`.`NOM_PROV`) */;

/*View structure for view v_roluser */

/*!50001 DROP TABLE IF EXISTS `v_roluser` */;
/*!50001 DROP VIEW IF EXISTS `v_roluser` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_roluser` AS (select `usuario`.`ID_USER` AS `ID_USER`,`usuario`.`NUMERO_DOC` AS `NUMERO_DOC`,`usuario`.`TIPO_DOC` AS `TIPO_DOC`,`usuario`.`NOM_USER` AS `NOM_USER`,`usuario`.`APE_USER` AS `APE_USER`,`usuario`.`EST_USER` AS `EST_USER`,`rol`.`NOM_ROL` AS `NOM_ROL`,`permisos`.`DESCRIPCION` AS `DESCRIPCION` from (((`usuario` join `rol` on(`usuario`.`ROL_USER` = `rol`.`NOM_ROL`)) join `permisos_has_rol` on(`rol`.`ID_ROL` = `permisos_has_rol`.`ROL_ID_ROL`)) join `permisos` on(`permisos_has_rol`.`PERMISOS_ID_PERMISO` = `permisos`.`ID_PERMISO`)) order by `usuario`.`ID_USER`) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
