<?php
// Renvoie 10 questions au hasard SANS indiquer les bonnes réponses.
require __DIR__ . '/../includes/db.php';
try {
    $pdo = db();
    $ids = $pdo->query('SELECT que_id FROM Question')->fetchAll(PDO::FETCH_COLUMN);
    shuffle($ids);
    $ids = array_slice($ids, 0, 10);
    if (!$ids) json_out(['questions' => []]);

    $in = implode(',', array_fill(0, count($ids), '?'));
    $st = $pdo->prepare("SELECT q.que_id, q.type, q.enonce, t.name AS theme
                         FROM Question q JOIN Theme t ON t.theme_id = q.theme_id
                         WHERE q.que_id IN ($in)");
    $st->execute($ids);
    $qs = [];
    foreach ($st as $r) {
        $qs[(int)$r['que_id']] = ['id' => (int)$r['que_id'], 'q' => $r['enonce'], 'type' => $r['type'],
                                  'theme' => $r['theme'], 'choices' => []];
    }
    $st = $pdo->prepare("SELECT rep_id, question_id, contenu FROM Reponse WHERE question_id IN ($in) ORDER BY rep_id");
    $st->execute($ids);
    foreach ($st as $r) {
        $qs[(int)$r['question_id']]['choices'][] = ['id' => (int)$r['rep_id'], 'text' => $r['contenu']];
    }
    json_out(['questions' => array_map(fn($id) => $qs[$id], $ids)]);
} catch (Throwable $e) {
    json_out(['error' => 'server'], 500);
}
