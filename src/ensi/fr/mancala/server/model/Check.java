package ensi.fr.mancala.server.model;

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


    public static int isEndedGame(Game g){
        int totalSeeds = g.board.getTotalSeeds();
        int startHolePlayerId = (g.passivePlayer.id == 1?0:6);
        int startHoleOpponentId = (startHolePlayerId + 6) % 12;

        if(g.passivePlayer.granary >= 25){
            System.out.println(g.passivePlayer.name + " a gagné");
            return 1;
        }

        if(Check.nbCellsAvailable == 0){
            g.activePlayer.granary += totalSeeds;
            if(g.activePlayer.granary > g.passivePlayer.granary){
                System.out.println(g.activePlayer.name + " a gagné : adversaire affamé");
            }else if(g.activePlayer.granary == g.passivePlayer.granary){
                System.out.println(" Match null");
                return 2;
            }else{
                System.out.println(g.passivePlayer.name + " a gagné : adversaire affamé");
            }
            return 1;
        }

        if(totalSeeds <= 6 && ennemyIsHungry(g.board, startHoleOpponentId) && Check.nbCellsAvailable == 0){
            System.out.println(g.passivePlayer.name + " a gagné");
            return 1;
        }


        if(totalSeeds < 6 && g.passivePlayer.granary < 24 && g.activePlayer.granary < 24){
            System.out.println("Match NULL");
            return 2;
        }

        if(totalSeeds <= 10 ){
            System.out.println("Est-ce que le match est null ?");
            if(g.activePlayer.getAcceptancy()){
                System.out.println("Match NULL");
                int gainSplit = totalSeeds /2;
                g.passivePlayer.granary += gainSplit;
                g.activePlayer.granary += (totalSeeds - gainSplit);
                System.out.println("Gains finaux : "+ g.activePlayer.name + " a " +g.activePlayer.granary);
                System.out.println("Gains finaux :" + g.passivePlayer.name + " a " + g.passivePlayer.granary);
                return 2;
            }else{
                return -1;
            }
        }

        return -1;
    }

    public static void checkEatableCells(int lastVisitedCell,Player activePlayer, Board b) {
        boolean lastCaseToActivePlayer = false;
        Board cpy = new Board(b.holes);
        int playerGain = 0;

        if ((activePlayer.id == 1 && lastVisitedCell < 6) ||(activePlayer.id == 2) && lastVisitedCell >= 6 ){
            lastCaseToActivePlayer = true;
        }

        if(!lastCaseToActivePlayer){
            int startHolePlayerId = (activePlayer.id == 1?0:6);
            int startHoleOpponentId = (startHolePlayerId + 6) % 12;
            playerGain = b.eatCell(lastVisitedCell, startHoleOpponentId);
            if(Check.ennemyIsHungry(b, startHoleOpponentId)){
                System.out.println("Adversaire affamé vous jouez mais ne gagnez rien !!!");
                b.holes = cpy.holes;

            }else{
                activePlayer.granary += playerGain;
                System.out.println("Vous avez gagné " + playerGain +"\nVous possédez actuellement "+ activePlayer.granary + " grains");
            }
        }
    }

    public static boolean ennemyIsHungry(Board b, int startHoleOpponentId){

        for (int i = startHoleOpponentId; i<startHoleOpponentId+6;i++){
            if(b.holes[i].getNbSeeds() != 0){
                return false;
            }
        }
        return true;
    }

}


