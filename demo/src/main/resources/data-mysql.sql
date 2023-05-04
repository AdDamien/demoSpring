INSERT INTO `pays` (`id`, `nom`) VALUES
(1, 'France'),
(2, 'Allemagne'),
(3,'Espagne'),
(4, 'Danemark');

INSERT INTO `entreprise` (`id`, `nom`) VALUES
(1, 'Boulanger'),
(2, 'Metz Numeric School'),
(3,'Dev Factory'),
(4, 'SFR');

INSERT INTO `emploi` (`id`, `nom`) VALUES
(1, 'Boulanger'),
(2, 'Cuisinier'),
(3,'Charpentier'),
(4, 'Developpeur');
INSERT INTO `role` (`id`, `nom`) VALUES
(1, 'ROLE_ADMINISTRATEUR'),
(2, 'ROLE_UTILISATEUR');

INSERT INTO utilisateur (prenom , nom , age ,  pays_id , entreprise_id,email,mot_de_passe,role_id,created_At,updated_at) VALUES
("John","DOE" , 44 , 1,4,"ab@a.com","$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q",1,UTC_TIMESTAMP(),UTC_TIMESTAMP()),
("KEVIN","Joyeux",26 , 2,2,"a5@a.com","$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q",1,UTC_TIMESTAMP(),UTC_TIMESTAMP()),
("LUIGI","Castelli",30 , 4,2,"ar@a.com","$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q",1,UTC_TIMESTAMP(),UTC_TIMESTAMP()),
("DAMIEN","ADAM",35 , 3,2,"a7@a.com","$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q",2,UTC_TIMESTAMP(),UTC_TIMESTAMP());

INSERT INTO `recherche_emploi_utilisateur` (`utilisateur_id`, `emploi_id`) VALUES
(1, 1),
(1, 2),
(2,2);
