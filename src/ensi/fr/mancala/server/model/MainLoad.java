package ensi.fr.mancala.server.model;

import ensi.fr.mancala.client.controller.MainController;

public class MainLoad {

    public static void main(String[] args){
        ManageFile.loadMatchFromString("ME0:3:1:1:yy;0;xx;0;2;0-1-6-6-6-5-0-1-6-6-6-5");
    }
}
