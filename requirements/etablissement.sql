-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  ven. 01 fév. 2019 à 11:51
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
  `sexe` char(1) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `numAdmin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `administrateur`
--

INSERT INTO `administrateur` (`idAdmin`, `nom`, `prenom`, `sexe`, `login`, `password`, `numAdmin`) VALUES
(1, 'Diop', 'Abdou', 'M', 'admin', 'admin', 2);

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `idEtudiant` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `dateNaissance` date NOT NULL,
  `sexe` varchar(1) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `adresse` varchar(50) NOT NULL,
  `numEtudiant` int(11) NOT NULL,
  `nomFiliere` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`idEtudiant`, `nom`, `prenom`, `dateNaissance`, `sexe`, `email`, `telephone`, `adresse`, `numEtudiant`, `nomFiliere`) VALUES
(2, 'DIOP', 'Ndiane', '2019-01-08', 'M', 'neard@gmail.com', '23546', 'Mbao', 2, 'DAR'),
(3, 'SALL', 'Fatou', '2019-01-02', 'F', 'fatou@gmail.com', '23546', 'Mbao', 6, 'DAR'),
(34, 'Tall', 'Madani', '2019-01-01', 'M', 'tall@gmail.com', '3243546', 'Parcelle A', 55, 'RT'),
(35, 'Houssein', 'Abdek', '2019-01-01', 'M', 'tall@gmail.com', '3243546', 'Parcelle A', 35, 'RT'),
(36, 'Sow', 'Cheikh', '2019-01-01', 'M', 'tall@gmail.com', '3243546', 'Parcelle A', 15, 'RT');

-- --------------------------------------------------------

--
-- Structure de la table `evaluation`
--

CREATE TABLE `evaluation` (
  `id` int(11) NOT NULL,
  `forme` varchar(50) NOT NULL,
  `typeEval` varchar(50) NOT NULL,
  `intitule` varchar(50) NOT NULL,
  `coef` double NOT NULL,
  `module` varchar(50) NOT NULL,
  `dateEvaluation` date NOT NULL,
  `numEvaluation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `evaluation`
--

INSERT INTO `evaluation` (`id`, `forme`, `typeEval`, `intitule`, `coef`, `module`, `dateEvaluation`, `numEvaluation`) VALUES
(1, 'Control continu', 'TP', 'TP1', 0.6, 'JAVA', '2019-02-06', 1),
(2, 'Examen Final', 'Oral', 'Oral1', 0.4, 'JAVA', '2019-01-30', 3);

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
  `sexe` char(1) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `statut` tinyint(1) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `numFormateur` int(11) NOT NULL,
  `adresse` varchar(50) NOT NULL,
  `connect` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `formateur`
--

INSERT INTO `formateur` (`idFormateur`, `nom`, `prenom`, `sexe`, `email`, `telephone`, `statut`, `login`, `password`, `numFormateur`, `adresse`, `connect`) VALUES
(6, 'AKINOCHO', 'Ghislain', 'M', 'aiougi@gmail.com', '7826871', 1, 'formateur', 'formateur', 12, 'Dakar', 0),
(7, 'Dieng', 'Abdoulaye', 'M', 'aiougi@gmail.com', '7826871', 1, 'cd', 'c', 1, 'Dakar', 0),
(8, 'Preira', 'J Mari', 'M', 'aiougi@gmail.com', '7826871', 1, 'preira', 'preira', 3, 'Dakar', 0);

-- --------------------------------------------------------

--
-- Structure de la table `formateurmodule`
--

