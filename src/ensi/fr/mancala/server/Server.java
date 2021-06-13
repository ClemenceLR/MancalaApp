package ensi.fr.mancala.server;

import ensi.fr.mancala.server.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private int port;
    private Game g;
    private ServerSocket server;
    private Socket[] clients;

    public Server(int port){
        this.port = port;
        this.clients = new Socket[2];
        this.g = new Game();
    }

    public void start(){
        try {
            this.server = new ServerSocket(this.port);
            this.clients[0] = this.server.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Server.this.handleClient(Server.this.clients[0]);
                }
            }).start();
            this.clients[1] = this.server.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Server.this.handleClient(Server.this.clients[1]);
                }
            }).start();
        }catch (IOException e){
            System.err.println("Server failed to start : port " + this.port + " already in use");
            e.printStackTrace();
        }
    }

    public void handleClient(Socket cli){
        System.err.println("Client connected !");
        try {
            Scanner sc = new Scanner(cli.getInputStream());
            System.out.println(sc.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //A la connection = Pseudo
    //Num de case = num
    //Fin de tour = ok
    //Capituler = c
    //Capitulation = y
    //Refus = n -> Send refus de cap rc
    public String receive(){
        return "hapl";

    }
    //Init plateau = P:1,Pseudo:playeradversepseudo,0-0-0-0-0-0-0-0 (player num +plateau)
    //Send choix = ?
    //Mauvais move = x
    //Bon move = 0-0-0-0-0-00-0-0-0 (plateau)
    //Win = +
    //Eq = =
    //Loose = -
    //Captituler = c
    //Refus de cap = rc
    public void send(String toSend){

    }

    public void endConnection(){
        try {
            this.server.close();
        }catch(IOException e){
            System.err.println("The server failed to close");
            e.printStackTrace();
        }
        }


    public static void main(String[] args){
           Server s = new Server(8080);
           s.start();
    }
}
