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
        //TODO call load
    }

    public void play(int cellClicked){

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
        return "\nJoueur actif :" + this.activePlayer.name + "\nJoueur en attente "+this.passivePlayer.name;
    }
}
