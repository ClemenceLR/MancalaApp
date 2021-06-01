package ensi.fr.mancala.model;

public class Board {
    public static final int sizeBoard = 12;
    Cell[] holes = new Cell[sizeBoard];

    public Board(){
        for(Cell h:holes){
           h = new Cell();
        }
    }

    public Board(String b){
        String[] board = b.split("-");
        for(int i =0; i<sizeBoard; i++){
            int value = Integer.parseInt(board[i]);
            holes[i] = new Cell(value);
        }
    }

    public Board(Cell[] h){
        for(int i =0; i<sizeBoard; i++){
            holes[i] = h[i];
        }
    }
    public void printBoard(){
        for(Cell h:holes){
            System.out.print(" "+h.getNbSeeds() + " ");
        }
    }
    //TODO
    public void updateBoard(){

    }

}
