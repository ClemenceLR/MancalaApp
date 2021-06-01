package ensi.fr.mancala.model;

public class Check {

    public static int nbCellsAvailable;
    public static void setCellAvailable(Board b, int idActivePlayer){
        int startHolePlayerId;
        int startHoleOpponentId;
        int i;
        int nbCellsAvailable;
        Cell cellChecked;
        boolean isOpponentSideEmpty = true;

        startHolePlayerId = (idActivePlayer == 1?0:6);
        startHoleOpponentId = (startHolePlayerId + 6) % 12;
        nbCellsAvailable = 0;

        for(i=startHoleOpponentId;i<startHoleOpponentId+6;i++){
            if(b.holes[i].getNbSeeds() != 0){
                isOpponentSideEmpty = false;
            }
            b.holes[i].setAvailable(false);
        }

        for(i=startHolePlayerId;i<startHolePlayerId+6;i++){
            cellChecked = b.holes[i];
            if(cellChecked.getNbSeeds() == 0){
                cellChecked.setAvailable(false);
            }else{
                if(!isOpponentSideEmpty){
                    cellChecked.setAvailable(true);
                    nbCellsAvailable ++;
                }
                //si l'adversaire est affamé on vérifie si l'on peut lui remettre des graines
                else{
                    if(cellChecked.getNbSeeds() < (startHolePlayerId+6) - i){
                        cellChecked.setAvailable(false);
                    }
                    else{
                        cellChecked.setAvailable(true);
                        nbCellsAvailable ++;
                    }
                }
            }
        }

        Check.nbCellsAvailable = nbCellsAvailable;
    }


    public static boolean isEndedGame(Game g){
        //TODO good luck have fun
        return false;
    }

}
