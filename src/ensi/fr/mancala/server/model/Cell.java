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
        this.available = this.nbSeeds != 0;
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
        return this.getNbSeeds() == 2 || this.getNbSeeds() == 3;
    }
}
