-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 02 jan. 2019 à 19:26
-- Version du serveur :  10.1.26-MariaDB
-- Version de PHP :  7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `etablissement`
--

-- --------------------------------------------------------

--
-- Structure de la table `administrateur`
--

CREATE TABLE `administrateur` (
  `idAdmin` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `dateNaissance` date NOT NULL,
  `sexe` char(1) NOT NULL,
  `adresse` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `administrateur`
--

INSERT INTO `administrateur` (`idAdmin`, `nom`, `prenom`, `dateNaissance`, `sexe`, `adresse`, `email`, `telephone`, `login`, `password`) VALUES
(1, 'Diop', 'Abdou', '2018-12-23', 'M', 'Mbao', 'neardiop@gmail.com', '78&', 'bb', 'bb');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `idEtudiant` int(5) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `dateNaissance` date NOT NULL,
  `sexe` char(1) NOT NULL,
  `email` varchar(150) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `adresse` varchar(150) NOT NULL,
  `numEtudiant` int(11) NOT NULL,
  `nomFiliere` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`idEtudiant`, `nom`, `prenom`, `dateNaissance`, `sexe`, `email`, `telephone`, `adresse`, `numEtudiant`, `nomFiliere`) VALUES
(1, 'DIOP', 'Ndiane', '1997-11-08', 'M', 'GrandMbao', 'neardiop@gmail.com', '781654704', 12, 'LPTI'),
(2, 'FALL', 'Binta', '2018-12-19', 'F', 'Mbour', 'binta@gmail.com', '774567882', 13, 'LPTI'),
(3, 'Cv', 're', '2018-11-27', 'F', 'ew', 'vwf', '23212', 2, 'LPTI'),
(4, 'DIOP', 'jh', '2018-12-05', 'M', 'kj', 'dfgd', '1234', 14, 'LPTI'),
(5, 'NDIAYE', 'Issa', '2018-12-12', 'M', 'Keur M Fall', 'issa@gmail.com', '771234212', 18, 'LPTI'),
(8, 'A', 'A', '2018-11-29', 'A', 'D', 'D', '122', 22, 'LPTI'),
(11, 'FALL', 'Amadou', '2018-12-11', 'M', 'Keur Massar', 'amFall@gmail.com', '781452321', 8, 'RT'),
(12, 'dq', 'sqfc', '2018-12-12', 'q', 'qrfs', 'dfg', '1233', 21, 'DAR'),
(13, 'cet', 'tv', '2019-01-24', 'M', 'dvg', 'dxcfv', '23', 23, 'ASR');

-- --------------------------------------------------------

--
-- Structure de la table `filiere`
--

CREATE TABLE `filiere` (
  `idFiliere` int(11) NOT NULL,
  `intitule` varchar(100) NOT NULL,
  `numFiliere` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `filiere`
--

INSERT INTO `filiere` (`idFiliere`, `intitule`, `numFiliere`) VALUES
(4, 'RT', 3),
(6, 'DAR', 1),
(7, 'ASR', 2),
(9, 'SAN', 4),
(10, 'INGC', 5);

-- --------------------------------------------------------

--
-- Structure de la table `formateur`
--

CREATE TABLE `formateur` (
  `idFormateur` int(5) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `dateNaissance` date NOT NULL,
  `sexe` char(1) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `formateur`
--

INSERT INTO `formateur` (`idFormateur`, `nom`, `prenom`, `dateNaissance`, `sexe`, `email`, `telephone`, `status`, `login`, `password`) VALUES
(1, 'HOUSSEIN', 'Abdek', '1998-07-04', 'm', 'abdek.hpusssein@gmail.com', '775921398', 1, 'abdek', '12345');

-- --------------------------------------------------------

--
-- Structure de la table `module`
--

CREATE TABLE `module` (
  `id` int(11) NOT NULL,
  `intitule` varchar(100) NOT NULL,
  `coef` double NOT NULL,
  `numModule` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `module`
--

INSERT INTO `module` (`id`, `intitule`, `coef`, `numModule`) VALUES
(2, 'JAVA', 5, 12),
(3, 'HTML', 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `modulefiliere`
--

CREATE TABLE `modulefiliere` (
  `id` int(11) NOT NULL,
  `idfiliere` int(11) NOT NULL,
  `idModule` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `modulefiliere`
--

INSERT INTO `modulefiliere` (`id`, `idfiliere`, `idModule`) VALUES
(6, 4, 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `administrateur`
--
ALTER TABLE `administrateur`
  ADD PRIMARY KEY (`idAdmin`),
  ADD UNIQUE KEY `login` (`login`);

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`idEtudiant`),
  ADD UNIQUE KEY `numEtudiant` (`numEtudiant`);

--
-- Index pour la table `filiere`
--
ALTER TABLE `filiere`
  ADD PRIMARY KEY (`idFiliere`),
  ADD UNIQUE KEY `numFiliere` (`numFiliere`);

--
-- Index pour la table `formateur`
--
ALTER TABLE `formateur`
  ADD PRIMARY KEY (`idFormateur`),
  ADD UNIQUE KEY `login` (`login`);

--
-- Index pour la table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `numModule` (`numModule`);

--
-- Index pour la table `modulefiliere`
--
ALTER TABLE `modulefiliere`
  ADD PRIMARY KEY (`id`),
  ADD KEY `filiere` (`idfiliere`),
  ADD KEY `idModule` (`idModule`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `administrateur`
--
ALTER TABLE `administrateur`
  MODIFY `idAdmin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `idEtudiant` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `filiere`
--
ALTER TABLE `filiere`
  MODIFY `idFiliere` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `formateur`
--
ALTER TABLE `formateur`
  MODIFY `idFormateur` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `module`
--
ALTER TABLE `module`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `modulefiliere`
--
ALTER TABLE `modulefiliere`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `modulefiliere`
--
ALTER TABLE `modulefiliere`
  ADD CONSTRAINT `modulefiliere_ibfk_1` FOREIGN KEY (`idfiliere`) REFERENCES `filiere` (`idFiliere`),
  ADD CONSTRAINT `modulefiliere_ibfk_2` FOREIGN KEY (`idModule`) REFERENCES `module` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
