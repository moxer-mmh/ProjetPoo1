package Program;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

enum TypeLivre {
    ROMAN,
    BD,
    MANGA,
    MANUEL,
    REVUE
}

enum Planabonnement {
    NORMAL,
    VIP
}

class Bibliotheque {

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

class Agent extends Bibliotheque {

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

class Ressource extends Bibliotheque {

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

class Livre extends Ressource {

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

class Copie extends Ressource {

    private String id;
    private int quantite;
    private Livre Livre;

    public Copie(int quantite, Livre Livre) {
        this.quantite = quantite;
        this.Livre = Livre;
    }

    protected String getId() {
        return this.id;
    }

    protected int getQuantite() {
        return this.quantite;
    }

    protected Livre getLivre() {
        return this.Livre;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    protected void setLivre(Livre Livre) {
        this.Livre = Livre;
    }

    protected String generateResourceId(String category) {
        String resourceId = "B" + generateCategoryNumber(category) + "C" + generateRandomNumber();
        return resourceId;
    }

    protected String generateCategoryNumber(String category) {
        String categoryNumber = null;
        switch (category) {
            case "ROMAN":
                categoryNumber = "1";
                break;
            case "BD":
                categoryNumber = "2";
                break;
            case "MANGA":
                categoryNumber = "3";
                break;
            case "MANUEL":
                categoryNumber = "4";
                break;
            case "REVUE":
                categoryNumber = "5";
                break;
        }
        return categoryNumber;
    }

    private String generateRandomNumber() {
        int randomNumber = (int) (Math.random() * (9999 - 1000 + 1) + 1000);
        return String.valueOf(randomNumber);
    }

}

class User {

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

public class Main {

    public static void main(String[] args) {

        // 222231502109 Hocine Med Abdel Moncef
        // sorry teacher if i bother you a lot but know the main function worked for me
        // i tried to find the solution and added some upgrades to my code

        Agent agent = new Agent("Bibliotheque", "Paris", "Agent");

        Livre livre = agent.ajouterLivre("Livre", "Auteur", TypeLivre.ROMAN);
        Livre livre2 = agent.ajouterLivre("Livre2", "Auteur2", TypeLivre.BD);
        Livre livre3 = agent.ajouterLivre("Livre3", "Auteur3", TypeLivre.MANGA);
        Livre livre4 = agent.ajouterLivre("Livre4", "Auteur4", TypeLivre.MANUEL);

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.ajouterCopie(livre, 1);
        agent.ajouterCopie(livre2, 2);
        agent.ajouterCopie(livre3, 4);
        agent.ajouterCopie(livre4, 2);

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        User user = agent.ajouterUser("User", Planabonnement.VIP, 0, 25);
        User user2 = agent.ajouterUser("User2", Planabonnement.NORMAL, 0, 35);
        User user3 = agent.ajouterUser("User3", Planabonnement.VIP, 0, 15);

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.emprunterLivre(user, livre);
        agent.emprunterLivre(user, livre2);
        agent.emprunterLivre(user, livre3);
        agent.emprunterLivre(user, livre4);

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.emprunterLivre(user2, livre);
        agent.emprunterLivre(user2, livre2);
        agent.emprunterLivre(user2, livre3);
        agent.emprunterLivre(user2, livre4);

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.emprunterLivre(user3, livre);
        agent.emprunterLivre(user3, livre2);
        agent.emprunterLivre(user3, livre3);
        agent.emprunterLivre(user3, livre4);

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.afficherLivres();

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.afficherUsers();

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        LocalDate specifiedDate = LocalDate.of(2024, 1, 31);
        agent.rendreLivre(user, livre, java.sql.Date.valueOf(specifiedDate));

        LocalDate specifiedDate2 = LocalDate.of(2024, 2, 1);
        agent.rendreLivre(user, livre2, java.sql.Date.valueOf(specifiedDate2));

        System.out.println(
                "----------------------------------------------------------------------------------------------");
        LocalDate specifiedDate3 = LocalDate.of(2024, 1, 15);
        agent.rendreLivre(user2, livre2, java.sql.Date.valueOf(specifiedDate3));

        System.out.println(
                "----------------------------------------------------------------------------------------------");
        LocalDate specifiedDate4 = LocalDate.of(2024, 1, 11);
        agent.rendreLivre(user3, livre3, java.sql.Date.valueOf(specifiedDate4));
        LocalDate specifiedDate5 = LocalDate.of(2024, 1, 16);
        agent.rendreLivre(user3, livre4, java.sql.Date.valueOf(specifiedDate5));

        // i dont know why amende value is not normal

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.afficherLivres();

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.afficherUsers();

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        System.out.println(user.rechercherLivrebyTitre("Livre"));
        System.out.println(user.rechercherLivrebyAuteur("Auteur"));
        System.out.println(user.rechercherCopiebyId("B1C1111"));
        System.out.println(user.rechercheLivrebyType(TypeLivre.ROMAN));

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        System.out.println(user2.rechercherLivrebyTitre("Livre2"));
        System.out.println(user2.rechercherLivrebyAuteur("Auteur2"));
        System.out.println(user2.rechercherCopiebyId("B1C2222"));
        System.out.println(user2.rechercheLivrebyType(TypeLivre.BD));

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        System.out.println(user3.rechercherLivrebyTitre("Livre3"));
        System.out.println(user3.rechercherLivrebyAuteur("Auteur3"));
        System.out.println(user3.rechercherCopiebyId("B1C3333"));
        System.out.println(user3.rechercheLivrebyType(TypeLivre.MANGA));

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.supprimerLivre(livre);
        agent.supprimerLivre(livre2);
        agent.supprimerLivre(livre4);

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.supprimerUser(user2);
        agent.supprimerUser(user3);

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.afficherLivres();

        System.out.println(
                "----------------------------------------------------------------------------------------------");

        agent.afficherUsers();

        System.out.println(
                "----------------------------------------------------------------------------------------------");

    }
}
