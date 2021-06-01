package ensi.fr.mancala.model;

public class Game {
    public Board board;
    public Player activePlayer;
    public Player passivePlayer;

    public Game(){
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        this.activePlayer = new Player("P1");
        this.passivePlayer = new Player("P2");

        //TODO voir init des noms des joueurs
        //TODO premier joueur random
        //TODO enlever les cases non jouables par le premier joueur
        int rand = (int)(Math.random()*2)+1;
        if(rand == 1){
            this.activePlayer = p1;
            this.passivePlayer = p2;
        }else{
            this.activePlayer = p2;
            this.passivePlayer = p1;
        }
        this.board = setPlayerBoard(rand);

    }
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

    public Game(String fileName){
        //TODO call load
        Game g = ManageFile.loadGame(fileName);
        this.activePlayer = g.activePlayer;
        this.passivePlayer = g.passivePlayer;
        this.board = g.board;
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
