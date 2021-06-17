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

/**
 * Class client
 * @author : Guillaume Hasseneyer
 * @author : Clémence Le Roux
 *
 */
public class Client {
    private MainController mainController;
    private final InetAddress addr;
    private final int port;
    private Socket me;
    private final String pseudo;
    private Scanner in;
    private PrintStream out;
    private Logger logger;
    private boolean myTurn;
    private Timer timer;
    private File file;
    private boolean leave = false;
    private int alreadySend =1;
    /**
     * Client constructor
     * @param addr : addr
     * @param port : port
     * @param pseudo : pseudo
     */
    public Client(InetAddress addr, int port, String pseudo){
        this.addr = addr;
        this.port = port;
        this.pseudo = pseudo;
    }

    /**
     * Get the socket
     * @return socket
     */
    public Socket getMe(){
        return this.me;
    }

    /**
     * Set Main Controller
     * @param mainController main controller
     */
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * Set My Turn
     * @param myTurn : boolean that designs if it's my turn to play or no
     */
    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * Set timer
     * @param timer start the client timer
     */
    public void setTimer(Timer timer){
        this.timer = timer;
    }

    /**
     * Connect with the server
     */
    public void connect(){
        try {
            this.me = new Socket(this.addr, this.port);
            out = new PrintStream(me.getOutputStream());
            in = new Scanner(me.getInputStream());
        }catch(IOException e){
            logger.log(Level.WARNING,"Client failed to connect");
            this.me = null;
        }
        out.println(this.pseudo);
        out.println("1");
    }

    /**
     * Disconnect from the server
     */
    public void disconnect() {
        this.timer.cancel();
        try {
            this.me.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Intercept server messages
     */
    public void playGame() {
            String code = receive();

            switch (code){
                case "B" :
                    this.updateGame();
                    break;

                case "?":
                    myTurn = true;
                    break;

                case "M":
                    String score0 = receive();
                    String score1 = receive();
                    String matchNum = receive();
                    this.mainController.getGranaryController().updateMatchData(score0,score1,matchNum);
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
                            if(leave){
                                Platform.exit();
                                System.exit(0);
                            }
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

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setContentText(result);
                            a.setTitle("Jeu Terminé !");
                            a.showAndWait();
                        }
                    });
                    break;
                case "S":
                    this.mainController.needSave();
                    break;


            }

    }

    /**
     * Update of the game
     */
    public void updateGame(){
        String boardSeeds = receive();
        String boardAvailable = receive();
        String granaryPlayer0 = receive();
        String granaryPlayer1 = receive();

        this.mainController.updateGame(boardSeeds,boardAvailable,granaryPlayer0,granaryPlayer1);
    }

    /**
     * Send the game
     * @param toSend : string
     * @param isPriority : boolean
     */
    public void send(String toSend, Boolean isPriority){

        if(myTurn || isPriority){
            this.getOutput().println(toSend);
        }
        else{
            if(alreadySend ==1) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Action reserved to actual player !");
                a.setTitle("Action forbidden");
                a.showAndWait();
                alreadySend +=1;
            }else{
                alreadySend =1;
            }
        }
    }

    /**
     * Receive from the server
     * @return string
     */
    public String receive(){
        return this.getInput().nextLine();
    }

    /**
     * Get client input
     * @return scanner
     */
    private Scanner getInput() {
        return this.in;
    }

    /**
     * Get client output
     * @return print stream
     */
    public PrintStream getOutput(){
        return this.out;
    }

    /**
     * Main to launch the client and the view
     * @param args : args
     */
    public static void main(String[] args){
        MancalaApp.main(args);


    }

    /**
     * Get my turn
     * @return boolean
     */
    public boolean getMyTurn(){
        return this.myTurn;
    }

    /**
     * set file
     * @param file : file where we will save the data
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Set leave if the player is going to quit
     * @param leave boolean
     */
    public void setLeave(boolean leave) {
        this.leave = leave;
    }
}
