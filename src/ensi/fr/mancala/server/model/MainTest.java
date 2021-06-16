package ensi.fr.mancala.server.model;

public class MainTest {
    public static void main(String[] args){

        ManageFile.loadMatchFromString("ME0:1:0:P1;5;P2;5;1;0-0-0-0-1-2-0-0-0-0-0-0").print();
        System.out.println(ManageFile.loadMatchFromString("ME0:1:0:P1;5;P2;5;1;0-0-0-0-1-2-0-0-0-0-0-0"));
    }
}
