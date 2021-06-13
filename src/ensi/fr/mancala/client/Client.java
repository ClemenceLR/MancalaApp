package ensi.fr.mancala.client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private InetAddress addr;
    private int port;
    private Socket me;
    private String pseudo;

    public Client(InetAddress addr, int port, String pseudo){
        this.addr = addr;
        this.port = port;
        this.pseudo = pseudo;
    }

    public void connect(){
        try {
            this.me = new Socket(this.addr, this.port);
            new PrintStream(me.getOutputStream()).println(this.pseudo);

        }catch(IOException e){
            System.err.println("Client failed to connect");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try {
            Client c = new Client(InetAddress.getLocalHost(), 8080,"Jean Paul");
            c.connect();
        }catch(UnknownHostException e){
            System.err.println("Client creation failed");
        }
    }
}
