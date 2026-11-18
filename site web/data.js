/* ===== Quiz Cyber : accès au serveur (PHP + base de données) ===== */
const QUIZ_LENGTH = 10;

/* Échappe le texte venant de la base avant de l'insérer dans du HTML
   (les questions importées d'autres sites ne sont pas de confiance). */
const esc = s => String(s).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));

async function apiQuestions(){
  const r = await fetch('api/questions.php');
  if (!r.ok) throw new Error('questions');
  return (await r.json()).questions;
}
async function apiVerify(questionId, reponseId){
  const r = await fetch('api/verifier.php', {
    method: 'POST', headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({question_id: questionId, reponse_id: reponseId})
  });
  if (!r.ok) throw new Error('verifier');
  return r.json();   // {ok, correct:[ids], why}
}

/* Données d'exemple pour la page résultat quand aucun quiz n'a été fait */
const DEMO_QUESTIONS = [
  {q:"Que signifie le sigle HTTPS ?", choices:["HyperText Transfer Protocol Secure","High Transfer Text Protocol System","HyperText Transmission Private Service","Host Transfer Protocol Secure"], correct:0, why:"Le « S » indique que les échanges sont chiffrés avec TLS."},
  {q:"Quel type d'attaque consiste à tromper l'utilisateur avec un faux e-mail ?", choices:["DDoS","Phishing","Brute force","Sniffing"], correct:1, why:"Le phishing (hameçonnage) imite un expéditeur de confiance."},
  {q:"Quel port utilise SSH par défaut ?", choices:["21","23","22","80"], correct:2, why:"SSH utilise le port TCP 22."},
  {q:"Quelle option rend un mot de passe le plus robuste ?", choices:["Un mot courant","Sa date de naissance","Une phrase de passe longue et unique","Le même partout"], correct:2, why:"La longueur et l'unicité comptent plus que la complexité."},
  {q:"Que fait un pare-feu (firewall) ?", choices:["Accélère le réseau","Filtre le trafic selon des règles","Chiffre les disques","Supprime les virus"], correct:1, why:"Il autorise ou bloque les flux selon des règles définies."},
  {q:"Que signifie « 2FA » ?", choices:["Deux fichiers actifs","Double firewall avancé","Authentification à deux facteurs","Deuxième flux automatique"], correct:2, why:"Un second facteur (code, clé de sécurité) s'ajoute au mot de passe."},
  {q:"Quel protocole traduit un nom de domaine en adresse IP ?", choices:["DHCP","DNS","SMTP","ARP"], correct:1, why:"DNS associe les noms de domaine aux adresses IP."},
  {q:"Qu'est-ce qu'une injection SQL ?", choices:["Un virus réseau","Une requête malveillante insérée dans une saisie","Un bug du navigateur","Une panne de base de données"], correct:1, why:"Les requêtes préparées permettent de s'en protéger."},
  {q:"Quel est le rôle d'un VPN ?", choices:["Créer un tunnel chiffré","Bloquer la publicité","Augmenter le débit","Nettoyer le disque"], correct:0, why:"Le trafic est chiffré entre l'appareil et le serveur VPN."},
  {q:"Que fait un ransomware ?", choices:["Vole des mots de passe","Chiffre les données contre rançon","Ralentit le Wi-Fi","Installe des publicités"], correct:1, why:"Il rend les fichiers inaccessibles jusqu'au paiement d'une rançon."}
];

/* ===== Stockage navigateur (correction + meilleur score) ===== */
const QuizStore = {
  saveReview(r){ try{ sessionStorage.setItem('qc-review', JSON.stringify(r)); }catch(e){} },
  loadReview(){
    try{ const r = JSON.parse(sessionStorage.getItem('qc-review')); return Array.isArray(r) && r.length ? r : null; }
    catch(e){ return null; }
  },
  getBest(){ try{ const v = localStorage.getItem('qc-best'); return v === null ? null : +v; }catch(e){ return null; } },
  saveScore(s){ try{ const b = this.getBest(); if (b === null || s > b) localStorage.setItem('qc-best', s); }catch(e){} }
};
