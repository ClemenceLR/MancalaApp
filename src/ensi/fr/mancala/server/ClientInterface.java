package ensi.fr.mancala.server;

import ensi.fr.mancala.server.model.Player;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
/**
 * Managing client on the interface side
 * @author : Guillaume Hasseneyer
 * @author : Clémence Le Roux
 */
public class ClientInterface{
    private final Player player;
    private Scanner in;
    private PrintStream out;
    private final Socket me;

    /**
     * Client constructor
     * @param client socket client
     */
    public ClientInterface(Socket client){
        this.me = client;
        try {
            this.in = new Scanner(this.me.getInputStream());
            this.out = new PrintStream(this.me.getOutputStream());
        } catch (IOException e) {
            System.err.println("Client failed to connect");
        }
        this.player = new Player(in.nextLine());
    }

    /**
     * get Socket
     * @return socket
     */
    public Socket getSocket() {return  this.me; }

    /**
     * get Player
     * @return player designed by the socket
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * return output
     * @return socket output
     */
    public PrintStream getOutput() {
        return this.out;
    }

    /**
     * return input
     * @return socket input
     */
    public Scanner getInput() {
        return this.in;
    }
}
