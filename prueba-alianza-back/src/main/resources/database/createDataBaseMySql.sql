DROP USER IF EXISTS 'alianza'@'localhost';
CREATE USER IF NOT EXISTS 'alianza'@'localhost' IDENTIFIED BY 'alianza123';

/**
* ESQUEMA/BASE DE DATOS : prueba_alianza
**/
CREATE DATABASE IF NOT EXISTS prueba_alianza;
GRANT ALL PRIVILEGES ON prueba_alianza.* TO 'alianza'@'localhost';
FLUSH PRIVILEGES;
USE prueba_alianza;

/*
* Tabla: habilidad
*/
CREATE TABLE IF NOT EXISTS customer
(
    shared_key	VARCHAR(25) PRIMARY KEY,
    id_business VARCHAR(50) NOT NULL,
    email 		VARCHAR(50) NOT NULL,
    phone 		VARCHAR(10) NOT NULL,
    startDate	DATE NOT NULL,
    endDate		DATE NOT NULL,
    date_added 	DATE NOT NULL
    );