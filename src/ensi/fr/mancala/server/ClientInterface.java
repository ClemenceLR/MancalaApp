package ensi.fr.mancala.server;

import ensi.fr.mancala.client.Client;
import ensi.fr.mancala.server.model.Player;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientInterface{
    private Player player;
    private Scanner in;
    private PrintStream out;
    private Socket me;

    public ClientInterface(Socket client){
        this.me = client;
        try {
            this.in = new Scanner(this.me.getInputStream());
            this.out = new PrintStream(this.me.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.player = new Player(in.nextLine());
    }


    public Player getPlayer() {
        return this.player;
    }

    public PrintStream getOutput() {
        return this.out;
    }

    public Scanner getInput() {
        return this.in;
    }
}
