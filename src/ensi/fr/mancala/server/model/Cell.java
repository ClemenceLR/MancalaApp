package ensi.fr.mancala.server.model;

/**
 * Class in charge of cells
 *  @author Guillaume Haseneyer
 *  @author Clemence Le Roux
 *
 */
public class Cell {
    private int nbSeeds;
    private boolean available;

    /**
     * Cell constructor
     */
    public Cell(){
        this.setNbSeeds(4);
        this.available = true;
    }

    /**
     * Cell constructor with the number of seeds on it in parameter
     * @param nb : number of seeds of the cell
     */
    public Cell(int nb){
        this.nbSeeds = nb;
        this.available = this.nbSeeds != 0;
    }

    /**
     * Cell constructor
     * @param c : cell to copy
     */
    public Cell(Cell c){
        this.nbSeeds = c.getNbSeeds();
        this.available = c.isAvailable();
    }

    /**
     * set nb seeds
     * @param s : nb seeds to set
     */
    public void setNbSeeds(int s) {
        this.nbSeeds = s;

    }

    /**
     * get nb seeds
     * @return nb seeds
     */
    public int getNbSeeds() {
        return nbSeeds;
    }

    /**
     * get if the cell is available
     * @return available
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * set if the cell is available
     * @param available : boolean value
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Check if the seeds of one cell can be eaten
     * @return boolean
     */
    public boolean isEatable(){
        return this.getNbSeeds() == 2 || this.getNbSeeds() == 3;
    }
}
