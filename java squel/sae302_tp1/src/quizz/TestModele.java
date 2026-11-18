package quizz;

import java.util.ArrayList;
import java.util.List;

/**
 * Petit test sans base de données : vérifie Question.verifier et Quizz.repondre.
 * À lancer avec : java -cp bin quizz.TestModele
 */
public class TestModele {

    private int echecs = 0;

    private void verifie(String nom, boolean attendu, boolean obtenu) {
        String etat = (attendu == obtenu) ? "OK  " : "ECHEC";
        if (attendu != obtenu) echecs++;
        System.out.println(etat + " " + nom);
    }

    public static void main(String[] args) {
        new TestModele().lancer();   // seule méthode static : le point d'entrée
    }

    public void lancer() {
        Reponse longue  = new Reponse("Il est long (12 caractères ou plus)", true);
        Reponse unique   = new Reponse("Il est unique pour chaque site", true);
        Reponse prenom   = new Reponse("Il contient mon prénom", false);
        Reponse reutilise = new Reponse("Je le réutilise partout", false);

        Question q1 = new Question(1, "Quelles pratiques rendent un mot de passe plus sûr ?", "choix_multiple");
        q1.ajouterReponse(longue);
        q1.ajouterReponse(unique);
        q1.ajouterReponse(prenom);
        q1.ajouterReponse(reutilise);

        // --- Question.verifier ---
        verifie("toutes les bonnes réponses => juste", true, q1.verifier(List.of(longue, unique)));
        verifie("une bonne réponse manquante => faux", false, q1.verifier(List.of(longue)));
        verifie("une mauvaise réponse cochée => faux", false, q1.verifier(List.of(longue, unique, prenom)));
        verifie("rien de coché => faux", false, q1.verifier(new ArrayList<>()));

        // --- Quizz.repondre ---
        // Question vrai/faux : la bonne réponse est « Faux »
        Question q2 = new Question(2, "Le HTTPS garantit que le site est honnête.", "vrai_faux");
        q2.ajouterReponse(new Reponse("Vrai", false));
        q2.ajouterReponse(new Reponse("Faux", true));

        Quizz quizz = new Quizz();
        quizz.ajouterQuestion(q1);
        quizz.ajouterQuestion(q2);

        verifie("question courante = la première", true, quizz.questionCourante() == q1);
        verifie("repondre juste (q1)", true, quizz.repondre(List.of(longue, unique)));
        verifie("score = 1 après une bonne réponse", true, quizz.getScore() == 1);
        verifie("question courante = la deuxième", true, quizz.questionCourante() == q2);
        verifie("repondre faux (q2)", false, quizz.repondre(new ArrayList<>()));
        verifie("score toujours 1 après une mauvaise réponse", true, quizz.getScore() == 1);
        verifie("quizz terminé : plus de question", true, quizz.questionCourante() == null);

        System.out.println(echecs == 0 ? "\nTous les tests passent." : "\n" + echecs + " test(s) en échec.");
    }
}
