package quizz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseQuestions {

    private String url;   // ex. "jdbc:sqlite:quizz.db"

    public BaseQuestions(String url) {
        this.url = url;
    }

    /** Ouvre une connexion. SQLite demande d'activer les clés étrangères à chaque connexion. */
    private Connection connexion() throws SQLException {
        Connection c = DriverManager.getConnection(url);
        try (Statement st = c.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return c;
    }

    /** Crée les tables si elles n'existent pas. À appeler une fois au démarrage. */
    public void initialiser() throws SQLException {
        try (Connection c = connexion(); Statement st = c.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS question ("
                     + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                     + "enonce TEXT NOT NULL, type TEXT NOT NULL)");
            st.execute("CREATE TABLE IF NOT EXISTS reponse ("
                     + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                     + "question_id INTEGER NOT NULL, "
                     + "contenu TEXT NOT NULL, valide INTEGER NOT NULL, "
                     + "FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE)");
        }
    }

    /** Enregistre une nouvelle question avec ses réponses. */
    public void creer(Question q) throws SQLException {
        try (Connection c = connexion()) {
            c.setAutoCommit(false);   // tout ou rien : une seule transaction
            try {
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO question(enonce, type) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, q.getEnonce());
                    ps.setString(2, q.getType());
                    ps.executeUpdate();
                    try (ResultSet cles = ps.getGeneratedKeys()) {
                        if (cles.next()) {
                            q.setId(cles.getInt(1));   // on récupère l'id créé par la base
                        }
                    }
                }
                insererReponses(c, q);
                c.commit();
            } catch (SQLException e) {
                c.rollback();         // en cas d'erreur, on annule tout
                throw e;
            }
        }
    }

    /** Retourne toutes les questions de la base. */
    public List<Question> lireToutes() throws SQLException {
        List<Question> resultat = new ArrayList<>();
        try (Connection c = connexion();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, enonce, type FROM question")) {
            while (rs.next()) {
                Question q = new Question(rs.getInt("id"), rs.getString("enonce"), rs.getString("type"));
                lireReponses(c, q);
                resultat.add(q);
            }
        }
        return resultat;
    }

    /** Modifie une question existante. */
    public void modifier(Question q) throws SQLException {
        try (Connection c = connexion()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE question SET enonce = ?, type = ? WHERE id = ?")) {
                    ps.setString(1, q.getEnonce());
                    ps.setString(2, q.getType());
                    ps.setInt(3, q.getId());
                    ps.executeUpdate();
                }
                // Plus simple : on supprime les anciennes réponses et on réinsère les nouvelles
                try (PreparedStatement ps = c.prepareStatement(
                        "DELETE FROM reponse WHERE question_id = ?")) {
                    ps.setInt(1, q.getId());
                    ps.executeUpdate();
                }
                insererReponses(c, q);
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    /** Supprime la question qui a cet identifiant. */
    public void supprimer(int id) throws SQLException {
        try (Connection c = connexion();
             PreparedStatement ps = c.prepareStatement("DELETE FROM question WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();   // les réponses sont supprimées par ON DELETE CASCADE
        }
    }

    // ---- méthodes privées utilitaires ----

    private void insererReponses(Connection c, Question q) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO reponse(question_id, contenu, valide) VALUES (?, ?, ?)")) {
            for (Reponse r : q.getReponses()) {
                ps.setInt(1, q.getId());
                ps.setString(2, r.getContenu());
                ps.setInt(3, r.isValide() ? 1 : 0);   // SQLite n'a pas de vrai booléen
                ps.executeUpdate();
            }
        }
    }

    private void lireReponses(Connection c, Question q) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "SELECT contenu, valide FROM reponse WHERE question_id = ?")) {
            ps.setInt(1, q.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    q.ajouterReponse(new Reponse(rs.getString("contenu"), rs.getInt("valide") == 1));
                }
            }
        }
    }
}