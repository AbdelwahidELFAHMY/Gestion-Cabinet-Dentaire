-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 11 oct. 2024 à 19:24
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `cabinet_dentaire`
--

-- --------------------------------------------------------

--
-- Structure de la table `consultation`
--

DROP TABLE IF EXISTS `consultation`;
CREATE TABLE IF NOT EXISTS `consultation` (
  `id_consultation` int NOT NULL AUTO_INCREMENT,
  `dateConsultation` date NOT NULL,
  `traitementPrescrit` text,
  `description` text NOT NULL,
  `id_maladie` int NOT NULL,
  `id_patient` int NOT NULL,
  `id_rendez_vous` int NOT NULL,
  `id_dentiste` int NOT NULL,
  PRIMARY KEY (`id_consultation`),
  KEY `fk_consultation_maladie` (`id_maladie`),
  KEY `fk_consultation_patient` (`id_patient`),
  KEY `fk_consultation_rendezvous` (`id_rendez_vous`),
  KEY `fk_consultation_dentiste` (`id_dentiste`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `consultation`
--

INSERT INTO `consultation` (`id_consultation`, `dateConsultation`, `traitementPrescrit`, `description`, `id_maladie`, `id_patient`, `id_rendez_vous`, `id_dentiste`) VALUES
(1, '2024-06-10', 'Ibuprofen 200mg 3 fois par jour', 'Consultation de routine', 1, 1, 1, 1),
(2, '2024-06-11', 'Paracetamol 500mg en cas de douleur', 'Traitement de la gingivite', 2, 2, 2, 2);

-- --------------------------------------------------------

--
-- Structure de la table `dentiste`
--

DROP TABLE IF EXISTS `dentiste`;
CREATE TABLE IF NOT EXISTS `dentiste` (
  `id_dentiste` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `CIN` varchar(50) NOT NULL,
  `specialite` varchar(50) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_dentiste`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `dentiste`
--

INSERT INTO `dentiste` (`id_dentiste`, `nom`, `prenom`, `adresse`, `CIN`, `specialite`, `telephone`) VALUES
(1, 'El Mansouri', 'Rachid', '123 Rue de la Liberté, Casablanca', 'AB123456', 'Orthodontiste', '0612345678'),
(2, 'Bennani', 'Amina', '456 Avenue des Fleurs, Rabat', 'CD789012', 'Endodontiste', '0698765432');

-- --------------------------------------------------------

--
-- Structure de la table `maladie`
--

DROP TABLE IF EXISTS `maladie`;
CREATE TABLE IF NOT EXISTS `maladie` (
  `id_maladie` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `symptomes` text,
  `id_medicament` int NOT NULL,
  PRIMARY KEY (`id_maladie`),
  KEY `fk_maladie_medicament` (`id_medicament`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `maladie`
--

INSERT INTO `maladie` (`id_maladie`, `nom`, `symptomes`, `id_medicament`) VALUES
(1, 'Caries', 'Douleur, Sensibilité', 1),
(2, 'Gingivite', 'Rougeur, Saignement', 2),
(3, 'Abcès dentaire', 'Douleur intense, Gonflement', 3);

-- --------------------------------------------------------

--
-- Structure de la table `medicament`
--

DROP TABLE IF EXISTS `medicament`;
CREATE TABLE IF NOT EXISTS `medicament` (
  `id_medicament` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `dosage` varchar(50) NOT NULL,
  `quantiteStock` int DEFAULT '0',
  `prixUnitaire` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id_medicament`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `medicament`
--

INSERT INTO `medicament` (`id_medicament`, `nom`, `dosage`, `quantiteStock`, `prixUnitaire`) VALUES
(1, 'Ibuprofen', '200mg', 100, 5.00),
(2, 'Paracetamol', '500mg', 200, 3.00),
(3, 'Amoxicilline', '250mg', 150, 7.50);

-- --------------------------------------------------------

--
-- Structure de la table `ordonnance`
--

DROP TABLE IF EXISTS `ordonnance`;
CREATE TABLE IF NOT EXISTS `ordonnance` (
  `id_ordonnance` int NOT NULL AUTO_INCREMENT,
  `dateEmission` date NOT NULL,
  `dureeValidite` int DEFAULT NULL,
  `posologie` text,
  `id_consultation` int NOT NULL,
  PRIMARY KEY (`id_ordonnance`),
  KEY `fk_ordonnance_consultation` (`id_consultation`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ordonnance`
--

INSERT INTO `ordonnance` (`id_ordonnance`, `dateEmission`, `dureeValidite`, `posologie`, `id_consultation`) VALUES
(1, '2024-06-10', 30, 'Ibuprofen 200mg 3 fois par jour pendant 7 jours', 1),
(2, '2024-06-11', 30, 'Paracetamol 500mg en cas de douleur pendant 5 jours', 2);

-- --------------------------------------------------------

--
-- Structure de la table `patient`
--

DROP TABLE IF EXISTS `patient`;
CREATE TABLE IF NOT EXISTS `patient` (
  `id_patient` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `numeroSecuriteSociale` varchar(20) DEFAULT NULL,
  `adresse` text,
  `telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `CIN` varchar(10) NOT NULL,
  PRIMARY KEY (`id_patient`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `patient`
--

INSERT INTO `patient` (`id_patient`, `nom`, `prenom`, `numeroSecuriteSociale`, `adresse`, `telephone`, `CIN`) VALUES
(1, 'Boukhari', 'Mohamed', '123456780', '789 Rue des Palmiers, Marrakech', '0654321098', 'EF345678'),
(2, 'El Idrissi', 'Fatima', '987654321', '321 Boulevard Hassan II, Fès', '0678901234', 'GH901234'),
(3, 'Bouaadi', 'abdellah', '0877655', 'naybuybyub hbhj', '6655443322223', 'ib980988');

-- --------------------------------------------------------

--
-- Structure de la table `rendezvous`
--

DROP TABLE IF EXISTS `rendezvous`;
CREATE TABLE IF NOT EXISTS `rendezvous` (
  `id_rendez_vous` int NOT NULL AUTO_INCREMENT,
  `id_patient` int NOT NULL,
  `id_dentiste` int NOT NULL,
  `notes` text NOT NULL,
  `dateRendezVous` date NOT NULL,
  `heureRendezVous` time NOT NULL,
  PRIMARY KEY (`id_rendez_vous`),
  KEY `fk_rendezvous_patient` (`id_patient`),
  KEY `fk_rendezvous_dentiste` (`id_dentiste`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `rendezvous`
--

INSERT INTO `rendezvous` (`id_rendez_vous`, `id_patient`, `id_dentiste`, `notes`, `dateRendezVous`, `heureRendezVous`) VALUES
(1, 1, 1, 'Control annuel', '2024-06-10', '09:00:00'),
(2, 2, 2, 'Douleur persistante', '2024-06-11', '10:30:00');

-- --------------------------------------------------------

--
-- Structure de la table `secretaire`
--

DROP TABLE IF EXISTS `secretaire`;
CREATE TABLE IF NOT EXISTS `secretaire` (
  `id_secretaire` int NOT NULL AUTO_INCREMENT,
  `Nom` varchar(50) NOT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `CIN` varchar(50) NOT NULL,
  PRIMARY KEY (`id_secretaire`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `secretaire`
--

INSERT INTO `secretaire` (`id_secretaire`, `Nom`, `prenom`, `adresse`, `telephone`, `CIN`) VALUES
(1, 'Lahmar', 'Salma', 'Rue Al Kindi, Casablanca', '0623456789', 'IJ567890'),
(2, 'Moulay', 'Nadia', 'Avenue Al Massira, Rabat', '0634567890', 'KL678901');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `consultation`
--
ALTER TABLE `consultation`
  ADD CONSTRAINT `fk_consultation_dentiste` FOREIGN KEY (`id_dentiste`) REFERENCES `dentiste` (`id_dentiste`),
  ADD CONSTRAINT `fk_consultation_maladie` FOREIGN KEY (`id_maladie`) REFERENCES `maladie` (`id_maladie`),
  ADD CONSTRAINT `fk_consultation_patient` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id_patient`),
  ADD CONSTRAINT `fk_consultation_rendezvous` FOREIGN KEY (`id_rendez_vous`) REFERENCES `rendezvous` (`id_rendez_vous`);

--
-- Contraintes pour la table `maladie`
--
ALTER TABLE `maladie`
  ADD CONSTRAINT `fk_maladie_medicament` FOREIGN KEY (`id_medicament`) REFERENCES `medicament` (`id_medicament`);

--
-- Contraintes pour la table `ordonnance`
--
ALTER TABLE `ordonnance`
  ADD CONSTRAINT `fk_ordonnance_consultation` FOREIGN KEY (`id_consultation`) REFERENCES `consultation` (`id_consultation`);

--
-- Contraintes pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD CONSTRAINT `fk_rendezvous_dentiste` FOREIGN KEY (`id_dentiste`) REFERENCES `dentiste` (`id_dentiste`),
  ADD CONSTRAINT `fk_rendezvous_patient` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id_patient`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
