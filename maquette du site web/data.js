/* ===== Données du quiz (partagées par index, quiz et résultat) =====
   q = énoncé · choices = réponses · correct = index de la bonne réponse · why = explication
   Plus tard : remplacez ce tableau par les données de la base (PHP -> JSON). */
const QUESTIONS = [
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

/* ===== Stockage navigateur (maquette sans serveur) ===== */
const QuizStore = {
  saveAnswers(a){ try{ sessionStorage.setItem('qc-answers', JSON.stringify(a)); }catch(e){} },
  loadAnswers(){
    try{
      const a = JSON.parse(sessionStorage.getItem('qc-answers'));
      return Array.isArray(a) && a.length === QUESTIONS.length ? a : null;
    }catch(e){ return null; }
  },
  getBest(){ try{ const v = localStorage.getItem('qc-best'); return v === null ? null : +v; }catch(e){ return null; } },
  saveScore(s){ try{ const b = this.getBest(); if (b === null || s > b) localStorage.setItem('qc-best', s); }catch(e){} }
};
