package Program;

import java.time.LocalDate;
import java.util.*;

public class Agent extends Bibliotheque {

    private String agent;
    private final double Maxvipdays = 30;
    private final double Maxnormaldays = 14;
    private final double penalvip = 0.5;
    private final double penalnormal = 1;
    private final double livrevipmax = 5;
    private final double livrenormalmax = 2;

    public Agent(String nom, String adresse, String agent) {
        super(nom, adresse);
        this.agent = agent;
    }

    protected String getNom() {
        return this.agent;
    }

    protected void setNom(String nom) {
        this.agent = nom;
    }

    public Livre ajouterLivre(String titre, String auteur, TypeLivre typeLivre) {
        Livre livre = new Livre(titre, auteur, typeLivre);
        livre.copyLivre = null;
        Ressource.livres.add(livre);
        System.out.println(livre.getTitre() + " " + livre.getAuteur() + " " + livre.getTypeLivre());
        return livre;
    }

    public void supprimerLivre(Livre livre) {
        System.out.println(livre.getTitre() + " " + "est supprimer");
        Ressource.livres.remove(livre);
        livre = null;
    }

    public void ajouterCopie(Livre livre, int quantite) {
        Copie copie = new Copie(quantite, livre);
        copie.setId(copie.generateResourceId(livre.getTypeLivre().toString()));
        livre.copyLivre = copie;
        System.out.println(copie.getId() + " " + copie.getQuantite() + " " + copie.getLivre().getTitre());
    }

    public void supprimerCopie(Livre livre) {
        livre.copyLivre.setQuantite(livre.copyLivre.getQuantite() - 1);
    }

    public User ajouterUser(String nom, Planabonnement planabonnement, double amende, double money) {
        User user = new User(nom, planabonnement, amende, money);
        user.setLivres(new ArrayList<Livre>());
        Bibliotheque.users.add(user);
        System.out.println(user.getNom() + " " + user.getPlanabonnement() + " " + user.getAmende());
        return user;
    }

    public void supprimerUser(User user) {
        System.out.println(user.getNom() + " " + "est supprimer");
        Bibliotheque.users.remove(user);
        user = null;
    }

    public void modifierUser(User user, String nom, Planabonnement planabonnement, List<Livre> livres, double amende) {
        user.setNom(nom);
        user.setPlanabonnement(planabonnement);
        user.setLivres(livres);
        user.setAmende(amende);
    }

    public void modifierLivre(Livre livre, String titre, String auteur, TypeLivre typeLivre) {
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setTypeLivre(typeLivre);
    }

    public void modifierCopie(Copie copie, int quantite, Livre livre) {
        copie.setQuantite(quantite);
        copie.setLivre(livre);
    }

    public void emprunterLivre(User user, Livre livre) {
        if (user.getPlanabonnement() == Planabonnement.VIP) {
            if (user.getLivres().size() < this.livrevipmax) {
                if (user.getAmende() > 0) {
                    System.out.println("Vous avez une amende de " + user.getAmende() + "€");
                } else {
                    if (livre.verifierDisponibilite(livre)) {
                        user.emprunterLivre(livre);
                        LocalDate specifiedDate = LocalDate.of(2024, 1, 1);
                        livre.setDateEmprunt(java.sql.Date.valueOf(specifiedDate));
                        System.out.println(user.getNom() + " " + "a emprunté le livre " + livre.getTitre());
                    } else {
                        System.out.println("Le livre n'est pas disponible");
                    }
                }
            } else {
                System.out.println("Vous avez atteint le nombre maximal de livres");
            }
        } else {
            if (user.getLivres().size() < this.livrenormalmax) {
                if (user.getAmende() > 0) {
                    System.out.println("Vous avez une amende de " + user.getAmende() + "€");
                } else {
                    if (livre.verifierDisponibilite(livre)) {
                        user.emprunterLivre(livre);
                        LocalDate specifiedDate = LocalDate.of(2024, 1, 1);
                        livre.setDateEmprunt(java.sql.Date.valueOf(specifiedDate));
                        System.out.println(user.getNom() + " " + "a emprunté le livre " + livre.getTitre());
                    } else {
                        System.out.println("Le livre n'est pas disponible");
                    }
                }
            } else {
                System.out.println("Vous avez atteint le nombre maximal de livres");
            }
        }
    }

    public double calculerAmende(User user, Livre livre) {
        double amende = 0;
        if (user.getPlanabonnement() == Planabonnement.VIP) {
            if (livre.getDateRetour().getTime() - livre.getDateEmprunt().getTime() > this.Maxvipdays) {
                amende = (livre.getDateRetour().getTime() - livre.getDateEmprunt().getTime() - this.Maxvipdays)
                        * this.penalvip;
            }
        } else {
            if (livre.getDateRetour().getTime() - livre.getDateEmprunt().getTime() > this.Maxnormaldays) {
                amende = (livre.getDateRetour().getTime() - livre.getDateEmprunt().getTime() - this.Maxnormaldays)
                        * this.penalnormal;
            }
        }
        return amende;
    }

    public void rendreLivre(User user, Livre livre, Date dateRetour) {

        livre.setDateRetour(dateRetour);

        if (user.getPlanabonnement() == Planabonnement.VIP) {
            if (livre.getDateRetour().getTime() - livre.getDateEmprunt().getTime() > this.Maxvipdays) {
                user.ajouterAmende(this.calculerAmende(user, livre));
                System.out.println("Vous avez une amende de " + user.getAmende());
                user.payerAmende(user.getAmende());
                System.out.println("Vous avez payé votre amende");
            }
        } else {
            if (livre.getDateRetour().getTime() - livre.getDateEmprunt().getTime() > this.Maxnormaldays) {
                user.ajouterAmende(this.calculerAmende(user, livre));
                System.out.println("Vous avez une amende de " + user.getAmende());
                user.payerAmende(user.getAmende());
                System.out.println("Vous avez payé votre amende");
            }
        }

        user.rendreLivre(livre);
        System.out.println(user.getNom() + " " + "a rendu le livre " + livre.getTitre());

    }

    public void afficherLivres() {
        for (Livre livre : Ressource.livres) {
            System.out.println(livre.getTitre() + " " + livre.getAuteur() + " " + livre.getTypeLivre() + " "
                    + livre.copyLivre.getId() + " " + livre.copyLivre.getQuantite());
        }
    }

    public void afficherUsers() {
        for (User user : Bibliotheque.users) {
            System.out.println(
                    user.getNom() + " " + user.getPlanabonnement() + " " + user.getAmende() + " " + user.getMoney());
            System.out.println(user.getLivres().size());
            user.afficherLivres();
        }
    }

}

