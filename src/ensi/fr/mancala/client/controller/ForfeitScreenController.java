package ensi.fr.mancala.client.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ForfeitScreenController {

    MainController mainController;
    @FXML private Pane forfeitScreen;

    public void setMainController(MainController mainController){ this.mainController = mainController; }

    public void close(){
        this.mainController.getMainPane().getChildren().remove(forfeitScreen);
    }




}
