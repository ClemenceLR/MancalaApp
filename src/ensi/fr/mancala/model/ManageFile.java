package ensi.fr.mancala.model;

import java.io.*;
import java.util.Scanner;

public class ManageFile {

    public static void saveGame(String fileName, Game g){
        //TODO respecter format : nomj1 ; granaryj1 ; nomj2 ; granaryj2 ; joueurActif ; board (1-2-3etc)
        String fileNameC = ".//saves//" + fileName + ".txt";

        try {
            File saveFile = new File(fileNameC);
            if (saveFile.createNewFile()) {
                System.out.println("File saved");
            } else {
                System.out.println("\nFile alredy exists");
            }
        }catch(IOException e){
                System.out.println("An error occured");
                e.printStackTrace();
        }
        try(FileWriter saveData = new FileWriter(fileNameC)){
            
            String prepareData = "";

            if(g.activePlayer.id == 1){
                prepareData += g.activePlayer.name + ";" + g.activePlayer.granary + ";" + g.passivePlayer.name + ";" + g.passivePlayer.granary;
            }else{
                prepareData += g.passivePlayer.name + ";" +g.passivePlayer.granary + ";" + g.activePlayer.name + ";" + g.activePlayer.granary;
            }
            prepareData +=";"+ g.activePlayer.id+";";
            prepareData += g.board.toString();
            saveData.write(prepareData);
            saveData.close();
            System.out.println("Save successfull");
        }catch(IOException e){
            System.out.println("An error occured while writing");
            e.printStackTrace();
        }


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
                p1.id = 1;

                Player p2 = new Player();
                p2.name = d[2];
                p2.granary = Integer.parseInt(d[3]);
                p2.id = 2;
                //TODO add player ID
                int currentPlayer = Integer.parseInt(d[4]);
                if(currentPlayer == 1){
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
