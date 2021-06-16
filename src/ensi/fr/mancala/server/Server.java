package ensi.fr.mancala.server;

import ensi.fr.mancala.server.model.Board;
import ensi.fr.mancala.server.model.Check;
import ensi.fr.mancala.server.model.Game;

import java.util.Scanner;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final int port;
    private Game g;
    private ServerSocket server;
    private Game gameSave;
    private final ClientInterface[] clients;

    public Server(int port){
        this.port = port;
        this.clients = new ClientInterface[2];
    }


    public void start(){
    //public boolean start(){

        try {
            this.server = new ServerSocket(this.port);
            this.clients[0] = new ClientInterface(this.server.accept());
            this.clients[1] = new ClientInterface(this.server.accept());

            this.g = new Game(this.clients[0].getPlayer(), this.clients[1].getPlayer());
            //this.g = new Game("end_winner");
            sendNames(0);
            sendNames(1);

            sendUpdateGame();
            //return Integer.parseInt(receive(0)) == 1 && Integer.parseInt(receive(1)) == 1;

        }catch (IOException e){
            System.err.println("Server failed to start : port " + this.port + " already in use");
            e.printStackTrace();

        }
        //return false;

    }

    public void sendNames(int idClient){

        send(idClient, "N");
        send(idClient, this.clients[0].getPlayer().getName());
        send(idClient, this.clients[1].getPlayer().getName());

    }

    public void sendUpdateGame(){
        int playerId = this.g.activePlayer.id - 1;
        int opponentId = (playerId +1) %2;

        send(playerId, "B");//Sending the updated board to first client
        send(playerId, this.g.board.toString());
        send(playerId, this.g.board.cellAvailable());
        send(playerId, "" + this.clients[0].getPlayer().granary);
        send(playerId, "" + this.clients[1].getPlayer().granary);

        send(opponentId, "B");//Sending the updated board to second client
        send(opponentId, this.g.board.toString());
        send(opponentId, this.g.board.forbidPlay());
        send(opponentId, "" + this.clients[0].getPlayer().granary);
        send(opponentId, "" + this.clients[1].getPlayer().granary);
    }

    //A la connection = Pseudo
    //Num de case = num
    //Fin de tour = ok
    //Capituler = c
    //Capitulation = y
    //Refus = n -> Send refus de cap rc
    //rollback = r
    public String receiveNB(int id){
        String nextLine;
        Scanner sc = this.clients[id].getInput();
        try {
            if (this.clients[id].getSocket().getInputStream().available() > 0) {
                nextLine = sc.nextLine();
                return nextLine;
            }
            return "";
        }

        catch(IOException e){
            return "";
        }
    }

    public String receive(int id){
        Scanner sc = this.clients[id].getInput();
        return sc.nextLine();
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
        int winnerId;
        int cpt = 0;
        int input;
        String activePlayerMessage;
        String opponentPlayerMessage;
        int lastVisitedCell;
        do{
            int activePlayerID = this.g.activePlayer.id-1;
            int opponentPlayerID = this.g.passivePlayer.id-1;

            //send(cliID,this.g.board.toString());
            System.out.println("turn " + cpt + " - Player " + this.g.activePlayer.id);

            do{
                lastVisitedCell = -1;
                send(activePlayerID,"?"); //Demande de choix au client


                do {
                    activePlayerMessage = receiveNB(activePlayerID);
                    opponentPlayerMessage = receiveNB(opponentPlayerID);
                }while(activePlayerMessage.equals("") && opponentPlayerMessage.equals(""));

                if (!opponentPlayerMessage.equals("")){
                    //control+z
                    switch (opponentPlayerMessage) {
                        case "G":
                            send(opponentPlayerID,this.g.toString());
                            break;
                        case "Q":
                            send(activePlayerID,"S");
                            break;
                        case "U":
                            this.g = new Game(this.g.passivePlayer, this.g.activePlayer, this.gameSave.toString());
                            send(activePlayerID,"C");
                            send(opponentPlayerID,"C");
                            int invert = activePlayerID;
                            activePlayerID = opponentPlayerID;
                            opponentPlayerID = invert;
                            sendUpdateGame();
                            continue;

                    }
                }

                if (!activePlayerMessage.equals("")) {

                    switch(activePlayerMessage) {
                        case "N":
                            input = Integer.parseInt(receive(activePlayerID));
                            if (input > 11) {
                                input = -1;
                            }
                            this.gameSave = new Game();
                            this.gameSave.board = new Board(this.g.board.getHoles());
                            System.out.println("BS : " + this.gameSave.board.toString());
                            this.gameSave.activePlayer = this.g.activePlayer;
                            this.gameSave.passivePlayer = this.g.passivePlayer;
                            lastVisitedCell = this.g.play(input);
                            break;
                        case "L":
                            String file = receive(activePlayerID);
                            this.g = new Game(this.g.activePlayer, this.g.passivePlayer, file);
                            sendUpdateGame();
                            break;
                        case "Q":
                            send(opponentPlayerID,"S");
                            String choice = receive(opponentPlayerID);
                            send(activePlayerID,this.g.toString());
                            break;
                        case "S":

                            break;
                        case "F":
                             if(this.g.board.getTotalSeeds() <=10) {
                                 send(opponentPlayerID, "ff");
                                 String r = receive(opponentPlayerID);
                                 if (r.equals("f")) {
                                     this.g.splitRemainingSeed();
                                     break;
                                 }
                                 send(activePlayerID, "n");
                             }
                                break;
                        case "G":
                            send(activePlayerID,"G");
                            send(activePlayerID,this.g.toString());
                            break;
                    }
                }
            }
            while(lastVisitedCell == -1);
            Check.checkEatableCells(lastVisitedCell,this.g.activePlayer, this.g.board);

            this.g.changePlayer();
            Check.setCellAvailable(this.g.board,this.g.activePlayer.id);
            winnerId = Check.isEndedGame(this.g);

            cpt++;

            sendUpdateGame();
        }
        while(winnerId == -1);

        send(0,"R");
        send(1,"R");

        if (winnerId == 2) {
            send(0, "=");
            send(1, "=");
        } else {
            send(winnerId, "+");
            send((winnerId + 1) % 2, "-");
        }

        endConnection();
    }

    public void endConnection(){
        try {
            send(0,"D");
            send(1,"D");
            this.server.close();
        }catch(IOException e){
            System.err.println("The server failed to close");
            e.printStackTrace();
        }
        }


    public static void main(String[] args){
           Server s = new Server(8080);
           s.start();
           //TANT QUE JE N'AI PAS RECU LA VALIDATION DES 2
           s.playGame();
    }


}
