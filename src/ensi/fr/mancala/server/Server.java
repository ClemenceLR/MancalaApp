package ensi.fr.mancala.server;

import ensi.fr.mancala.server.model.Check;
import ensi.fr.mancala.server.model.Game;
import java.util.Scanner;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final int port;
    private Game g;
    private ServerSocket server;

    private final ClientInterface[] clients;

    public Server(int port){
        this.port = port;
        this.clients = new ClientInterface[2];
    }



    public boolean start(){
        try {
            this.server = new ServerSocket(this.port);
            this.clients[0] = new ClientInterface(this.server.accept());
            this.clients[1] = new ClientInterface(this.server.accept());

            this.g = new Game(this.clients[0].getPlayer(), this.clients[1].getPlayer());

            send(0, this.clients[1].getPlayer().getName());
            send(1, this.clients[0].getPlayer().getName());

            sendUpdateBoard();

            return Integer.parseInt(receive(0)) == 1 && Integer.parseInt(receive(1)) == 1;


        }catch (IOException e){
            System.err.println("Server failed to start : port " + this.port + " already in use");
            e.printStackTrace();
        }

        return false;
    }

    public void sendUpdateBoard(){
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
    public String receive(int id){
        Scanner sc = this.clients[id].getInput();
        if (sc.hasNextLine()) {
            return sc.nextLine();
        }else {
            return "";
        }
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

                activePlayerMessage = receive(activePlayerID);
                opponentPlayerMessage = receive(opponentPlayerID);

                if (!opponentPlayerMessage.equals("")){
                    //control+z
                }

                try{
                    input = Integer.parseInt(receive(activePlayerID));
                    if(input >11){
                        input = -1;
                    }
                    System.out.println("Cell " + input);
                    lastVisitedCell = this.g.play(input);

                } catch (NumberFormatException e){
                  //here we treat all the code from the active player;

                }




            }
            while(lastVisitedCell == -1);

            Check.checkEatableCells(lastVisitedCell,this.g.activePlayer, this.g.board);

            this.g.changePlayer();
            Check.setCellAvailable(this.g.board,this.g.activePlayer.id);
            termine = Check.isEndedGame(this.g);

            cpt++;

            sendUpdateBoard();
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
           //TANT QUE JE N'AI PAS RECU LA VALIDATION DES 2
           s.playGame();
    }


}
