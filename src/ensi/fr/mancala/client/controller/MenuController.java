package ensi.fr.mancala.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Menu controller
 *  @author : Guillaume Hasseneyer
 *  @author : Clémence Le Roux
 *
 */
public class MenuController {

    MainController mainController;

    public static boolean isCellNumbersHide = false;
    private static boolean isAmbiantSound = true;
    /**
     * Access the main controller by stocking it
     * @param mainController : main controller
     */
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * Load a game
     */
    public void loadGame() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a File Save");
        StringBuilder res = new StringBuilder();

        File file = fc.showOpenDialog(this.mainController.getMainPane().getScene().getWindow());
        Scanner sc;
        if (file != null) {
            try {
                sc = new Scanner(file);
                while (sc.hasNext()) {
                    res.append(sc.nextLine());
                }
                sc.close();
            } catch (FileNotFoundException e) {
                System.err.println("The server failed to close");
            }

            this.mainController.getClient().send("L@" + res.toString(), true);
        }
    }

    /**
     * Save a game
     */
    public void saveGame(){

        FileChooser fc = new FileChooser();
        fc.setTitle("Choose where to save the file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showSaveDialog(this.mainController.getMainPane().getScene().getWindow());

        if (file != null) {
            this.mainController.getClient().send("G", true);
        }
        this.mainController.getClient().setFile(file);
    }

    /**
     * Forfeit
     */
    public void forfeit() {
        this.mainController.getClient().send("F",false);
        this.mainController.getClient().setMyTurn(false);
    }

    /**
     * About
     */
    public void about(){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("The Mancala Application was created by Guillaume Haseneyer and Clémence Le Roux\n ENSICAEN students in 1A App\n 2020-2021");
        a.setTitle("About");
        a.showAndWait();
    }

    /**
     * Undo
     */
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

    /**
     * Exit
     */
    public void exit(){
        //
        this.mainController.getClient().send("Q",true);
        Platform.exit();
        System.exit(0);
    }

    public void newMatch() {

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Save ?");
        a.setContentText("Would you like to save ?");

        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            this.saveGame();
        }
        this.mainController.getClient().send("R", false);
    }

    public void changeSeedDisplay(ActionEvent actionEvent) {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        if(MenuController.isCellNumbersHide){
            menuItem.setText("Hide seeds numbers");
            this.mainController.getBoardController().changeSeedsDisplay(true);
            MenuController.isCellNumbersHide = false;
        }
        else{
            menuItem.setText("Show seeds numbers");
            this.mainController.getBoardController().changeSeedsDisplay(false);
            MenuController.isCellNumbersHide = true;
        }

    }


    public void changeOnMouseEnteredOption(ActionEvent actionEvent) {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        BoardController.showOnMouseEntered = !BoardController.showOnMouseEntered;

        if(BoardController.showOnMouseEntered){
            menuItem.setText("Don't show seeds numbers on mouse entered");
        }
        else{

            menuItem.setText("Show seed number on mouse entered");
        }

    }

    /**
     * Manage the playing of the sounds of the app
     * @param actionEvent : trigger
     */
    public void playSound(ActionEvent actionEvent){
        MenuItem menuItem = (MenuItem) actionEvent.getSource();

        if(isAmbiantSound){
            this.mainController.getAmbiantSound().pause();
            this.mainController.getButtonSound().stop();
            menuItem.setText("Play sounds");
            isAmbiantSound = false;
            BoardController.setIsAmbiantSound(false);
        }else{
            this.mainController.getAmbiantSound().play();
            menuItem.setText("Stop sounds");
            BoardController.setIsAmbiantSound(true);
            isAmbiantSound = true;
        }
    }

    public void showRules() {

        String rules = this.readRules();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(rules);
        a.setTitle("Rules");
        a.setHeaderText("Rules");
        a.showAndWait();

    }

    public String readRules(){
        String rules = "";
        try
        {

            FileInputStream file =new FileInputStream(new File(getClass().getResource("/rules/rules.txt").toURI()));
            Scanner scanner = new Scanner(file);
            System.out.println("File :" + file);

            while(scanner.hasNextLine())
            {
                System.out.println(rules);
                rules += scanner.nextLine();
                rules += "\n\n";
            }
            scanner.close();
        }
        catch(IOException | URISyntaxException e)
        {
            System.err.println(e);
        }
        return rules;
    }
}
