# Installation : site web + base de données (Debian / Ubuntu)

## 1. Paquets
```bash
sudo apt update
sudo apt install apache2 php libapache2-mod-php php-mysql mariadb-server
```

## 2. Base de données
```bash
sudo mysql < db/schema.sql        # crée la base quiz_db et les tables
sudo mysql < db/seed.sql          # thèmes + 10 questions de départ

sudo mysql -e "CREATE USER 'quiz'@'localhost' IDENTIFIED BY 'CHOISIR_UN_MOT_DE_PASSE';
               GRANT SELECT ON quiz_db.* TO 'quiz'@'localhost';
               FLUSH PRIVILEGES;"
```
Le site n'a besoin que de lire (SELECT). Le programme Java, qui crée, modifie et supprime des questions, utilisera un autre compte avec les droits d'écriture.

## 3. Site
```bash
sudo cp -r index.html quiz.html resultat.html style.css data.js api includes export /var/www/html/
```
Puis modifier le mot de passe dans `/var/www/html/includes/db.php` (constante `DB_PASS`).
Le site doit être à la racine du serveur pour que le JSON soit à `http://IP/export`.

## 4. Vérifier
```bash
curl http://localhost/api/questions.php     # 10 questions, sans les bonnes réponses
curl http://localhost/export/               # toutes les questions au format du sujet
```
Puis ouvrir `http://IP/` dans un navigateur et faire le quiz.

## Dépannage
- `{"error":"server"}` : base arrêtée, mauvais mot de passe ou mauvais nom de base dans `includes/db.php`. Voir `/var/log/apache2/error.log`.
- Page blanche ou « Impossible de charger les questions » : Apache ou PHP non démarré, ou ouverture du fichier HTML directement (double-clic) au lieu de `http://`.
