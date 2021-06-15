package ensi.fr.mancala.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MancalaApp extends Application {

    public static void main(String[] args) {
        launch(args);
        //TODO récupérer et créer les joueurs en fonction des clients enregistrés

    }

    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Game.fxml"));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Mancala");
        primaryStage.show();



    }

}

