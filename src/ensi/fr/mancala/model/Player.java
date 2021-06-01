package ensi.fr.mancala.model;

public class Player {
    public String name;
    public int granary;
    public int id;
    public static int nbJoueurs = 1;

    public Player(){
        this.name = "toto"+nbJoueurs;
        this.id = nbJoueurs;
        this.granary = 0;
        nbJoueurs +=1;
    }

    public Player(String n){
        this.name = n;
        this.id = nbJoueurs;
        this.granary = 0;
        nbJoueurs +=1;
    }

    public Player(String n, int g){
        this.name = n;
        this.granary = g;
        this.id = nbJoueurs;
        nbJoueurs +=1;
    }

    public void setGranary(int granary) {
        this.granary = granary;
    }
}
