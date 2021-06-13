package ensi.fr.mancala.server.model;

public class Cell {
    private int nbSeeds;
    private boolean available;

    public Cell(){
        this.setNbSeeds(4);
        this.available = true;
    }

    public Cell(int nb){
        this.nbSeeds = nb;
        if(this.nbSeeds != 0) {
            this.available = true;
        }else{
            this.available = false;
        }
    }

    public Cell(Cell c){
        this.nbSeeds = c.getNbSeeds();
        this.available = c.isAvailable();
    }
    public void setNbSeeds(int s) {
        this.nbSeeds = s;

    }

    public int getNbSeeds() {
        return nbSeeds;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isEatable(){
        if(this.getNbSeeds() == 2 || this.getNbSeeds() == 3){
            return true;
        }
        return false;
    }
}
