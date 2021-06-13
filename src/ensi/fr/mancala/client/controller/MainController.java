package ensi.fr.mancala.client.controller;

import ensi.fr.mancala.server.model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML private Pane mainPane;

    Game currentGame;
    private MenuController menuController;
    private GranaryController granaryController;
    private BoardController boardController;

    public void initialize() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Menu.fxml"));
        Node menuNode = fxmlLoader.load();
        this.menuController = fxmlLoader.getController();
        this.menuController.setMainController(this);
        mainPane.getChildren().add(menuNode);


        fxmlLoader = new FXMLLoader(getClass().getResource("../view/Granary.fxml"));
        Node granaryNode = fxmlLoader.load();
        this.granaryController = fxmlLoader.getController();
        this.granaryController.setMainController(this);
        mainPane.getChildren().add(granaryNode);

        fxmlLoader = new FXMLLoader(getClass().getResource("../view/Board.fxml"));
        Node boardNode = fxmlLoader.load();
        this.boardController = fxmlLoader.getController();
        this.boardController.setMainController(this);
        mainPane.getChildren().add(boardNode);

        this.currentGame = new Game();

        StackPane one = this.boardController.getCellByNumber(1);
        this.boardController.updateCell(one,true,12);


    }


    //TODO lance la maj des cellules en fonction de si elles ont des graines ou pas
    public void updateGame(){
        //update board / player / player score / setCellAvailable
    }

    //TODO Récup filename (voir comment on fait en jfx)
    public void saveGame(String fileName){

    }

    //TODO
    public void loadGame(String fileName){

    }
}
