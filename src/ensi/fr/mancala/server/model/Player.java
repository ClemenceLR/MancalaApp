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
    private static int  nbPlayers = 1;
    /**
     * Player constructor
     */
    public Player(){
        this.name = "toto"+nbPlayers;
        this.id = nbPlayers;
        this.granary = 0;
        nbPlayers ++;
    }

    /**
     * Player constructor
     * @param n : string
     */
    public Player(String n){
        this.name = n;
        this.id = nbPlayers;
        this.granary = 0;
        nbPlayers ++;
    }

    /**
     * Player constructor
     * @param n : string
     * @param g : id of the player
     */
    public Player(String n, int g){
        this.name = n;
        this.granary = g;
        this.id = nbPlayers;
        nbPlayers ++;
    }

    public Player(Player p){
        this.name = p.name;
        this.granary = p.granary;
        this.id = p.id;
    }

    /**
     * Get the player name
     * @return player name
     */
    public String getName() {
        return this.name;
    }


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


    /**
     * Add given seeds to granary
     * @param totalSeeds : seeds to add
     */
    public void addGranary(int totalSeeds) {
        this.granary += totalSeeds;
    }
}
