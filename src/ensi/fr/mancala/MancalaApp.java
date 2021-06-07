package ensi.fr.mancala;

import ensi.fr.mancala.model.Check;
import ensi.fr.mancala.model.Game;
import ensi.fr.mancala.model.ManageFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MancalaApp extends Application {

    public static void main(String[] args) {
        launch(args);
        //TODO récupérer et créer les joueurs en fonction des clients enregistrés
        //Appeler Game g =  Game(p1,p2);
        //Game g = new Game();
        //g.activePlayer.getCellClicked();
        //g.activePlayer.getCellClicked();



        //g.playGame();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Game.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Mancala");
        primaryStage.show();
    }

}
