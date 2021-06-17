package ensi.fr.mancala.client.controller;

import ensi.fr.mancala.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.InetAddress;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Client config screen controller
 *  @author : Guillaume Hasseneyer
 *  @author : Clémence Le Roux
 *
 */
public class ClientConfigScreenController {

    @FXML
    private Pane configScreenPane;

    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private TextField pseudo;


    MainController mainController;

    /**
     * Access the main controller by stocking it
     * @param mainController : main controller
     */
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * Connect to server
     */
    @FXML
    private void connectToServer(){
        Client client;
        Timer timer;

        InetAddress ipValue;
        int portValue;
        String pseudoValue;

        try {

            ipValue = this.ip.getText().toLowerCase(Locale.ROOT) == "localhost"
                    ?InetAddress.getLocalHost()
                    :InetAddress.getByName(this.ip.getText());

            portValue = Integer.parseInt(this.port.getText());
            pseudoValue = this.pseudo.getText();

            client = new Client(ipValue, portValue, pseudoValue);
            client.connect();
            client.setMainController(this.mainController);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.playGame();
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
