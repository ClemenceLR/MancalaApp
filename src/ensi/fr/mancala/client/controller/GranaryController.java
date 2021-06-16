package ensi.fr.mancala.client.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GranaryController {

    @FXML
    private Text granary0;

    @FXML
    private Text granary1;

    @FXML private Text granaryText0;
    @FXML private Text granaryText1;

    MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void setGranary(String nbSeeds, int player){
        if(player == 0){
            granary0.setText(nbSeeds);
        }
        else{
            granary1.setText(nbSeeds);
        }

    }

    public void updateNames(String name0, String name1) {
        this.granaryText0.setText(name0 + " granary ");
        this.granaryText1.setText(name1 + " granary ");
    }
}
