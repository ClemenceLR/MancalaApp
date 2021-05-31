package ensi.fr.mancala.model;

public class Cell {
    private int nbSeeds;
    private boolean available;

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
