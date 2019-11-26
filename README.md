# Projet_JEE
M1 APP - Projet JEE - Agilan COLBERT &amp; Kévin RATTINAM

/********************/
/* Cible : JDBC - V1*/
/********************/

/*On supprime les tables si elles existent */
DROP TABLE EMPLOYES;

/*Cr�ation de la table EMPLOYES*/
CREATE TABLE "EMPLOYES" (
	"ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	"NOM" VARCHAR(25) NOT NULL,
	"PRENOM" VARCHAR(25) NOT NULL,
	"TELDOM" VARCHAR(10) NOT NULL,
	"TELPORT" VARCHAR(10) NOT NULL,
	"TELPRO" VARCHAR(10) NOT NULL,
	"ADRESSE" VARCHAR(150) NOT NULL,
	"CODEPOSTAL" VARCHAR(5) NOT NULL,
	"VILLE" VARCHAR(25) NOT NULL,
	"EMAIL" VARCHAR(25) NOT NULL,
	CONSTRAINT primary_key_empl PRIMARY KEY (ID)
);

/*Insertion de 4 membres*/
INSERT INTO EMPLOYES(NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES
('Turing','Alan','0123456789','0612345678','0698765432','2 rue des Automates','92700','Colombes','aturing@efrei.fr'),
('Galois','Evariste','0145362787','0645362718','0611563477','21 rue des Morts-trop-jeunes','92270','Bois-colombes','egalois@efrei.fr'),
('Boole','George','0187665987','0623334256','0654778654','65 rue de la Logique','92700','Colombes','gboole@efrei.fr'),
('Gauss','Carl Friedrich','0187611987','0783334256','0658878654','6 rue des Transformations','75016','Paris','cgauss@efrei.fr'),
('Pascal','Blaise','0187384987','0622494256','0674178654','39 bvd de Port-Royal','21000','Dijon','bpascal@efrei.fr'),
('Euler','Leonhard','0122456678','0699854673','0623445166','140 avenue Complexe','90000','Nanterre','leuler@efrei.fr');

/*On supprime les tables si elles existent */
DROP TABLE UTILISATEUR;

/*Création de la table UTILISATEUR*/
CREATE TABLE "UTILISATEUR" (
"ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	"LOGIN" VARCHAR(25) NOT NULL,
	"PASSWORD" VARCHAR(25) NOT NULL,
	CONSTRAINT primary_key_user PRIMARY KEY (ID)
);

/*Insertion de l'utilisateur admin*/
INSERT INTO UTILISATEUR(LOGIN,PASSWORD) VALUES('admin','admin');
/************************************************************************************************************************************/
/**************************/
/* Cible : MySQL - V2 & V3*/
/**************************/

DROP TABLE IF EXISTS UTILISATEUR;

CREATE TABLE UTILISATEUR
(
	ID INT NOT NULL AUTO_INCREMENT,
	LOGIN VARCHAR(25) not null,
	PASSWORD VARCHAR(25) not null,
    PRIMARY KEY(ID)
)
;
INSERT INTO UTILISATEUR(LOGIN,PASSWORD) VALUES ('admin','admin');
INSERT INTO UTILISATEUR(LOGIN,PASSWORD) VALUES ('empl','empl');

DROP TABLE IF EXISTS EMPLOYES;

CREATE TABLE EMPLOYES(
	ID INT NOT NULL AUTO_INCREMENT,
	NOM VARCHAR(25),
	PRENOM VARCHAR(25),
    TELDOM VARCHAR(10),
	TELPORT VARCHAR(10),
	TELPRO VARCHAR(10),
	ADRESSE VARCHAR(150),
	CODEPOSTAL VARCHAR(15),
	VILLE VARCHAR(25),
	EMAIL VARCHAR(25),
    PRIMARY KEY (ID)
);

/*Insertion de 4 membres*/
INSERT INTO EMPLOYES(NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES
('Bond','James','0123456789','0612345678','0698765432','2 avenue 007','92700','Colombes','jbond@gmail.com');
INSERT INTO EMPLOYES(NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES
('Jones','Indiana','0145362787','0645362718','0611563477','10 rue des Aventuriers','92270','Bois-colombes','ijones@gmail.com');
INSERT INTO EMPLOYES(NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES
('Bourne','Jason','0187665987','0623334256','0654778654','65 rue Agent Secret Perdu','92700','Colombes','jbourne@yahoo.fr');
INSERT INTO EMPLOYES(NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES
('Stark','Arya','0187611987','0783334256','0658878654','6 rue Sans-Nom','75016','Paris','astark@nord.com');
INSERT INTO EMPLOYES(NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES
('Lanister','Cersei','0187384987','0622494256','0674178654','5 bvd des Reines','21000','Dijon','clanister@mail.co.uk');
