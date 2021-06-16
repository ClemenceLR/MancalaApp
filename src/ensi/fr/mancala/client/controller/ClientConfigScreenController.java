package ensi.fr.mancala.client.controller;

import ensi.fr.mancala.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.InetAddress;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ClientConfigScreenController {

    @FXML
    private Pane configScreenPane;

    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private TextField pseudo;


    MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void connectToServer(){
        Client client;
        Timer timer;

        InetAddress ip;
        int port;
        String pseudo;


        try {

            ip = this.ip.getText().toLowerCase(Locale.ROOT) == "localhost"
                    ?InetAddress.getLocalHost()
                    :InetAddress.getByName(this.ip.getText());

            port = Integer.parseInt(this.port.getText());
            pseudo = this.pseudo.getText();

            client = new Client(ip, port, pseudo);
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

            if(client.getMe() != null){
                this.mainController.getMainPane().getChildren().remove(configScreenPane);
            }

        }
        catch(Exception e){
            System.err.println("Client creation failed");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Error in connection, please check your configurations inputs and retry");
            a.setTitle("Configuration error");
            a.showAndWait();
        }

    }
}
