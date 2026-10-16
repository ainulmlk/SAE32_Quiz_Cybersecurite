package quizz;

/**
 * Une réponse possible à une question.
 * JSON : { "type": ..., "contenu": ..., "valide": ... }
 */
public class Reponse {

    private String contenu;   // texte de la réponse
    private boolean valide;   // true si c'est une bonne réponse

    public Reponse(String contenu, boolean valide) {
        this.contenu = contenu;
        this.valide = valide;
    }

    public String getContenu() {
        return contenu;
    }

    public boolean isValide() {
        return valide;
    }
}
