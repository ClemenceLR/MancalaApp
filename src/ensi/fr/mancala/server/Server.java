package ensi.fr.mancala.server;

import ensi.fr.mancala.server.model.*;

import java.util.Scanner;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Class server
 * @author : Guillaume Hasseneyer
 * @author : Clémence Le Roux
 *
 */
public class Server {
    private final int port;
    private Match match;
    //private Game g;
    private ServerSocket server;
    private Game gameSave;
    private final ClientInterface[] clients;

    /**
     * Server constructor
     * @param port : int port
     */
    public Server(int port){
        this.port = port;
        this.clients = new ClientInterface[2];
    }

    /**
     * Starting the server
     */
    public void start(){

        try {
            this.server = new ServerSocket(this.port);
            this.clients[0] = new ClientInterface(this.server.accept());
            this.clients[1] = new ClientInterface(this.server.accept());

            this.match = new Match(this.clients[0].getPlayer(), this.clients[1].getPlayer());
            sendNames(0);
            sendNames(1);
            sendMatchData();

        }catch (IOException e){
            System.err.println("Server failed to start : port " + this.port + " already in use");
            e.printStackTrace();

        }

    }

    /**
     * Send Names
     * @param idClient : int id of the client we want to send infos to
     */
    public void sendNames(int idClient){

        send(idClient, "N");
        send(idClient, this.clients[0].getPlayer().getName());
        send(idClient, this.clients[1].getPlayer().getName());

    }

    /**
     * Send board updated
     */
    public void sendUpdateGame(){
        int playerId = this.match.getGame().activePlayer.id - 1;
        int opponentId = (playerId +1) %2;
        System.out.println("ALORS" + playerId + "POURQUOI" + opponentId);
        send(playerId, "B");//Sending the updated board to first client
        send(playerId, this.match.getGame().board.toString());
        send(playerId, this.match.getGame().board.cellAvailable());
        send(playerId, "" + this.clients[0].getPlayer().granary);
        send(playerId, "" + this.clients[1].getPlayer().granary);

        send(opponentId, "B");//Sending the updated board to second client
        send(opponentId, this.match.getGame().board.toString());
        send(opponentId, this.match.getGame().board.forbidPlay());
        send(opponentId, "" + this.clients[0].getPlayer().granary);
        send(opponentId, "" + this.clients[1].getPlayer().granary);
    }

    /**
     * Send match data
     */
    private void sendMatchData(){
        String score0String = String.valueOf(this.match.getScore(0));
        String score1String = String.valueOf(this.match.getScore(1));
        String matchNumString = String.valueOf(this.match.getMatchNum());

        send(0, "M");//Sending the updated board to first client
        send(0, score0String);
        send(0, score1String);
        send(0, matchNumString);

        send(1, "M");
        send(1, score0String);
        send(1, score1String);
        send(1, matchNumString);
    }

    /**
     * Receiving number
     * @param id : int
     * @return number received
     */
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

    /**
     * Receiving
     * @param id : int
     * @return message received
     */
    public String receive(int id){
        Scanner sc = this.clients[id].getInput();
        return sc.nextLine();
    }

    /**
     * Send
     * @param id : int id
     * @param toSend : string param to send
     */
    public void send(int id, String toSend){
        this.clients[id].getOutput().println(toSend);
    }

    /**
     * Play a game
     */
    public void play(){
        int winnerId;
        int input;
        String activePlayerMessage;
        String opponentPlayerMessage;
        int lastVisitedCell;
        do{
            int activePlayerID = this.match.getGame().activePlayer.id-1;
            int opponentPlayerID = this.match.getGame().passivePlayer.id-1;

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
                            send(opponentPlayerID,this.match.toString());
                            break;
                        case "L":
                            String file = receive(opponentPlayerID);

                            System.out.println(this.match);

                            this.match = ManageFile.loadMatchFromString(file);
                            System.out.println(this.match);

                            System.out.println("AP ID : " + activePlayerID + "OID :"+opponentPlayerID);

                            if(this.match.getGame().activePlayer.id-1 != activePlayerID){
                                int temp = activePlayerID;
                                activePlayerID = opponentPlayerID;
                                opponentPlayerID = temp;
                                send(activePlayerID,"C");
                                send(opponentPlayerID,"C");
                            }
                            sendMatchData();
                            sendUpdateGame();
                            break;
                        case "Q":
                            send(activePlayerID,"S");
                            break;
                        case "U":
                            Game game = new Game(this.match.getGame().passivePlayer, this.match.getGame().activePlayer,
                                    this.gameSave.toString());
                            this.match.setGame(game);
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
                            this.gameSave.board = new Board(this.match.getGame().board.getHoles());
                            this.gameSave.activePlayer = this.match.getGame().activePlayer;
                            this.gameSave.passivePlayer = this.match.getGame().passivePlayer;
                            lastVisitedCell = this.match.getGame().play(input);
                            break;
                        case "L":
                            String file = receive(activePlayerID);

                            System.out.println(this.match);
                            this.match = ManageFile.loadMatchFromString(file);
                            if(this.match.getGame().activePlayer.id-1 != activePlayerID){
                                int temp = activePlayerID;
                                activePlayerID = opponentPlayerID;
                                opponentPlayerID = temp;
                                send(activePlayerID,"C");
                                send(opponentPlayerID,"C");
                            }
                            sendMatchData();
                            sendUpdateGame();
                            break;
                        case "Q":
                            send(opponentPlayerID,"S");
                            //String choice = receive(opponentPlayerID);
                            send(activePlayerID,this.match.toString());
                            break;
                        case "S":
                            break;
                        case "F":
                             if(this.match.getGame().board.getTotalSeeds() <=10) {
                                 send(opponentPlayerID, "ff");
                                 String r = receive(opponentPlayerID);
                                 if (r.equals("f")) {
                                     this.match.getGame().splitRemainingSeed();
                                     break;
                                 }
                                 send(activePlayerID, "n");
                             }
                                break;
                        case "G":
                            send(activePlayerID,"G");
                            send(activePlayerID,this.match.toString());
                            break;
                    }
                }
            }
            while(lastVisitedCell == -1);
            Check.checkEatableCells(lastVisitedCell,this.match.getGame().activePlayer, this.match.getGame().board);

            this.match.getGame().changePlayer();
            Check.setCellAvailable(this.match.getGame().board,this.match.getGame().activePlayer.id);
            winnerId = Check.isEndedGame(this.match.getGame());

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
            this.match.incScore(winnerId);
        }

        this.match.incMatchNum();
        sendMatchData();

        Game game = new Game(this.clients[0].getPlayer(),this.clients[1].getPlayer());
        this.match.setGame(game);
    }

    /**
     * End the connection with the clients
     */
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


    /**
     * Main to launch the server
     * @param args args
     */
    public static void main(String[] args){
        Server s = new Server(8080);
        s.start();
        //TANT QUE JE N'AI PAS RECU LA VALIDATION DES 2
        do {
            s.sendUpdateGame();
            s.play();
        }
        while(s.match.getMatchNum() < Match.nbGames + 1);
        s.endConnection();
    }




}
