package ensi.fr.mancala.server.model;

import java.io.*;
import java.util.Scanner;
/**
 * ManageFile contains loading and saving method
 * @author Guillaume Haseneyer
 * @author Clemence Le Roux
 **/
public class ManageFile {

    /**
     * Load game from string
     * @param p1 : player one
     * @param p2 : player two
     * @param game : String representing the current game
     * @return game
     */
    public static Game loadGameFromString(Player p1, Player p2, String game){
        Game g = new Game();
        String[] d = game.split(";");
            //TODO Parsing des données a modifier avec la logique des matchs
            p1.name = d[1];
            p1.granary = Integer.parseInt(d[2]);
            p1.id = 1;

            p2.name = d[3];
            p2.granary = Integer.parseInt(d[4]);
            p2.id = 2;
            //TODO add player ID
            int currentPlayer = Integer.parseInt(d[5]);
            if(currentPlayer == 1){
                g.activePlayer = p1;
                g.passivePlayer = p2;
            }else{
                g.activePlayer = p2;
                g.passivePlayer = p1;
            }
        g.board = new Board(d[6]);

            return g;

    }

    /**
     * Load match from a string
     * @param match : string representing the match
     * @return match loaded
     */
    public static Match loadMatchFromString(String match){
        Match m = new Match();
        String[] data = match.split(":");
        if(!data[0].equals("ME0")){
            return null;
        }
        m.setMatchNum(Integer.parseInt(data[1]));
        m.setScore(0,Integer.parseInt(data[2]));
        m.setScore(1,Integer.parseInt(data[3]));

        Game g = new Game();
        String[] d = data[4].split(";");


        Player p1 = new Player();
        p1.name = d[0];
        p1.granary = Integer.parseInt(d[1]);
        p1.id = 1;

        Player p2 = new Player();
        p2.name = d[2];
        p2.granary = Integer.parseInt(d[3]);
        p2.id = 2;

        int currentPlayer = Integer.parseInt(d[4]);
        if(currentPlayer == 1){
            g.activePlayer = p1;
            g.passivePlayer = p2;
        }else{
            g.activePlayer = p2;
            g.passivePlayer = p1;
        }
        g.board = new Board(d[5]);
        m.setGame(g);

        return m;

    }

    /**
     * Load a game from a string
     * @param fileName : name of the file
     * @return game
     */
    public static Game loadGame(String fileName){
        Game g = new Game();
        String fName = ".//saves//" + fileName + ".txt";

        try{
            File gameToLoad = new File(fName);
            Scanner reader = new Scanner(gameToLoad);
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                System.out.println(data);
                String[] d = data.split(";");

                Player p1 = new Player();
                p1.name = d[0];
                p1.granary = Integer.parseInt(d[1]);
                p1.id = 1;

                Player p2 = new Player();
                p2.name = d[2];
                p2.granary = Integer.parseInt(d[3]);
                p2.id = 2;

                int currentPlayer = Integer.parseInt(d[4]);
                if(currentPlayer == 1){
                    g.activePlayer = p1;
                    g.passivePlayer = p2;
                }else{
                    g.activePlayer = p2;
                    g.passivePlayer = p1;
                }
                g.board = new Board(d[5]);


            }
            reader.close();
        }catch(FileNotFoundException e){
            System.out.println("Le fichier n'existe pas");
            e.printStackTrace();
        }

        return g;
    }
}
