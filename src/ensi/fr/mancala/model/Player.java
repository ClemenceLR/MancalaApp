package ensi.fr.mancala.model;

public class Player {
    public String name;
    public int granary;
    public static int id = 0;

    public Player(){
        this.name = "toto"+id;
        this.granary = 0;
        id +=1;
    }

    public Player(String n){
        this.name = n;
        this.granary = 0;
        id += 1;
    }

    public Player(String n, int g){
        this.name = n;
        this.granary = g;
        id += 1;
    }

    public void setGranary(int granary) {
        this.granary = granary;
    }
}
