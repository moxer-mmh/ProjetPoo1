package Program;

import java.util.*;

public class User {

    private String nom;
    private Planabonnement planabonnement;
    private List<Livre> livres = new ArrayList<Livre>();
    private double amende;
    private double money;

    public User(String nom, Planabonnement planabonnement, double amende, double money) {
        this.nom = nom;
        this.planabonnement = planabonnement;
        this.amende = amende;
        this.money = money;
    }

    protected String getNom() {
        return this.nom;
    }

    protected Planabonnement getPlanabonnement() {
        return this.planabonnement;
    }

    protected List<Livre> getLivres() {
        return this.livres;
    }

    protected double getAmende() {
        return this.amende;
    }

    protected double getMoney() {
        return this.money;
    }

    protected void setNom(String nom) {
        this.nom = nom;
    }

    protected void setPlanabonnement(Planabonnement planabonnement) {
        this.planabonnement = planabonnement;
    }

    protected void setLivres(List<Livre> livres) {
        this.livres = livres;
    }

    protected void setAmende(double amende) {
        this.amende = amende;
    }

    protected void setMoney(double money) {
        this.money = money;
    }

    public void emprunterLivre(Livre livre) {
        this.livres.add(livre);
        livre.copyLivre.setQuantite(livre.copyLivre.getQuantite() - 1);
    }

    public void rendreLivre(Livre livre) {
        this.livres.remove(livre);
        livre.copyLivre.setQuantite(livre.copyLivre.getQuantite() + 1);
    }

    public void payerAmende(double amende) {
        this.money = this.money - amende;
        this.amende = 0;
    }

    public void ajouterAmende(double amende) {
        this.amende = this.amende + amende;
    }

    public void afficherLivres() {
        for (Livre livre : this.livres) {
            System.out.println(livre.getTitre());
        }
    }

    public String rechercherLivrebyTitre(String titre) {
        for (Livre livre : Ressource.livres) {
            if (livre.getTitre() == titre) {
                return livre.getTitre();
            }
        }
        return "Le livre n'existe pas";
    }

    public String rechercherLivrebyAuteur(String auteur) {
        for (Livre livre : Ressource.livres) {
            if (livre.getAuteur() == auteur) {
                return livre.getTitre();
            }
        }
        return "Le livre n'existe pas";
    }

    public String rechercherCopiebyId(String id) {
        for (Livre livre : Ressource.livres) {
            if (livre.copyLivre.getId() == id) {
                return livre.getTitre();
            }
        }
        return "Le livre n'existe pas";
    }

    public String rechercheLivrebyType(TypeLivre typeLivre) {
        for (Livre livre : Ressource.livres) {
            if (livre.getTypeLivre() == typeLivre) {
                return livre.getTitre();
            }
        }
        return "Le livre n'existe pas";
    }

}

