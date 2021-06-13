package ensi.fr.mancala.server.model;

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
        for(int i=0;i<sizeBoard;i++){
            this.holes[i] = new Cell(h[i]);

        }

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

    public String cellAvailable(){
        String data = "";
        for(int i=0; i<sizeBoard; i++){
            if(i != sizeBoard-1){
                data += holes[i].isAvailable() +"-";

            }else{
                data += holes[i].isAvailable();
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

    public int eatCell(int lastVisitedCell, int startHoleOpponentId){
        boolean stopEating = false;
        int gain = 0;
        do{
            if(this.holes[lastVisitedCell].isEatable()){
                gain += this.holes[lastVisitedCell].getNbSeeds();
                this.holes[lastVisitedCell].setNbSeeds(0);
            }else{
                stopEating = true;
            }
            lastVisitedCell -= 1;
        }while(lastVisitedCell >= startHoleOpponentId && !stopEating);

        return gain;
    }

    public int getTotalSeeds(){
        int totalSeeds = 0;
        for(Cell h : this.holes){
            totalSeeds += h.getNbSeeds();
        }
        return totalSeeds;
    }

}
