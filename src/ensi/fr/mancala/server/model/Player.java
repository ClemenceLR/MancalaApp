package ensi.fr.mancala.server.model;


/**
 * Managing the player
 * @author : Guillaume Hasseneyer
 * @author : Clémence Le Roux
 */
public class Player {
    private String name;
    private int granary;
    private int id;
    public static int nbJoueurs = 1;

    /**
     * Player constructor
     */
    public Player(){
        this.name = "toto"+nbJoueurs;
        this.id = nbJoueurs;
        this.granary = 0;
        nbJoueurs +=1;
    }

    /**
     * Player constructor
     * @param n : string
     */
    public Player(String n){
        this.name = n;
        this.id = nbJoueurs;
        this.granary = 0;
        nbJoueurs +=1;
    }

    /**
     * Player constructor
     * @param n : string
     * @param g : id of the player
     */
    public Player(String n, int g){
        this.name = n;
        this.granary = g;
        this.id = nbJoueurs;
        nbJoueurs +=1;
    }

    /**
     * Get the player name
     * @return player name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the player name
     */
    public void setName(String name){
        this.name = name;
    }

    public int getGranary() {
        return granary;
    }

    public void setGranary(int granary) {
        this.granary = granary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getNbJoueurs() {
        return nbJoueurs;
    }



    /**
     * Add given seeds to granary
     * @param totalSeeds : seeds to add
     */
    public void addGranary(int totalSeeds) {
        this.granary += totalSeeds;
    }
}
