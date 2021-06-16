package ensi.fr.mancala.client.controller;

import ensi.fr.mancala.client.Client;
import ensi.fr.mancala.server.model.Board;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class MainController {
    @FXML private Pane mainPane;

    private MenuController menuController;
    private GranaryController granaryController;
    private BoardController boardController;
    private ClientConfigScreenController clientConfigScreenController;
    private ForfeitScreenController forfeitScreenController;

    private Client client;

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

        fxmlLoader = new FXMLLoader(getClass().getResource("../view/ClientConfigScreen.fxml"));
        Node configScreenNode = fxmlLoader.load();
        this.clientConfigScreenController = fxmlLoader.getController();
        this.clientConfigScreenController.setMainController(this);
        mainPane.getChildren().add(configScreenNode);


    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public Pane getMainPane() {
        return mainPane;
    }

    public GranaryController getGranaryController(){
        return this.granaryController;
    }
    
    public void updateGame(String boardSeeds, String boardAvailable, String granaryPlayer0, String granaryPlayer1) {
        int i;
        StackPane stackPaneToUpdate;
        int nbSeedsForUpdate;
        boolean availableForUpdate;
        String[] boardSeedsArray = boardSeeds.split("-");
        String[] boardAvailableArray = boardAvailable.split("-");

        for(i=0; i< Board.SIZE_BOARD; i++){
            stackPaneToUpdate = this.boardController.getCellByNumber(i);
            nbSeedsForUpdate = Integer.parseInt(boardSeedsArray[i]);
            availableForUpdate = boardAvailableArray[i].equals("true");

            this.boardController.updateCell(stackPaneToUpdate,availableForUpdate,nbSeedsForUpdate);
        }

        this.granaryController.setGranary(granaryPlayer0,0);
        this.granaryController.setGranary(granaryPlayer1,1);
    }

    public void askForfeit() {

        Platform.runLater(new Runnable () {

            @Override
            public void run() {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Forfeit ?");
                a.setContentText("Would you like to forfeit ?");

                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    MainController.this.getClient().send("f", true);
                } else {
                    MainController.this.getClient().send("n", true);
                }
            }
        });

    }
}
