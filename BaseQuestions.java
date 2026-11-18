package quizz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Accès à la base de données MariaDB (création, lecture, modification,
 * suppression des questions) via JDBC.
 * Tables : question(id, enonce, type) et reponse(id, question_id, contenu, valide).
 */
public class BaseQuestions {

    private String url;        // ex. "jdbc:mariadb://adresse-vm:3306/quizz"
    private String utilisateur;
    private String motDePasse;

    public BaseQuestions(String url, String utilisateur, String motDePasse) {
        this.url = url;
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;
    }

    private Connection connexion() throws SQLException {
        return DriverManager.getConnection(url, utilisateur, motDePasse);
    }

    /** Enregistre une nouvelle question avec ses réponses et retourne son identifiant. */
    public int creer(Question q) throws SQLException {
        try (Connection c = connexion()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO question (enonce, type) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, q.getEnonce());
                    ps.setString(2, q.getType());
                    ps.executeUpdate();
                    try (ResultSet cles = ps.getGeneratedKeys()) {
                        cles.next();
                        id = cles.getInt(1);
                    }
                }
                insererReponses(c, id, q.getReponses());
                c.commit();
                return id;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    /** Retourne toutes les questions de la base, avec leurs réponses. */
    public List<Question> lireToutes() throws SQLException {
        List<Question> resultat = new ArrayList<>();
        try (Connection c = connexion();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, enonce, type FROM question ORDER BY id")) {
            while (rs.next()) {
                Question q = new Question(rs.getInt("id"), rs.getString("enonce"), rs.getString("type"));
                resultat.add(q);
            }
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT contenu, valide FROM reponse WHERE question_id = ? ORDER BY id")) {
                for (Question q : resultat) {
                    ps.setInt(1, q.getId());
                    try (ResultSet r = ps.executeQuery()) {
                        while (r.next()) {
                            q.ajouterReponse(new Reponse(r.getString("contenu"), r.getBoolean("valide")));
                        }
                    }
                }
            }
        }
        return resultat;
    }

    /** Modifie une question existante (énoncé, type et réponses). */
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
                try (PreparedStatement ps = c.prepareStatement(
                        "DELETE FROM reponse WHERE question_id = ?")) {
                    ps.setInt(1, q.getId());
                    ps.executeUpdate();
                }
                insererReponses(c, q.getId(), q.getReponses());
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    /** Supprime la question qui a cet identifiant (ses réponses sont supprimées par ON DELETE CASCADE). */
    public void supprimer(int id) throws SQLException {
        try (Connection c = connexion();
             PreparedStatement ps = c.prepareStatement("DELETE FROM question WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private void insererReponses(Connection c, int questionId, List<Reponse> reponses) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO reponse (question_id, contenu, valide) VALUES (?, ?, ?)")) {
            for (Reponse r : reponses) {
                ps.setInt(1, questionId);
                ps.setString(2, r.getContenu());
                ps.setBoolean(3, r.isValide());
                ps.executeUpdate();
            }
        }
    }
}
