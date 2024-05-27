package Program;

import java.util.*;

public class Ressource extends Bibliotheque {

    static List<Livre> livres = new ArrayList<Livre>();

    public Ressource() {
        super(null, null);
    }

    public void ajouterLivre(Livre livre) {
        Ressource.livres.add(livre);
    }

    public void supprimerLivre(Livre livre) {
        Ressource.livres.remove(livre);
    }

}

