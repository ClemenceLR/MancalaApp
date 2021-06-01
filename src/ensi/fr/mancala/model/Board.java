package ensi.fr.mancala.model;

public class Board {
    public static final int sizeBoard = 12;
    public Cell[] holes = new Cell[sizeBoard];

    public Board(){
        int i;
        for(i=0;i<sizeBoard;i++){
           holes[i] = new Cell();
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
        holes = h;
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
