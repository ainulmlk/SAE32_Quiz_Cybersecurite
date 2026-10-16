package quizz;

import java.io.IOException;

/**
 * Échange de quizz au format JSON avec les autres sites.
 * Un site d'adresse X.Y.Z.T publie son JSON sur http://X.Y.Z.T/export
 */
public class ImportJSON {

    /** Transforme un quizz en texte JSON. */
    public String exporter(Quizz quizz) {
        // TODO
        return null;
    }

    /** Récupère le JSON d'un site (http://adresse/export) et le transforme en quizz. */
    public Quizz importer(String adresse) throws IOException, InterruptedException {
        // TODO
        return null;
    }
}