CREATE TABLE `formateurmodule` (
  `id` int(11) NOT NULL,
  `idFormateur` int(11) NOT NULL,
  `idModuleFiliere` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `formateurmodule`
--

INSERT INTO `formateurmodule` (`id`, `idFormateur`, `idModuleFiliere`) VALUES
(1, 6, 6);

-- --------------------------------------------------------

--
-- Structure de la table `linkresponsablefiliere`
--

CREATE TABLE `linkresponsablefiliere` (
  `id` int(11) NOT NULL,
  `idResponsable` int(11) NOT NULL,
  `idFiliere` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `linkresponsablefiliere`
--

INSERT INTO `linkresponsablefiliere` (`id`, `idResponsable`, `idFiliere`) VALUES
(1, 2, 4),
(2, 3, 6);

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
(3, 'HTML', 3, 1),
(4, 'PHP', 4, 2);

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
(6, 6, 2);

-- --------------------------------------------------------

--
-- Structure de la table `notes`
--

CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `idEvaluation` int(11) NOT NULL,
  `idEtudiant` int(11) NOT NULL,
  `note` double NOT NULL,
  `commentaire` varchar(50) NOT NULL,
  `valider` tinyint(1) NOT NULL,
  `numNote` int(11) NOT NULL,
  `noteFoisCoef` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `notes`
--

INSERT INTO `notes` (`id`, `idEvaluation`, `idEtudiant`, `note`, `commentaire`, `valider`, `numNote`, `noteFoisCoef`) VALUES
(1, 1, 2, 19, 'Trés Bien', 1, 1, 11.4),
(2, 1, 3, 12, 'Passable', 1, 3, 7.199999999999999),
(3, 2, 2, 14, 'Bien', 1, 4, 5.6000000000000005),
(4, 2, 3, 19, 'Bien', 1, 5, 7.6000000000000005);

-- --------------------------------------------------------

--
-- Structure de la table `responsablefiliere`
--

CREATE TABLE `responsablefiliere` (
  `IdResponsable` int(11) NOT NULL,
  `Nom` varchar(255) NOT NULL,
  `Prenom` varchar(255) NOT NULL,
  `Adresse` varchar(255) DEFAULT NULL,
  `Email` varchar(255) NOT NULL,
  `Telephone` varchar(13) DEFAULT NULL,
  `Sexe` enum('M','F') NOT NULL,
  `Login` varchar(32) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `numResponsable` int(11) NOT NULL,
  `connect` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `responsablefiliere`
--

INSERT INTO `responsablefiliere` (`IdResponsable`, `Nom`, `Prenom`, `Adresse`, `Email`, `Telephone`, `Sexe`, `Login`, `Password`, `numResponsable`, `connect`) VALUES
(2, 'Abdou', 'diop', 'ug', 'jh', '098', 'M', 'T', 'T', 2, 0),
(3, 'Fall', 'Omar', 'Dakar', 'omar@gmail.com', '56', 'M', 'omar', 'omar', 22, 0);

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
  ADD PRIMARY KEY (`idEtudiant`);

--
-- Index pour la table `evaluation`
--
ALTER TABLE `evaluation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `numEvaluation` (`numEvaluation`);

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
  ADD UNIQUE KEY `login` (`login`),
  ADD UNIQUE KEY `numFormateur` (`numFormateur`);

--
-- Index pour la table `formateurmodule`
--
ALTER TABLE `formateurmodule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idFormateur` (`idFormateur`),
  ADD KEY `idModuleFiliere` (`idModuleFiliere`);

--
-- Index pour la table `linkresponsablefiliere`
--
ALTER TABLE `linkresponsablefiliere`
  ADD PRIMARY KEY (`id`);

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
-- Index pour la table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idEtudiant` (`idEtudiant`),
  ADD KEY `idEvaluation` (`idEvaluation`);

--
-- Index pour la table `responsablefiliere`
--
ALTER TABLE `responsablefiliere`
  ADD PRIMARY KEY (`IdResponsable`),
  ADD UNIQUE KEY `Login` (`Login`);

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
  MODIFY `idEtudiant` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT pour la table `evaluation`
--
ALTER TABLE `evaluation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `filiere`
--
ALTER TABLE `filiere`
  MODIFY `idFiliere` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `formateur`
--
ALTER TABLE `formateur`
  MODIFY `idFormateur` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `formateurmodule`
--
ALTER TABLE `formateurmodule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `linkresponsablefiliere`
--
ALTER TABLE `linkresponsablefiliere`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `module`
--
ALTER TABLE `module`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `modulefiliere`
--
ALTER TABLE `modulefiliere`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `responsablefiliere`
--
ALTER TABLE `responsablefiliere`
  MODIFY `IdResponsable` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `formateurmodule`
--
ALTER TABLE `formateurmodule`
  ADD CONSTRAINT `formateurmodule_ibfk_1` FOREIGN KEY (`idFormateur`) REFERENCES `formateur` (`idFormateur`),
  ADD CONSTRAINT `formateurmodule_ibfk_2` FOREIGN KEY (`idModuleFiliere`) REFERENCES `modulefiliere` (`id`);

--
-- Contraintes pour la table `modulefiliere`
--
ALTER TABLE `modulefiliere`
  ADD CONSTRAINT `modulefiliere_ibfk_1` FOREIGN KEY (`idfiliere`) REFERENCES `filiere` (`idFiliere`),
  ADD CONSTRAINT `modulefiliere_ibfk_2` FOREIGN KEY (`idModule`) REFERENCES `module` (`id`);

--
-- Contraintes pour la table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`idEtudiant`) REFERENCES `etudiant` (`idEtudiant`),
  ADD CONSTRAINT `notes_ibfk_2` FOREIGN KEY (`idEvaluation`) REFERENCES `evaluation` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
