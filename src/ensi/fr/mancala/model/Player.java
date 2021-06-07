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
    public boolean getAcceptancy(){
        int move;
        boolean cont;
        String m;
        Scanner sc = new Scanner(System.in);

        do{
            System.out.println("What do you want to do ? 1 (continue) / 2 (null) ");
            m = sc.nextLine();
            try {
                move = Integer.parseInt(m);
            }
            catch(NumberFormatException e){
                move = -1;
                System.out.println("Invalid input please press 1 to continue or 2 to stop the game");
            }

            if(move !=1 && move != 2){
                System.out.println("Invalid number please press 1 to continue or 2 to stop the game");
                cont = false;
            }
            else {
                System.out.println(move);
                cont = true;
            }
        } while(!cont);
        System.out.println(move);
        if(move == 1){
            return false;
        }else {
            return true;
        }
    }

}
