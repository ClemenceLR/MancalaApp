package ensi.fr.mancala.client.controller;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MenuController {

    MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }


    public void loadGame() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a File Save");
        String res = "";

        File file = fc.showOpenDialog(this.mainController.getMainPane().getScene().getWindow());
        Scanner sc;
        if (file != null) {
            try {
                sc = new Scanner(file);
                while (sc.hasNext()) {
                    res += sc.nextLine();
                }
                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            this.mainController.getClient().send("L", false);
            this.mainController.getClient().send(res, false);
        }
    }

    public void saveGame(){

        FileChooser fc = new FileChooser();
        fc.setTitle("Choose where to save the file");

        File file = fc.showSaveDialog(this.mainController.getMainPane().getScene().getWindow());

        if (file != null) {
            this.mainController.getClient().send("G", true);
        }
        /*
        try {
            new PrintStream(file).println("");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        */
        this.mainController.getClient().setFile(file);
    }

    public void forfeit() {
        this.mainController.getClient().send("F",false);
        this.mainController.getClient().setMyTurn(false);
    }

    public void about(){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("The Mancala Application was created by Guillaume Haseneyer and Clémence Le Roux\n ENSICAEN students in 1A App");
        a.setTitle("About");
        a.showAndWait();
    }

    public void undo(){
        if(!this.mainController.getClient().getMyTurn()) {
            this.mainController.getClient().send("U", true);
        }
    }
}
