package ensi.fr.mancala.server;

import ensi.fr.mancala.server.model.Check;
import ensi.fr.mancala.server.model.Game;
import ensi.fr.mancala.server.model.Player;
import javafx.beans.value.ObservableIntegerValue;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private int port;
    private Game g;
    private List<Player> playerList;
    private ServerSocket server;
    private Thread t1, t2;

    private ClientInterface[] clients;

    public Server(int port){
        this.port = port;
        this.clients = new ClientInterface[2];
        this.playerList = new ArrayList<Player>();
    }



    public boolean start(){
        try {
            this.server = new ServerSocket(this.port);
            this.clients[0] = new ClientInterface(this.server.accept());
            this.clients[1] = new ClientInterface(this.server.accept());

            this.g = new Game(this.clients[0].getPlayer(), this.clients[1].getPlayer());
            send(0, this.clients[1].getPlayer().getName());
            send(0, this.g.board.toString());
            send(0, this.g.board.cellAvailable());

            send(1, this.clients[0].getPlayer().getName());
            send(1, this.g.board.toString());
            send(1, this.g.board.cellAvailable());

            return Integer.parseInt(receive(0)) == 1 && Integer.parseInt(receive(1)) == 1;


        }catch (IOException e){
            System.err.println("Server failed to start : port " + this.port + " already in use");
            e.printStackTrace();
        }

        return false;
    }

    //A la connection = Pseudo
    //Num de case = num
    //Fin de tour = ok
    //Capituler = c
    //Capitulation = y
    //Refus = n -> Send refus de cap rc
    //rollback = r
    public String receive(int id){
        return this.clients[id].getInput().nextLine();

    }
    //Init plateau = P:1,Pseudo:playeradversepseudo,0-0-0-0-0-0-0-0 (player num +plateau)
    //Send choix = ?

    //Bon move = B:0-0-0-0-0-00-0-0-0 (plateau)
    //Win = +
    //Eq = =
    //Loose = -
    //Captituler = c
    //Refus de cap = rc
    //
    public void send(int id, String toSend){
        this.clients[id].getOutput().println(toSend);
    }

    public void playGame(){
        int termine = -1;
        int cpt = 0;
        int input;
        int lastVisitedCell;
        do{
            int cliID = this.g.activePlayer.id-1;
            send(cliID,this.g.board.toString());
            System.out.println("turn " + cpt + " - Player " + cliID+1);

            do{
                send(cliID,"?"); //Demande de choix au client
                input = Integer.parseInt(receive(cliID));
                System.out.println("Cell " + input);
                lastVisitedCell = this.g.play(input);

            }
            while(lastVisitedCell == -1);

            Check.checkEatableCells(lastVisitedCell,this.g.activePlayer, this.g.board);

            this.g.changePlayer();
            Check.setCellAvailable(this.g.board,this.g.activePlayer.id);
            termine = Check.isEndedGame(this.g);
            cpt++;
        }
        while(termine == -1);

        //switch (termine)
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
           s.playGame();
    }


}
