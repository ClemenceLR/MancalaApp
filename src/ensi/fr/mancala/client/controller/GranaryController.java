package ensi.fr.mancala.client.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
/**
 * Granary controller
 *  @author : Guillaume Hasseneyer
 *  @author : Clémence Le Roux
 *
 */
public class GranaryController {

    @FXML
    private Text granary0;

    @FXML
    private Text granary1;

    @FXML private Text granaryText0;
    @FXML private Text granaryText1;

    @FXML private Text matchData;

    MainController mainController;

    /**
     * Access the main controller by stocking it
     * @param mainController : main controller
     */
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * Set the players granary
     * @param nbSeeds : int number of seeds
     * @param player : player
     */
    public void setGranary(String nbSeeds, int player){
        if(player == 0){
            granary0.setText(nbSeeds);
        }
        else{
            granary1.setText(nbSeeds);
        }

    }

    /**
     * Update names
     * @param name0 : string
     * @param name1 : string
     */
    public void updateNames(String name0, String name1) {
        this.granaryText0.setText(name0 + " granary ");
        this.granaryText1.setText(name1 + " granary ");
    }

    /**
     * Update Match Data
     * @param scoreP0 : string
     * @param scoreP1 : string
     * @param matchNum : match num
     */
    public void updateMatchData(String scoreP0, String scoreP1, String matchNum){
        this.matchData.setText("Game " + matchNum + " : score is " + scoreP0 + "-" + scoreP1);
    }
}
