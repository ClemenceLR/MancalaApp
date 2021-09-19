package ensi.fr.mancala.server.model;

import java.util.Random;

/**
This class is the main core of the project : it is the main part of the model
Managing the game party
@author : Guillaume Hasseneyer
@author : Clémence Le Roux

* */
public class Game {

    private Board board;
    private Player activePlayer;
    private Player passivePlayer;

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
        Random r = new Random();
        int rand = r.nextInt(2)+1;

        p1.setGranary(0);
        p2.setGranary(0);

        if(rand == 1){
            this.activePlayer = p1;
            this.passivePlayer = p2;
        }else{
            this.activePlayer = p2;
            this.passivePlayer = p1;
        }
        this.board = setPlayerBoard(rand);


    }

    /**
     * Game constructor
     * @param p1 : player 1
     * @param p2 : player 2
     * @param gameToLoad : game we want to load
     */
    public Game(Player p1, Player p2, String gameToLoad){
        Game g = ManageFile.loadGameFromString(p1,p2,gameToLoad);
        this.activePlayer = g.activePlayer;
        this.passivePlayer = g.passivePlayer;
        this.board = g.board;
        Check.setCellAvailable(this.board,this.activePlayer.getId());
    }

    /**
     * Load a game using a filename
     * @param fileName : file we want to load
     */
    public Game(String fileName){
        Game g = ManageFile.loadGame(fileName);
        this.activePlayer = g.activePlayer;
        this.passivePlayer = g.passivePlayer;
        this.board = g.board;
        Check.setCellAvailable(this.board,this.activePlayer.getId());
    }

    public Game(Game game){
        this.activePlayer = game.activePlayer;
        this.passivePlayer = game.passivePlayer;
        this.board = game.board;
        Check.setCellAvailable(this.board,this.activePlayer.getId());
    }

    /**
     * Initialize the board of each players
     * @param playerId : id of the current player
     * @return the new board
     */
    public Board setPlayerBoard(int playerId){
        Board b = new Board();
        int index = (playerId == 1?6:0);
        int len;
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
     * Allows the player to play if he picked a valid cell
     * @param cellClicked : id of the cell chosen by the player
     * @return move
     */
    public int play(int cellClicked) {
        int seeds;
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
            return cellUpdated;
        }
        return -1;
    }

    /**
     * Change the current player
     */
    public void changePlayer(){
        Player temp = this.activePlayer;
        this.activePlayer = passivePlayer;
        this.passivePlayer = temp;
    }

    /**
     * Return a string that modelised the game
     * @return game
     */
    @Override
    public String toString() {
        return this.activePlayer.getName() + ";" + this.activePlayer.getGranary() + ";" + this.passivePlayer.getName() + ";" + this.passivePlayer.getGranary() + ";" + this.activePlayer.getId() + ";" + this.board;
    }

    /**
     * splitRemainingSeed
     * @return code
     */
    public int splitRemainingSeed(){
        int totalSeeds = this.board.getTotalSeeds();
        int gainSplit = totalSeeds /2;
        this.passivePlayer.addGranary(gainSplit);
        this.activePlayer.addGranary (totalSeeds - gainSplit);
        for(int i = 0; i< Board.SIZE_BOARD; i++){
            this.board.holes[i].setNbSeeds(0);
        }
        if(this.passivePlayer.getGranary() > this.activePlayer.getGranary()){
            return this.passivePlayer.getId()-1;
        }else if(this.activePlayer.getGranary() > this.passivePlayer.getGranary()){
            return this.activePlayer.getId()-1;
        }else{
            return 2;
        }

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Player getPassivePlayer() {
        return passivePlayer;
    }

    public void setPassivePlayer(Player passivePlayer) {
        this.passivePlayer = passivePlayer;
    }

    public void updatePlayers(Player activePlayer, Player passivePlayer){
        this.activePlayer.update(activePlayer);
        this.passivePlayer.update(passivePlayer);
    }

}
