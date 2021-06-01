package ensi.fr.mancala.model;

public class Board {
    public static final int sizeBoard = 12;
    public Cell[] holes = new Cell[sizeBoard];

    public Board(){
        //TODO modifier l'initialisation pour pour que les cases qui ne sont pas au premier joueur soient non available
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
    @Override
    public String toString(){
       String data = "";
       for(int i=0; i<sizeBoard; i++){
           if(i != sizeBoard-1){
               data += holes[i].getNbSeeds() +"-";

           }else{
               data += holes[i].getNbSeeds();
           }
       }
       return data;
    }
    //TODO
    public void updateBoard(){

    }

    public void printAvailable(){
        for(Cell c : holes){
            System.out.print(c.isAvailable() + " ");
        }
    }


}
