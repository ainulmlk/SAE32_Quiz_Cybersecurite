package quizz;

import java.sql.SQLException;
import java.util.List;

/**
 * Accès à la base de données (création, lecture, modification, suppression des questions).
 * Le SGBD reste à choisir (par exemple SQLite en local, via JDBC).
 */
public class BaseQuestions {

    private String url;   // ex. "jdbc:sqlite:quizz.db"

    public BaseQuestions(String url) {
        this.url = url;
    }

    /** Enregistre une nouvelle question avec ses réponses. */
    public void creer(Question q) throws SQLException {
        // TODO
    }

    /** Retourne toutes les questions de la base. */
    public List<Question> lireToutes() throws SQLException {
        // TODO
        return null;
    }

    /** Modifie une question existante. */
    public void modifier(Question q) throws SQLException {
        // TODO
    }

    /** Supprime la question qui a cet identifiant. */
    public void supprimer(int id) throws SQLException {
        // TODO
    }
}
