package ensi.fr.mancala.client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private InetAddress addr;
    private int port;
    private Socket me;
    private String pseudo;
    private Scanner in;
    private PrintStream out;

    public Client(InetAddress addr, int port, String pseudo){
        this.addr = addr;
        this.port = port;
        this.pseudo = pseudo;
    }

    public void connect(){
        try {
            this.me = new Socket(this.addr, this.port);
            out = new PrintStream(me.getOutputStream());
            in = new Scanner(me.getInputStream());
        }catch(IOException e){
            System.err.println("Client failed to connect");
            e.printStackTrace();
        }
        out.println(this.pseudo);
        System.out.println(in.nextLine());
        System.out.println(in.nextLine());
        System.out.println(in.nextLine());

        out.println("1");
        return;
    }


    private void play() {


    }

    public static void main(String[] args){
        try {
            Client c = new Client(InetAddress.getLocalHost(), 8080,"A");
            c.connect();
            c.play();
        }catch(UnknownHostException e){
            System.err.println("Client creation failed");
        }
        System.out.println();
    }

}
