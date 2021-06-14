package ensi.fr.mancala.client;

import ensi.fr.mancala.client.controller.MainController;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private MainController mainController;
    private InetAddress addr;
    private int port;
    private Socket me;
    private String pseudo;
    private Scanner in;
    private PrintStream out;
    private CellId cellClicked;
    private Logger logger;

    public Client(InetAddress addr, int port, String pseudo){
        this.addr = addr;
        this.port = port;
        this.pseudo = pseudo;
        this.cellClicked = CellId.MINUSONE;
    }

    public void setCellClicked(String cellClicked){
        this.cellClicked = CellId.valueOf(cellClicked);
    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
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
        /*
        System.out.println(in.nextLine());
        System.out.println(in.nextLine());
        System.out.println(in.nextLine());
        */
        out.println("1");
        return;
    }


    public void play() {
            String r = receive();
            System.out.println(r);

            if(r.equals("?")){//TODO transfo en switch
                while(Integer.parseInt(this.cellClicked.label)<0) {
                    //TODO le contrôleur envoie le num de la case cliquée (setCellClicked) Client.setCellClicked()
                }
                send(""+this.cellClicked);
                this.cellClicked = CellId.MINUSONE;
            }

    }
    public void send(String toSend){
        this.getOutput().println(toSend);
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

}