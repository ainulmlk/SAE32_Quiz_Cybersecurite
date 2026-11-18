<?php
// http://IP/export : publie les questions du site au format du sujet.
// (Question_Autres / Reponse_Autre = questions importées : on ne les republie pas.)
require __DIR__ . '/../includes/db.php';
try {
    $pdo = db();
    $reps = [];
    foreach ($pdo->query('SELECT question_id, contenu, explications, verifie FROM Reponse ORDER BY rep_id') as $r) {
        $reps[$r['question_id']][] = ['reponse' => [
            'type'         => 'texte',                 // pas de colonne "type" dans Reponse : valeur fixe
            'contenu'      => $r['contenu'],
            'valide'       => (bool)$r['verifie'],
            'explications' => $r['explications'],      // champ en plus, autorisé ("au minimum")
        ]];
    }
    $quizz = [];
    foreach ($pdo->query('SELECT que_id, type, enonce FROM Question ORDER BY que_id') as $q) {
        $quizz[] = ['question' => ['type' => $q['type'], 'enonce' => $q['enonce'], 'reponses' => $reps[$q['que_id']] ?? []]];
    }
    json_out(['quizz' => $quizz]);
} catch (Throwable $e) {
    json_out(['error' => 'server'], 500);
}
