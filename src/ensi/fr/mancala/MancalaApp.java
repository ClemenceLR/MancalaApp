package ensi.fr.mancala;

import ensi.fr.mancala.model.Game;
import ensi.fr.mancala.model.ManageFile;

public class MancalaApp {
    public static void main(String[] args) {
        /*int[] t = new int[12];
        int k = 0;
        for(int j : t){
            j += k;
            k++;
        }
        for(int i=0;i<t.length; i++){
            System.out.println(t[i]);
        }*/
        Game g = ManageFile.loadGame("save");
        System.out.println(g);
    }

}
