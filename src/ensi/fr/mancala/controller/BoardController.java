package ensi.fr.mancala.controller;

import javafx.scene.input.MouseEvent;

public class BoardController {

    MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void play(MouseEvent mouseEvent) {
        System.out.println("TEST");
    }
}
