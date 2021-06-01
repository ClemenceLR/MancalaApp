package ensi.fr.mancala.model;

public class Game {
    public Board board;
    public Player activePlayer;
    public Player passivePlayer;

    public Game(){
        this.board = new Board();
        this.activePlayer = new Player("P1");
        this.passivePlayer = new Player("P2");
        //TODO voir init des noms des joueurs
    }

    public Game(String fileName){
        Game g =ManageFile.loadGame(fileName);
        this.activePlayer = g.activePlayer;
        this.passivePlayer = g.passivePlayer;
        this.board = g.board;
    }

    public void play(int cellClicked){
        int seeds = 0;
        if(board.holes[cellClicked].isAvailable()){ // Si la case est jouable
            seeds = board.holes[cellClicked].getNbSeeds();
            board.holes[cellClicked].setNbSeeds(0);
            for(int i =cellClicked+1; i<=cellClicked+seeds;i++){
                board.holes[i].setNbSeeds(board.holes[i].getNbSeeds()+1);
            }
        }
        //checkSeedsEarned();
    }

    public void changePlayer(){
        Player temp = this.activePlayer;
        this.activePlayer = passivePlayer;
        this.passivePlayer = temp;
    }

    @Override
    public String toString(){
        System.out.println("Plateau : ");
        this.board.printBoard();
        return "\nJoueur actif : " + this.activePlayer.name + "\nJoueur en attente : "+this.passivePlayer.name;
    }

    public void printBoard(){
        this.board.printBoard();
    }
}
