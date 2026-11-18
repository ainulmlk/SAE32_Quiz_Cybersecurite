CREATE DATABASE IF NOT EXISTS quiz_db CHARACTER SET utf8mb4;   -- (seul ajout : l'encodage, pour les accents)

USE quiz_db;

-- TABLE 1: THEME
CREATE TABLE Theme (
    theme_id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    difficulty VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL
);

-- TABLE 2: QUESTION
CREATE TABLE Question (
    que_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    enonce TEXT NOT NULL,
    theme_id INT NOT NULL,

    FOREIGN KEY (theme_id)
        REFERENCES Theme(theme_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- TABLE 3: REPONSE
CREATE TABLE Reponse (
    rep_id INT AUTO_INCREMENT PRIMARY KEY,
    contenu TEXT NOT NULL,
    explications TEXT,
    verifie BOOLEAN NOT NULL DEFAULT FALSE,
    question_id INT NOT NULL,

    FOREIGN KEY (question_id)
        REFERENCES Question(que_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- TABLE 4: QUESTION AUTRES
CREATE TABLE Question_Autres (
    queAut_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    enonce TEXT NOT NULL,
    theme_id INT NOT NULL,

    FOREIGN KEY (theme_id)
        REFERENCES Theme(theme_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- TABLE 5: REPONSE AUTRE
CREATE TABLE Reponse_Autre (
    repAutr_id INT AUTO_INCREMENT PRIMARY KEY,
    contenu TEXT NOT NULL,
    explications TEXT,
    verifie BOOLEAN NOT NULL DEFAULT FALSE,
    queAut_id INT NOT NULL,

    FOREIGN KEY (queAut_id)
        REFERENCES Question_Autres(queAut_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
