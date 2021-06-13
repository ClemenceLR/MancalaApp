package ensi.fr.mancala.server.model;

import ensi.fr.mancala.server.ClientInterface;

/**
This class is the main core of the project : it is the main part of the model
Managing the game party
@author : Guillaume Hasseneyer
@author : Clémence Le Roux

* */
public class Game {
    public Board board;
    public Board previousBoard;
    public Player activePlayer;
    public Player passivePlayer;

    /**
     * Construct a new game by initializing the board and players
     * */
    public Game(){
        this(new Player("p1"),new Player("p2"));

    }

    /**
     * Construct a new game with given players
     * @param p1 : player one
     * @param p2 : player two
     */
    public Game(Player p1, Player p2){
        int rand = (int)(Math.random()*2)+1;
        if(rand == 1){
            this.activePlayer = p1;
            this.passivePlayer = p2;
        }else{
            this.activePlayer = p2;
            this.passivePlayer = p1;
        }
        this.board = setPlayerBoard(rand);
        this.previousBoard = this.board;
    }

    /**
     * Load a game using a filename
     * @param fileName
     */
    public Game(String fileName){
        Game g = ManageFile.loadGame(fileName);
        this.activePlayer = g.activePlayer;
        this.passivePlayer = g.passivePlayer;
        this.board = g.board;
        this.previousBoard = g.board;
    }

    /**
     * Initialize the board of each players
     * @param playerid
     * @return the new board
     */
    public Board setPlayerBoard(int playerid){
        Board b = new Board();
        int index = (playerid == 1?6:0);
        int len =0;
        if(index == 6){
            len = b.holes.length;
        }else{
            len = b.holes.length/2;
        }
        for(int i=index; i<len; i++){
            b.holes[i].setAvailable(false);
        }
        return b;
    }

    /**
     * Play the game
     */
    public void playGame(){
        int termine = -1;
        int nbCellsAvailable;
        int cpt = 0;
        int input;
        int lastVisitedCell;
        boolean checkEatable = false;
        do{
            lastVisitedCell = -1;
            this.printBoard();
            System.out.println("turn " + cpt + " - Player " + this.activePlayer.id);

            do{
                input = activePlayer.getCellClicked();
                System.out.println("Cell " + input);
                lastVisitedCell = play(input);

            }
            while(lastVisitedCell == -1);

            Check.checkEatableCells(lastVisitedCell,activePlayer, this.board);

            this.changePlayer();
            Check.setCellAvailable(this.board,this.activePlayer.id);
            termine = Check.isEndedGame(this);
            cpt++;
        }
        while(termine == -1);

        //switch (termine)
    }

    /**
     * Allows the player to play
     * @param cellClicked
     * @return
     */
    public int play(int cellClicked) {
        int seeds = 0;
        int cellUpdated;
        if (board.holes[cellClicked].isAvailable()) { // Si la case est jouable
            seeds = board.holes[cellClicked].getNbSeeds();
            board.holes[cellClicked].setNbSeeds(0);
            cellUpdated = cellClicked;
            do {
                cellUpdated = (cellUpdated +1)%12;
                if(cellUpdated == cellClicked){
                    cellUpdated = (cellUpdated +1)%12;
                }
                board.holes[cellUpdated].setNbSeeds(board.holes[cellUpdated].getNbSeeds() + 1);
                seeds --;
            }
            while(seeds != 0);
            for (int i = cellClicked + 1; i <= cellClicked + seeds; i++) {
                cellUpdated = i % 12;
                board.holes[i].setNbSeeds(board.holes[cellUpdated].getNbSeeds() + 1);
            }
            return cellUpdated; //TODO a vérifier car ne marche peut être pas
        }
        else{
            System.out.println("This cell is not playable");
        }
        return -1;
        //checkSeedsEarned();
    }

    /**
     * Change the current player
     */
    public void changePlayer(){
        Player temp = this.activePlayer;
        this.activePlayer = passivePlayer;
        this.passivePlayer = temp;
    }

    @Override
    public String toString(){
        System.out.println("Plateau : ");
        this.board.printBoard();
        return "\nJoueur actif :" + this.activePlayer.name + "\nJoueur en attente "+this.passivePlayer.name;
    }

    public void printBoard(){
        this.board.printBoard();
        System.out.println();
    }

}
