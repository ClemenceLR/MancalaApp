package ensi.fr.mancala.server.model;

/**
 * Static class in charge to check the game status
 * @author Guillaume Haseneyer
 * @author Clemence Le Roux
 */
public class Check {

    public static int nbCellsAvailable;


    private Check(){

    }
    /**
     * setCellAvailable : go through the board to set which cell is available
     * @param b : board
     * @param idActivePlayer : id of the current player
     */
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

    /**
     * Check if the game is ended
     * @param g : game
     * @return ending code (2 : match null, player id, -1 : game continues)
     */
    public static int isEndedGame(Game g){
        int totalSeeds = g.board.getTotalSeeds();
        int startHolePlayerId = (g.passivePlayer.getId() == 1?0:6);
        int startHoleOpponentId = (startHolePlayerId + 6) % 12;

        if(g.passivePlayer.getGranary() >= 25){
            return g.passivePlayer.getId()-1;
        }

        if(Check.nbCellsAvailable == 0){
            g.activePlayer.addGranary(totalSeeds);
            if(g.activePlayer.getGranary() > g.passivePlayer.getGranary()){
                return g.activePlayer.getId()-1;
            }else if(g.activePlayer.getGranary() == g.passivePlayer.getGranary()){
                return 2;
            }else{
                return g.passivePlayer.getId()-1;
            }
        }

        if(totalSeeds <= 6 && ennemyIsHungry(g.board, startHoleOpponentId) && Check.nbCellsAvailable == 0){
            return g.passivePlayer.getId()-1;
        }


        if(totalSeeds < 6 && g.passivePlayer.getGranary() < 24 && g.activePlayer.getGranary() < 24){
            return 2;
        }

        return -1;
    }

    /**
     * checkEatableCells : check if the player can win some seeds
     * @param lastVisitedCell : id of the lastvisitedcell
     * @param activePlayer : id of the active player
     * @param b : board
     */
    public static void checkEatableCells(int lastVisitedCell,Player activePlayer, Board b) {
        boolean lastCaseToActivePlayer = false;
        Board cpy = new Board(b.holes);
        int playerGain;

        if ((activePlayer.getId() == 1 && lastVisitedCell < 6) ||(activePlayer.getId() == 2) && lastVisitedCell >= 6 ){
            lastCaseToActivePlayer = true;
        }
        if(!lastCaseToActivePlayer){
            int startHolePlayerId = (activePlayer.getId() == 1?0:6);
            int startHoleOpponentId = (startHolePlayerId + 6) % 12;
            playerGain = b.eatCell(lastVisitedCell, startHoleOpponentId);
            if(Check.ennemyIsHungry(b, startHoleOpponentId)){
                b.holes = cpy.holes;

            }else{
                activePlayer.addGranary(playerGain);
            }
        }
    }

    /**
     * ennemyIsHungry : check if we can play the move without starving the opponent
     * @param b : board
     * @param startHoleOpponentId : starting id of the opponent side cell
     * @return boolean
     */
    public static boolean ennemyIsHungry(Board b, int startHoleOpponentId){

        for (int i = startHoleOpponentId; i<startHoleOpponentId+6;i++){
            if(b.holes[i].getNbSeeds() != 0){
                return false;
            }
        }
        return true;
    }

}


