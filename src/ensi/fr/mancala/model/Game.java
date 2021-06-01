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
        //TODO premier joueur random
        //TODO enlever les cases non jouables par le premier joueur
    }

    public Game(String fileName){
        //TODO call load
    }

    public void playGame(){
        boolean termine = false;
        int nbCellsAvailable;
        int cpt = 0;
        int random;
        do{
            this.printBoard();
            random = (int)(Math.random() * 12);
            System.out.print(random + "--------");
            play(random);
            this.changePlayer();
            Check.setCellAvailable(this.board,this.activePlayer.id);
            termine = Check.isEndedGame(this);
            cpt++;
        }
        while(!termine && cpt < 10);
    }

    public void play(int cellClicked) {
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
        return "\nJoueur actif :" + this.activePlayer.name + "\nJoueur en attente "+this.passivePlayer.name;
    }

    public void printBoard(){
        this.board.printBoard();
        System.out.println();
    }
}
