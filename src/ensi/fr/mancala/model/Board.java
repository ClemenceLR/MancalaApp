package ensi.fr.mancala.model;

public class Board {
    public static final int sizeBoard = 12;
    Cell[] holes = new Cell[sizeBoard];

    public Board(){
        for(Cell h:holes){
            h.setNbSeeds(4);
            h.setAvailable(true);
        }
    }

    public Board(Cell[] h){
        for(int i =0; i<sizeBoard; i++){
            holes[i] = h[i];
        }
    }

    //TODO
    public void updateBoard(){

    }

}
