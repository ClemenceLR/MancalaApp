package ensi.fr.mancala.server.model;

import java.util.Scanner;

/**
 * Managing the player
 * @author : Guillaume Hasseneyer
 * @author : Clémence Le Roux
 */
public class Player {
    public String name;
    public int granary;
    public int id;
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
}
