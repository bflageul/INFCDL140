
CREATE DATABASE IF NOT EXISTS `agrodb` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE `agrodb`;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS job;

-- Site (situation géographique)
CREATE TABLE city (
    id INT NOT NULL UNIQUE auto_increment,
    cityName VARCHAR(255) NOT NULL UNIQUE
);

-- Service
CREATE TABLE job (
    id INT NOT NULL UNIQUE auto_increment,
    jobName VARCHAR(255) NOT NULL UNIQUE
);

-- Salariés
CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    cellphone VARCHAR(20),
    mail VARCHAR(255) UNIQUE,
    jobId INT NOT NULL,
    cityId INT NOT NULL,
    FOREIGN KEY (jobId) REFERENCES job (id),
    FOREIGN KEY (cityId) REFERENCES city (id)
 );

-- ENGINE=InnoDB DEFAULT CHARSET=latin1;
insert into city(id, cityName) values
(1, 'Paris'),
(2, 'Nantes'),
(3, 'Toulouse'),
(4, 'Nice'),
(5, 'Lille');

insert into job(id, jobName) values
(1, 'Comptabilité'),
(2, 'Production'),
(3, 'Accueil'),
(4, 'Informatique'),
(5, 'Commercial');

insert into employee(id, firstname, lastname, phone, cellphone, mail, jobId, cityId) values
(1,'Carine','Schmitt', '0298989898', '0698989898', 'carine@agro.fr',1,1),
(2,'King','Jean', '0298000000', '0698980000', 'jean@agro.fr',2,4);



