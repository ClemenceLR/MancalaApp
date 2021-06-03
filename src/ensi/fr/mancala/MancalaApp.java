package ensi.fr.mancala;

import ensi.fr.mancala.model.Check;
import ensi.fr.mancala.model.Game;
import ensi.fr.mancala.model.ManageFile;

public class MancalaApp {
    public static void main(String[] args) {
        //TODO récupérer et créer les joueurs en fonction des clients enregistrés
        //Appeler Game g =  Game(p1,p2);
        Game g = new Game();
        //g.activePlayer.getCellClicked();
        //g.activePlayer.getCellClicked();



        g.playGame();
    }

}
