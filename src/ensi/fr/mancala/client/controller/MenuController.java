package ensi.fr.mancala.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);
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
        }else{
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("It is already your turn, you can't rollback");
            a.setTitle("No rollback available");
            a.showAndWait();
        }
    }

    public void exit(){
        //
        this.mainController.getClient().send("Q",true);
        Platform.exit();
        System.exit(0);
    }

    public void newMatch(ActionEvent actionEvent) {
        //TODO
    }
}
