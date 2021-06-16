package ensi.fr.mancala.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class in charge to launch the app controllers
 * @author : Guillaume Hasseneyer
 *  @author : Clémence Le Roux
 *
 */
public class MancalaApp extends Application {

    public static void main(String[] args) {
        launch(args);
        //TODO récupérer et créer les joueurs en fonction des clients enregistrés

    }

    /**
     * Initialize the stage
     * @param primaryStage : stage
     * @throws Exception : if fails
     */
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Game.fxml"));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Mancala");
        primaryStage.show();



    }

}

