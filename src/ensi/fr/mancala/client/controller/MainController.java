package ensi.fr.mancala.client.controller;

import ensi.fr.mancala.client.Client;
import ensi.fr.mancala.server.model.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class MainController {
    @FXML private Pane mainPane;

    private MenuController menuController;
    private GranaryController granaryController;
    private BoardController boardController;

    private Client client;
    private Timer timer;

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

        try {
            this.client = new Client(InetAddress.getLocalHost(), 8080, "B");
            this.client.connect();
            this.client.setMainController(this);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.play();
                }
            }, 0, 100);
        }
        catch(UnknownHostException e){
            System.err.println("Client creation failed");
        }

    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client){
        this.client = client;
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
