package ensi.fr.mancala.client;

import ensi.fr.mancala.client.controller.MainController;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private MainController mainController;
    private InetAddress addr;
    private int port;
    private Socket me;
    private String pseudo;
    public String opponent;
    private Scanner in;
    private PrintStream out;
    private Logger logger;
    private boolean myTurn;
    private Timer timer;
    private File file;

    public Client(InetAddress addr, int port, String pseudo){
        this.addr = addr;
        this.port = port;
        this.pseudo = pseudo;
    }


    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void setTimer(Timer timer){
        this.timer = timer;
    }

    public void connect(){
        try {
            this.me = new Socket(this.addr, this.port);
            out = new PrintStream(me.getOutputStream());
            in = new Scanner(me.getInputStream());
        }catch(IOException e){
            logger.log(Level.WARNING,"Client failed to connect");
            e.printStackTrace();
        }
        out.println(this.pseudo);
        //this.opponent = in.nextLine();
        //updateGame();
        out.println("1");
        return;
    }

    public void disconnect() {
        this.timer.cancel();
        try {
            this.me.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void play() {
            String code = receive();

            switch (code){
                case "B" :
                    this.updateGame();
                    break;
                case "C":
                    this.myTurn = !this.myTurn;
                    break;
                case "D":
                    disconnect();
                    break;

                case "ff":
                    this.mainController.askForfeit();
                    break;

                case "G":
                    try {
                        PrintStream ps = new PrintStream(this.file);
                        ps.println(receive());
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert aa = new Alert(Alert.AlertType.INFORMATION);
                            aa.setContentText("Save Completed !");
                            aa.setTitle("File saved under the given file");
                            aa.showAndWait();
                        }
                    });
                    break;

                case "N":
                    String name0 = receive();
                    String name1 = receive();
                    this.mainController.getGranaryController().updateNames(name0, name1);
                    break;

                case "R":
                    String r = receive();
                    String result = (r.equals("=") ? "Egalité" : (r.equals("+") ? "Gagné !" : "Perdu..."));
                    //TODO débugger pour ne pas avoir erreur de thread
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setContentText(result);
                    a.setTitle("Jeu Terminé !");
                    a.showAndWait();
                    break;

                case "?":
                    myTurn = true;
                    break;

            }

    }

    public void updateGame(){
        String boardSeeds = receive();
        String boardAvailable = receive();
        String granaryPlayer0 = receive();
        String granaryPlayer1 = receive();

        this.mainController.updateGame(boardSeeds,boardAvailable,granaryPlayer0,granaryPlayer1);
    }

    public void send(String toSend, Boolean isPriority){

        if(myTurn || isPriority){
            System.out.println("On envoie: " + toSend);
            this.getOutput().println(toSend);
        }
        else{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Action reserved to actual player !");
            a.setTitle("Action forbidden");
            a.showAndWait();
        }
    }

    public String receive(){
        return this.getInput().nextLine();
    }

    private Scanner getInput() {
        return this.in;
    }

    public PrintStream getOutput(){
        return this.out;
    }

    public static void main(String[] args){
        /* try {
            Client c = new Client(InetAddress.getLocalHost(), 8080, "B");
            c.connect();
        }
        catch(UnknownHostException e){
            System.err.println("Client creation failed");
        }*/

        MancalaApp.main(args);


    }

    public void setFile(File file) {
        this.file = file;
    }
}
