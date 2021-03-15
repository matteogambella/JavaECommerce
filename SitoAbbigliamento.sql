CREATE DATABASE IF NOT EXISTS `sitoabbigliamento`;

USE `sitoabbigliamento`;

DROP TABLE IF EXISTS `Prodotti`;
CREATE TABLE `Prodotti`(
    nomeprodotto   VARCHAR(50) NOT NULL,
    sesso          VARCHAR(5)  NOT NULL,
    categoria      VARCHAR(50) NOT NULL,
    marca          VARCHAR(50) NOT NULL,
    prezzo         DOUBLE      NOT NULL,
	fotomodello    VARCHAR(80) NOT NULL,
    altezzamodello DOUBLE      NOT NULL,
    tagliamodello  VARCHAR(3)  NOT NULL,
	colore1        VARCHAR(50) NOT NULL,
    colore2        VARCHAR(50) NOT NULL,
    colore3        VARCHAR(50) NOT NULL,
PRIMARY KEY (nomeprodotto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Ordine`;
CREATE TABLE `Ordine`(
    codiceordine        INT(11) NOT NULL AUTO_INCREMENT,
    cliente             VARCHAR(50) NOT NULL,
    datainvioordine     VARCHAR(50) NOT NULL,
    nomespedizione      VARCHAR(80) NOT NULL,
    indirizzospedizione VARCHAR(80) NOT NULL,
    cap                 VARCHAR(10) NOT NULL,
    cittaspedizione     VARCHAR(50) NOT NULL,
PRIMARY KEY(codiceordine)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ArticoloOrdine`;
CREATE TABLE `ArticoloOrdine`(
    codiceordine  INT NOT NULL,
    nomeprodotto  VARCHAR(50) NOT NULL,
    colore        VARCHAR(50) NOT NULL,
    quantita      INT         NOT NULL,
    taglia        VARCHAR(3)  NOT NULL,
    voto          INT         ,
PRIMARY KEY(codiceordine,nomeprodotto,colore,taglia)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `GuidaTaglie`;
CREATE TABLE `GuidaTaglie`(
    marca         VARCHAR(50) NOT NULL,
    macrocategoria VARCHAR(50) NOT NULL,
    sesso         VARCHAR(5)  NOT NULL,
    taglia        VARCHAR(3)  NOT NULL,
    valoremisura1 DOUBLE      NOT NULL,
    valoremisura2 DOUBLE      NOT NULL,
    valoremisura3 DOUBLE      NOT NULL,
PRIMARY KEY(marca,macrocategoria,sesso,taglia)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `AssociaCategoria`;
CREATE TABLE `AssociaCategoria`(
    categoria VARCHAR(50) NOT NULL,
    macrocategoria VARCHAR(50) NOT NULL,
PRIMARY KEY(categoria,macrocategoria)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
    

INSERT INTO Prodotti VALUES

('Ultra shorts','Uomo','Shorts','Gymshark',22.00,'foto_ultra_shorts.jpg',1.86,'XL','bianco','nero','grigio'),
('Ultra jacquard shorts','Uomo','Shorts','Gymshark',25.00,'foto_ultra_jacquard_shorts.jpg',1.80,'L','bianco','nero','grigio'),
('Crucial shorts','Uomo','Shorts','Gymshark',20.00,'foto_crucial_shorts.jpg',1.80,'L','bianco','nero','grigio'),
('Dual band shorts','Donna','Shorts','Gymshark',20.00,'foto_dual_band_shorts.jpg',1.68,'M','grigio','rosso','bianco'),
('Elevate cycling shorts','Donna','Shorts','Gymshark',22.00,'foto_elevate_cycling_shorts.jpg',1.70,'S','rosa','viola','bianco'),

('Divide T-shirt','Uomo','T-Shirt','Gymshark',25.00,'foto_divide_tshirt.jpg',1.85,'L','rosso','verde','blu'),
('Ultra jacquard T-Shirt','Uomo','T-Shirt','Gymshark',22.00,'foto_ultra_jacquard_tshirt.jpg',1.87,'L','bianco','nero','grigio'),
('Essential T-Shirt','Donna','T-Shirt','Gymshark',22.00,'foto_essential_tshirt.jpg',1.71,'M','verde','ocra','beige'),
('Flawless Knit T-Shirt','Donna','T-Shirt','Gymshark',20.00,'foto_flawless_knit_tshirt.jpg',1.72,'M','rosso','azzurro','blu'),
('True Texture Vest','Donna','T-Shirt','Gymshark',25.00,'foto_true_texture_vest_tshirt.jpg',1.70,'S','bianco','nero','rosso');

INSERT INTO guidataglie values

('Gymshark','Bottom','Uomo','S',81,99,79),
('Gymshark','Bottom','Uomo','M',86,104,80),
('Gymshark','Bottom','Uomo','L',91,109,81),
('Gymshark','Bottom','Uomo','XL',96,114,82),
('Gymshark','Bottom','Uomo','XXL',101,119,83),
('Gymshark','Top','Uomo','S',100,79,90),
('Gymshark','Top','Uomo','M',105,84,95),
('Gymshark','Top','Uomo','L',110,89,100),
('Gymshark','Top','Uomo','XL',115,94,105),
('Gymshark','Top','Uomo','XXL',120,99,110),
('Gymshark','Bottom','Donna','S',60,99,75),
('Gymshark','Bottom','Donna','M',65,104,76),
('Gymshark','Bottom','Donna','L',70,109,77),
('Gymshark','Bottom','Donna','XL',75,114,78),
('Gymshark','Bottom','Donna','XXL',80,119,79),
('Gymshark','Top','Donna','S',84,60,86),
('Gymshark','Top','Donna','M',89,65,91),
('Gymshark','Top','Donna','L',94,70,96),
('Gymshark','Top','Donna','XL',99,75,101),
('Gymshark','Top','Donna','XXL',104,80,106);

INSERT INTO AssociaCategoria values

('Shorts','Bottom'),
('T-Shirt','Top');

INSERT INTO Ordine (cliente,datainvioordine,nomespedizione,indirizzospedizione,cap,cittaspedizione) VALUES 
('mattgamb@hotmail.com','2019-02-14','Matteo gambella','Via corte braccini 8','56125','Pisa'),
('mattgamb@hotmail.com','2019-02-14','Matteo gambella','Via corte braccini 8','56125','Pisa'),
('erikabruni@outlook.com','2019-01-10','Erika Bruni','Via galanti 6','56125','Pisa'),
('erikabruni@outlook.com','2019-02-01','Erika Bruni','Via galanti 6','56125','Pisa'),
('alessandromarchi@outlook.com','2019-02-01','Alessandro marchi','Via galanti 10','56125','Pisa'),
('alessandromarchi@outlook.com','2019-02-10','Alessandro marchi','Via galanti 10','56125','Pisa');

INSERT into ArticoloOrdine(codiceordine,nomeprodotto,colore,quantita,taglia,voto) 
VALUES
(2,"Ultra Shorts","nero",4,"XS",null),
(2,"Ultra Shorts","bianco",1,"L",4),
(2,"Ultra Shorts","grigio",1,"L",5),
(1,"Essential T-Shirt","ocra",4,"S",null),
(1,"Crucial shorts","bianco",3,"S",null),
(1,"Crucial shorts","nero",4,"M",null),
(3,"Essential T-Shirt","verde",3,"S",null),
(3,"True Texture Vest","nero",2,"S",null),
(4,"True Texture Vest","bianco",12,"M",4),
(4,"Flawless Knit T-Shirt","azzurro",3,"L",3),
(4,"Dual band shorts","grigio",1,"S",4),
(4,"Elevate cycling shorts","rosa",7,"S",null),
(5,"Divide T-shirt","rosso",6,"L",null),
(5,"Divide T-shirt","verde",3,"XL",3),
(6,"Ultra jacquard shorts","bianco",10,"L",4),
(6,"Ultra jacquard shorts","nero",8,"M",null),
(6,"Ultra jacquard T-Shirt","nero",8,"M",null),
(6,"Ultra jacquard T-Shirt","bianco",6,"L",null);



    

