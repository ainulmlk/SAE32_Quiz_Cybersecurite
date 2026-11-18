SET NAMES utf8mb4;
USE quiz_db;

-- Thèmes (ceux du sujet). "category" et "difficulty" : valeurs provisoires à confirmer.
INSERT INTO Theme (theme_id, category, difficulty, name) VALUES
  (1, 'Hygiène informatique', 'Facile', 'Architecture d''Internet'),
  (2, 'Hygiène informatique', 'Facile', 'Mots de passe'),
  (3, 'Hygiène informatique', 'Facile', 'Sécurité du matériel'),
  (4, 'Hygiène informatique', 'Facile', 'Sécurité en ligne'),
  (5, 'Hygiène informatique', 'Facile', 'Sécurité des données'),
  (6, 'Hygiène informatique', 'Facile', 'Courrier électronique');

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (1, 'qcm', 'Que signifie le sigle HTTPS ?', 4);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (1, 'HyperText Transfer Protocol Secure', 'Le « S » indique que les échanges sont chiffrés avec TLS.', 1),
  (1, 'High Transfer Text Protocol System', NULL, 0),
  (1, 'HyperText Transmission Private Service', NULL, 0),
  (1, 'Host Transfer Protocol Secure', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (2, 'qcm', 'Quel type d''attaque consiste à tromper l''utilisateur avec un faux e-mail ?', 6);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (2, 'DDoS', NULL, 0),
  (2, 'Phishing', 'Le phishing (hameçonnage) imite un expéditeur de confiance.', 1),
  (2, 'Brute force', NULL, 0),
  (2, 'Sniffing', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (3, 'qcm', 'Quel port utilise SSH par défaut ?', 1);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (3, '21', NULL, 0),
  (3, '23', NULL, 0),
  (3, '22', 'SSH utilise le port TCP 22.', 1),
  (3, '80', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (4, 'qcm', 'Quelle option rend un mot de passe le plus robuste ?', 2);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (4, 'Un mot courant', NULL, 0),
  (4, 'Sa date de naissance', NULL, 0),
  (4, 'Une phrase de passe longue et unique', 'La longueur et l''unicité comptent plus que la complexité.', 1),
  (4, 'Le même partout', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (5, 'qcm', 'Que fait un pare-feu (firewall) ?', 1);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (5, 'Accélère le réseau', NULL, 0),
  (5, 'Filtre le trafic selon des règles', 'Il autorise ou bloque les flux selon des règles définies.', 1),
  (5, 'Chiffre les disques', NULL, 0),
  (5, 'Supprime les virus', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (6, 'qcm', 'Que signifie « 2FA » ?', 2);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (6, 'Deux fichiers actifs', NULL, 0),
  (6, 'Double firewall avancé', NULL, 0),
  (6, 'Authentification à deux facteurs', 'Un second facteur (code, clé de sécurité) s''ajoute au mot de passe.', 1),
  (6, 'Deuxième flux automatique', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (7, 'qcm', 'Quel protocole traduit un nom de domaine en adresse IP ?', 1);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (7, 'DHCP', NULL, 0),
  (7, 'DNS', 'DNS associe les noms de domaine aux adresses IP.', 1),
  (7, 'SMTP', NULL, 0),
  (7, 'ARP', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (8, 'qcm', 'Qu''est-ce qu''une injection SQL ?', 5);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (8, 'Un virus réseau', NULL, 0),
  (8, 'Une requête malveillante insérée dans une saisie', 'Les requêtes préparées permettent de s''en protéger.', 1),
  (8, 'Un bug du navigateur', NULL, 0),
  (8, 'Une panne de base de données', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (9, 'qcm', 'Quel est le rôle d''un VPN ?', 4);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (9, 'Créer un tunnel chiffré', 'Le trafic est chiffré entre l''appareil et le serveur VPN.', 1),
  (9, 'Bloquer la publicité', NULL, 0),
  (9, 'Augmenter le débit', NULL, 0),
  (9, 'Nettoyer le disque', NULL, 0);

INSERT INTO Question (que_id, type, enonce, theme_id) VALUES (10, 'qcm', 'Que fait un ransomware ?', 5);
INSERT INTO Reponse (question_id, contenu, explications, verifie) VALUES
  (10, 'Vole des mots de passe', NULL, 0),
  (10, 'Chiffre les données contre rançon', 'Il rend les fichiers inaccessibles jusqu''au paiement d''une rançon.', 1),
  (10, 'Ralentit le Wi-Fi', NULL, 0),
  (10, 'Installe des publicités', NULL, 0);

