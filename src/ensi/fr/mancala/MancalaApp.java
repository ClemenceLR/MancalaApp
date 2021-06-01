package ensi.fr.mancala;

import ensi.fr.mancala.model.Game;
import ensi.fr.mancala.model.ManageFile;

public class MancalaApp {
    public static void main(String[] args) {

        // Game g = ManageFile.loadGame("save");

        Game g = new Game();
        g.printBoard();

    }

}
