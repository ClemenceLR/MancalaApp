package ensi.fr.mancala.model;

public class Check {
    public static int setCellAvailable(Board b, Player activePlayer){
        int startHolePlayerId;
        int i;
        int nbCellsAvailable;
        Cell cellChecked;

        startHolePlayerId = (activePlayer.id == 1?0:6);
        nbCellsAvailable = 0;

        boolean isOpponentSideEmpty = Check.checkOpponentSideEmpty(b.holes, (startHolePlayerId + 6) % 12);
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

        return nbCellsAvailable;
    }

    private static boolean checkOpponentSideEmpty(Cell[] holes, int opponentHoleStartId){
        int i;
        for(i=opponentHoleStartId; i<opponentHoleStartId+6;i++){
            if(holes[i].getNbSeeds() != 0){
                return false;
            }
        }
        return true;
    }


    public static boolean isEndedGame(Game g){
        //TODO good luck have fun
        return false;
    }

}
