<?php
// Page de diagnostic : http://localhost/quiz/test_db.php   (à SUPPRIMER une fois que tout marche)
header('Content-Type: text/plain; charset=utf-8');
require __DIR__ . '/includes/db.php';
$opts = [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION];

echo "Utilisateur lu dans includes/db.php : '" . DB_USER . "'  (mot de passe " . (DB_PASS === '' ? 'vide' : 'renseigné') . ")\n\n";

echo "1. Connexion à MySQL ... ";
try { $srv = new PDO('mysql:host=localhost;charset=utf8mb4', DB_USER, DB_PASS, $opts); echo "OK\n"; }
catch (Throwable $e) {
    echo "ECHEC\n   " . $e->getMessage() . "\n\n";
    echo "=> MySQL n'est pas démarré dans XAMPP, ou DB_USER / DB_PASS sont faux.\n";
    echo "   Avec XAMPP : DB_USER = 'root' et DB_PASS = '' (vide).\n";
    exit;
}

echo "2. Base quiz_db ... ";
$found = $srv->query("SHOW DATABASES LIKE 'quiz_db'")->fetchColumn();
if (!$found) { echo "INTROUVABLE\n\n=> Importe db/schema.sql puis db/seed.sql dans phpMyAdmin (onglet Importer).\n"; exit; }
echo "OK\n";

echo "3. Connexion à quiz_db ... ";
try { $pdo = db(); echo "OK\n"; }
catch (Throwable $e) { echo "ECHEC\n   " . $e->getMessage() . "\n\n=> Vérifie la ligne DB_DSN dans includes/db.php (dbname=quiz_db).\n"; exit; }

echo "4. Tables et données ...\n";
foreach (['Theme', 'Question', 'Reponse'] as $t) {
    try { echo "   $t : " . $pdo->query("SELECT COUNT(*) FROM `$t`")->fetchColumn() . " lignes\n"; }
    catch (Throwable $e) { echo "   $t : MANQUANTE\n   " . $e->getMessage() . "\n"; }
}
echo "\nAttendu : Theme 6, Question 10, Reponse 40 (avec seed.sql).\nSi tout est OK, essaie http://localhost/quiz/api/questions.php\n";
