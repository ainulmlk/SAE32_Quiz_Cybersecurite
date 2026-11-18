<?php
// Reçoit {question_id, reponse_id}, renvoie si c'est juste + la correction.
require __DIR__ . '/../includes/db.php';
if ($_SERVER['REQUEST_METHOD'] !== 'POST') json_out(['error' => 'POST attendu'], 405);
$in  = json_decode(file_get_contents('php://input'), true) ?: [];
$qid = (int)($in['question_id'] ?? 0);
$rid = (int)($in['reponse_id'] ?? 0);
if (!$qid || !$rid) json_out(['error' => 'requête invalide'], 400);
try {
    $st = db()->prepare('SELECT rep_id, verifie, explications FROM Reponse WHERE question_id = ?');
    $st->execute([$qid]);
    $rows = $st->fetchAll();
    if (!$rows) json_out(['error' => 'question inconnue'], 404);
    $correct = []; $why = [];
    foreach ($rows as $r) {
        if ($r['verifie']) {
            $correct[] = (int)$r['rep_id'];
            if (trim((string)$r['explications']) !== '') $why[] = trim($r['explications']);
        }
    }
    json_out(['ok' => in_array($rid, $correct, true), 'correct' => $correct, 'why' => implode(' ', $why)]);
} catch (Throwable $e) {
    json_out(['error' => 'server'], 500);
}
