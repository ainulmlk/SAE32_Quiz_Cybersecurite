package quizz;

import java.util.ArrayList;
import java.util.List;

/**
 * Une question du quizz.
 * JSON : { "type": ..., "enonce": ..., "reponses": [ reponse* ] }
 */
public class Question {

    private int id;                                   // identifiant dans la base de données
    private String enonce;                            // texte de la question
    private String type;                              // ex. "choix multiple", "vrai/faux"
    private List<Reponse> reponses = new ArrayList<>();

    public Question(int id, String enonce, String type) {
        this.id = id;
        this.enonce = enonce;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getEnonce() {
        return enonce;
    }

    public String getType() {
        return type;
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    /** Ajoute une réponse possible à cette question. */
    public void ajouterReponse(Reponse r) {
        // TODO
    }

    /**
     * Vérifie les réponses cochées par le joueur.
     * @return true si elles correspondent exactement aux bonnes réponses
     */
    public boolean verifier(List<Reponse> choix) {
        // TODO
        return false;
    }
}
