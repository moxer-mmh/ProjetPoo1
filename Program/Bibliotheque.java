package Program;

import java.util.*;

public class Bibliotheque {

    private String nom;
    private String adresse;
    protected static List<User> users = new ArrayList<User>();

    public Bibliotheque(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

    protected String getNom() {
        return this.nom;
    }

    protected String getAdresse() {
        return this.adresse;
    }

    protected void setNom(String nom) {
        this.nom = nom;
    }

    protected void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void ajouterUser(User user) {
        Bibliotheque.users.add(user);
    }

    public void supprimerUser(User user) {
        Bibliotheque.users.remove(user);
    }

}

