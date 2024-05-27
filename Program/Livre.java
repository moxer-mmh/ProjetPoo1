package Program;

import java.util.*;

public class Livre extends Ressource {

    private String titre;
    private String auteur;
    private TypeLivre typeLivre;
    public Copie copyLivre;

    private Date dateEmprunt;
    private Date dateRetour;

    public Livre(String titre, String auteur, TypeLivre typeLivre) {
        this.titre = titre;
        this.auteur = auteur;
        this.typeLivre = typeLivre;
    }

    protected String getTitre() {
        return this.titre;
    }

    protected String getAuteur() {
        return this.auteur;
    }

    protected TypeLivre getTypeLivre() {
        return this.typeLivre;
    }

    protected void setTitre(String titre) {
        this.titre = titre;
    }

    protected void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    protected void setTypeLivre(TypeLivre typeLivre) {
        this.typeLivre = typeLivre;
    }

    public boolean verifierDisponibilite(Livre livre) {
        if (livre.copyLivre.getQuantite() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public Date getDateEmprunt() {
        return this.dateEmprunt;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Date getDateRetour() {
        return this.dateRetour;
    }

}

