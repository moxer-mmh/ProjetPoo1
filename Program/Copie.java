package Program;

public class Copie extends Ressource {

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

