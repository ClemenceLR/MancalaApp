package ensi.fr.mancala.client.controller;

import javafx.event.ActionEvent;

public class MenuController {

    MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void play() {
        this.mainController.getClient().play();
    }
}
