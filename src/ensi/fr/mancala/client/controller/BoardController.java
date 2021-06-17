package ensi.fr.mancala.client.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Board controller
 *  @author : Guillaume Hasseneyer
 *  @author : Clémence Le Roux
 *
 */
public class BoardController {

    MainController mainController;

    @FXML private StackPane zero;
    @FXML private StackPane one;
    @FXML private StackPane two;
    @FXML private StackPane three;
    @FXML private StackPane four;
    @FXML private StackPane five;
    @FXML private StackPane six;
    @FXML private StackPane seven;
    @FXML private StackPane eight;
    @FXML private StackPane nine;
    @FXML private StackPane ten;
    @FXML private StackPane eleven;

    /**
     * Access the main controller by stocking it
     * @param mainController : main controller
     */
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * Catch the click of the player
     * @param mouseEvent : click
     */
    public void play(MouseEvent mouseEvent) {
        StackPane stackPane = (StackPane) mouseEvent.getSource();
        String stringToSend = (String) stackPane.getUserData();
        this.mainController.getClient().send("N@" + stringToSend, false);
        this.mainController.getClient().setMyTurn(false);

    }

    /**
     * Get the number of the cell
     * @param number : int number of the cell
     * @return int
     */
    public StackPane getCellByNumber(int number){
        switch (number){
            case 0 :
                return zero;
            case 1 :
                return one;
            case 2 :
                return two;
            case 3 :
                return three;
            case 4 :
                return four;
            case 5 :
                return five;
            case 6 :
                return six;
            case 7 :
                return seven;
            case 8 :
                return eight;
            case 9 :
                return nine;
            case 10 :
                return ten;
            default :
                return eleven;

        }

    }

    /**
     *  Update Cell
     * @param cellToUpdate : cell to update
     * @param available : cell available
     * @param nbSeed : nb of seed
     */
    public void updateCell(StackPane cellToUpdate, boolean available, int nbSeed){
        ObservableList<Node> childrens = cellToUpdate.getChildren();
        Circle circle = (Circle) childrens.get(0);
        Text text = (Text) childrens.get(1);

        text.setText("" + nbSeed);

        if(available){
            circle.setFill(Paint.valueOf("LIGHTGREEN"));
        }
        else{
            circle.setFill(Paint.valueOf("LIGHTGREY"));
        }
    }

    public void changeSeedsDisplay(boolean display){
        try{
            zero.getChildren().get(1).setVisible(display);
            one.getChildren().get(1).setVisible(display);
            two.getChildren().get(1).setVisible(display);
            three.getChildren().get(1).setVisible(display);
            four.getChildren().get(1).setVisible(display);
            five.getChildren().get(1).setVisible(display);
            six.getChildren().get(1).setVisible(display);
            seven.getChildren().get(1).setVisible(display);
            eight.getChildren().get(1).setVisible(display);
            nine.getChildren().get(1).setVisible(display);
            ten.getChildren().get(1).setVisible(display);
            eleven.getChildren().get(1).setVisible(display);
        }
        catch(IndexOutOfBoundsException e){

        }



    }
}
