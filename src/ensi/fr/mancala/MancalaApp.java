package ensi.fr.mancala;

import ensi.fr.mancala.model.Check;
import ensi.fr.mancala.model.Game;
import ensi.fr.mancala.model.ManageFile;

public class MancalaApp {
    public static void main(String[] args) {

        Game g = new Game();

        // Game g = new Game();
        // g.printBoard();
        // ManageFile.saveGame("partie",g);
        // Game g2 =ManageFile.loadGame("partie");
        // System.out.println(g2);
        // g2.play(0);
        //g2.board.printBoard();

       g.playGame();
    }

}
