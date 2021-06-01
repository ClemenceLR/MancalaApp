package ensi.fr.mancala.model;

public class Cell {
    private int nbSeeds;
    private boolean available;

    public Cell(){
        this.nbSeeds = 4;
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
}
