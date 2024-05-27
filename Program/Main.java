package Program;

import java.time.LocalDate;


public class Main {

    public static void main(String[] args) {

        

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
