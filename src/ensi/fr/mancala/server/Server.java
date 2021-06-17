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
    private ServerSocket serverSocket;
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
            this.serverSocket = new ServerSocket(this.port);
            this.clients[0] = new ClientInterface(this.serverSocket.accept());
            this.clients[1] = new ClientInterface(this.serverSocket.accept());

            this.match = new Match(this.clients[0].getPlayer(), this.clients[1].getPlayer());
            sendNames(0);
            sendNames(1);
            sendMatchData();

        }catch (IOException e){
            System.err.println("Server failed to start : port " + this.port + " already in use");

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
        int playerId = this.match.getGame().getActivePlayer().getId() - 1;
        int opponentId = (playerId +1) %2;

        send(playerId, "B");//Sending the updated board to first client
        send(playerId, this.match.getGame().getBoard().toString());
        send(playerId, this.match.getGame().getBoard().cellAvailable());
        send(playerId, "" + this.clients[0].getPlayer().getGranary());
        send(playerId, "" + this.clients[1].getPlayer().getGranary());

        send(opponentId, "B");//Sending the updated board to second client
        send(opponentId, this.match.getGame().getBoard().toString());
        send(opponentId, this.match.getGame().getBoard().forbidPlay());
        send(opponentId, "" + this.clients[0].getPlayer().getGranary());
        send(opponentId, "" + this.clients[1].getPlayer().getGranary());
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
            int activePlayerID = this.match.getGame().getActivePlayer().getId()-1;
            int opponentPlayerID = this.match.getGame().getPassivePlayer().getId()-1;

            do{
                lastVisitedCell = -1;
                send(activePlayerID,"?"); //Demande de choix au client

                do {
                    activePlayerMessage = receiveNB(activePlayerID);
                    opponentPlayerMessage = receiveNB(opponentPlayerID);
                }while(activePlayerMessage.equals("") && opponentPlayerMessage.equals(""));

                if (!opponentPlayerMessage.equals("")){
                    switch (opponentPlayerMessage) {
                        case "G":
                            send(opponentPlayerID,"G");
                            send(opponentPlayerID,this.match.toString());
                            break;
                        case "L":
                            loadMatch(opponentPlayerID, opponentPlayerID, activePlayerID);
                            activePlayerID = this.match.getGame().getActivePlayer().getId()-1;
                            opponentPlayerID = this.match.getGame().getPassivePlayer().getId()-1;
                            break;
                        case "Q":
                            send(activePlayerID,"S");
                            break;
                        case "U":
                            if(gameSave != null) {
                                this.match.setGame(new Game(gameSave));
                                send(activePlayerID, "C");
                                send(opponentPlayerID, "C");
                                int invert = activePlayerID;
                                activePlayerID = opponentPlayerID;
                                opponentPlayerID = invert;
                                updatePlayerInClients();
                                sendUpdateGame();
                                this.gameSave = null;
                            }
                            break;
                        default:
                            break;

                    }
                }
                else{
                    if (!activePlayerMessage.equals("")) {

                        switch(activePlayerMessage) {
                            case "N":
                                input = Integer.parseInt(receive(activePlayerID));
                                if (input > 11) {
                                    input = -1;
                                }
                                this.gameSave = new Game();
                                this.gameSave.setBoard(new Board(this.match.getGame().getBoard().getHoles()));
                                this.gameSave.setActivePlayer(new Player(this.match.getGame().getActivePlayer()));
                                this.gameSave.setPassivePlayer(new Player(this.match.getGame().getPassivePlayer()));
                                lastVisitedCell = this.match.getGame().play(input);
                                System.out.println("Play : " + this.match.getGame().toString());
                                break;
                            case "L":
                                loadMatch(activePlayerID,opponentPlayerID,activePlayerID);
                                activePlayerID = this.match.getGame().getActivePlayer().getId()-1;
                                opponentPlayerID = this.match.getGame().getPassivePlayer().getId()-1;
                                break;
                            case "Q":
                                send(opponentPlayerID,"S");
                                send(activePlayerID,this.match.toString());
                                break;
                            case "F":
                                if(this.match.getGame().getBoard().getTotalSeeds() <=10) {
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

                            case "R":
                                this.match = new Match(this.clients[0].getPlayer(), this.clients[1].getPlayer());
                                sendMatchData();
                                sendUpdateGame();
                                break;
                            default:
                                break;
                        }
                    }

                }


            }
            while(lastVisitedCell == -1);
            Check.checkEatableCells(lastVisitedCell,this.match.getGame().getActivePlayer(), this.match.getGame().getBoard());

            this.match.getGame().changePlayer();
            Check.setCellAvailable(this.match.getGame().getBoard(),this.match.getGame().getActivePlayer().getId());
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
        this.gameSave = null;
    }

    private void updatePlayerInClients() {
        Player activePlayer = this.match.getGame().getActivePlayer();
        Player passivePlayer = this.match.getGame().getActivePlayer();

        int activePlayerClientId = (this.match.getGame().getActivePlayer().getId() +1)%2;
        int passivePlayerClientId = (this.match.getGame().getActivePlayer().getId()+1)%2;

        this.clients[activePlayerClientId].setPlayer(activePlayer);
        this.clients[passivePlayerClientId].setPlayer(passivePlayer);

    }

    /**
     * Load a match from received data
    * @param idP1 : trigger Id
     * @param opponentPlayerID : current opponent id
     * @param activePlayerID : current active player id
     */
    public void loadMatch(int idP1, int opponentPlayerID, int activePlayerID){
        String file = receive(idP1);
        Match m;

        m = ManageFile.loadMatchFromString(file);

        if(m != null) {
            this.match = m;
            if (this.match.getGame().getActivePlayer().getId() - 1 != activePlayerID) {
                send(activePlayerID, "C");
                send(opponentPlayerID, "C");
            }
            sendMatchData();
            sendUpdateGame();
        }
    }

    /**
     * End the connection with the clients
     */
    public void endConnection(){
        try {
            send(0,"D");
            send(1,"D");
            this.serverSocket.close();
        }catch(IOException e){
            System.err.println("The server failed to close");
        }
        }


    /**
     * Main to launch the server
     * @param args args
     */
    public static void main(String[] args){
        Server s = new Server(42000);
        s.start();
        do {
            s.sendUpdateGame();
            s.play();
        }
        while(s.match.getMatchNum() < Match.NB_GAMES + 1);
        s.endConnection();
    }




}
