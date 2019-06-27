CREATE TABLE ResponsableFiliere(
	IDResponable int NOT NULL AUTO_INCREMENT,
	Nom varchar(255) NOT NULL,
	Prenom varchar(255) NOT NULL,
	DateNaissance date,
	Adresse varchar(255),
	Email varchar(255) NOT NULL,
	Telephone varchar(13),
	Sexe ENUM('M', 'F') NOT NULL,
	Login varchar(32) NOT NULL,
	Password varchar(255) NOT NULL,
	PRIMARY KEY (IDResponable),
	UNIQUE(Login)
);