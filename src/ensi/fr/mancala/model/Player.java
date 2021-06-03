package ensi.fr.mancala.model;

import java.io.IOException;
import java.util.Scanner;

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


    public int getCellClicked(){
        int move;
        boolean cont;
        String m;
        Scanner sc = new Scanner(System.in);

        do{
            System.out.println("Pick a cell ");
            m = sc.nextLine();
            try {
                move = Integer.parseInt(m);
            }
            catch(NumberFormatException e){
                move = -1;
                System.out.println("Invalid input please choose a number between 0 and 11");
            }

            if(move <0 || move > 11){
                System.out.println("Invalid number " + move + " please choose between 0 and 11");
                cont = false;
            }
            else {
                System.out.println(move);
                cont = true;
            }
        } while(!cont);
        return move;
    }

    public void eat_seed(Cell eatenCell){
        this.granary += eatenCell.getNbSeeds();
        eatenCell.setNbSeeds(0);
    }
    //TODO TANT QUE C'EST POSSIBLE ON MANGE et si invalide à la fin on rollback


}
