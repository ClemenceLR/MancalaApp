package ensi.fr.mancala.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MenuController {

    MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void play() {
        this.mainController.getClient().play();
    }

    public void loadGame(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a File Save");
        String res = "";

        File file = fc.showOpenDialog(this.mainController.getMainPane().getScene().getWindow());
        Scanner sc;
        if (file != null) {
            try {
                sc = new Scanner(file);
                while(sc.hasNext()){
                    res += sc.nextLine();
                }
                sc.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

            this.mainController.getClient().send("L", false);
            this.mainController.getClient().send(res, false);
        }

    }
}
