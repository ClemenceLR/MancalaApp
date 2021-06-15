package ensi.fr.mancala.client.controller;

import ensi.fr.mancala.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

public class ClientConfigScreenController {

    @FXML
    private Pane configScreenPane;

    MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void connectToServer(ActionEvent actionEvent){
        Client client;
        Timer timer;

        try {
            client = new Client(InetAddress.getLocalHost(), 8080, "PseudoCool");
            client.connect();
            client.setMainController(this.mainController);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.play();
                }
            }, 0, 10);
            client.setTimer(timer);
            this.mainController.setClient(client);
            this.mainController.getMainPane().getChildren().remove(configScreenPane);
        }
        catch(Exception e){
            System.err.println("Client creation failed");
        }

    }
}
