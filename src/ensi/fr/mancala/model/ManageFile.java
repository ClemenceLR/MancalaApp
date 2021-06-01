package ensi.fr.mancala.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ManageFile {

    public static void saveGame(String fileName, Game g){
        //TODO respecter format : nomj1 ; granaryj1 ; nomj2 ; granaryj2 ; joueurActif ; board (1-2-3etc)
    }

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
                //TODO Parsing des données a modifier avec la logique des matchs
                Player p1 = new Player();
                p1.name = d[0];
                p1.granary = Integer.parseInt(d[1]);

                Player p2 = new Player();
                p2.name = d[2];
                p2.granary = Integer.parseInt(d[3]);
                if(d[4].equals(p1.name)){
                    g.activePlayer = p1;
                    g.passivePlayer = p2;
                }else{
                    g.activePlayer = p2;
                    g.passivePlayer = p1;
                }
                Board b = new Board(d[5]);
                g.board = b;


            }
            reader.close();
        }catch(FileNotFoundException e){
            System.out.println("Le fichier n'existe pas");
            e.printStackTrace();
        }

        return g;
    }
}
