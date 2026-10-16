package quizz;

import java.util.ArrayList;
import java.util.List;

/**
 * Un quizz : une liste de questions et le score du joueur.
 * JSON : { "quizz": [ question* ] }
 */
public class Quizz {

    private List<Question> questions = new ArrayList<>();
    private int score;

    public List<Question> getQuestions() {
        return questions;
    }

    /** Ajoute une question au quizz. */
    public void ajouterQuestion(Question q) {
        // TODO
    }

    /**
     * Vérifie la réponse du joueur ("c'est mon dernier mot") et met à jour le score.
     * @return true si la réponse est juste
     */
    public boolean repondre(List<Reponse> choix) {
        // TODO
        return false;
    }

    public int getScore() {
        return score;
    }
}
