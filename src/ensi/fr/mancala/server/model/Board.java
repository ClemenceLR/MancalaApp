package ensi.fr.mancala.server.model;
/**
 * Class containing the Board main functions
 * @author Guillaume Haseneyer
 * @author Clemence Le Roux
* */
public class Board {
    public static final int SIZE_BOARD = 12;
    public Cell[] holes = new Cell[SIZE_BOARD];

    /**
     * Board constructor, initialize a board with new cells containing 4 seeds
     */
    public Board() {
        int i;
        for (i = 0; i < SIZE_BOARD; i++) {
            holes[i] = new Cell();
        }
    }

    /**
     * Board constructor, initialize a board with the given string containing the number of seeds of each cells
     * @param b : string representing the board
     */
    public Board(String b) {
        String[] board = b.split("-");
        for (int i = 0; i < SIZE_BOARD; i++) {
            int value = Integer.parseInt(board[i]);
            holes[i] = new Cell(value);
        }
    }

    /**
     * Board constructor, initialize a board with the given cell table
     * @param h : cell table to copy
     */
    public Board(Cell[] h) {
        for (int i = 0; i < SIZE_BOARD; i++) {
            this.holes[i] = new Cell(h[i]);

        }

    }

    /**
     * Get a copy of the holes set of the board
     * @return h
     */
    public Cell[] getHoles(){
        Cell[]h = new Cell[SIZE_BOARD];
        System.arraycopy(this.holes, 0, h, 0, SIZE_BOARD); //TODO CHECK IF OK
        return h;
    }

    /**
     * Print the board
     */
    public void printBoard() {
        for (Cell h : holes) {
            System.out.print(" " + h.getNbSeeds() + " ");
        }
    }

    /**
     * Return the board in the string format in order to save it
     * @return data : string representing the board with int values
     */
    @Override
    public String toString() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < SIZE_BOARD; i++) {
            if (i != SIZE_BOARD - 1) {
                data.append(holes[i].getNbSeeds()).append("-");

            } else {
                data.append(holes[i].getNbSeeds());
            }
        }
        return data.toString();
    }

    /**
     * Return the boolean data of the board : each cell is represented by a boolean value
     * @return data : string representing the board with boolean values
     */
    public String cellAvailable() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < SIZE_BOARD; i++) {
            if (i != SIZE_BOARD - 1) {
                data.append(holes[i].isAvailable()).append("-");

            } else {
                data.append(holes[i].isAvailable());
            }
        }
        return data.toString();
    }

    /**
     * Return the board modelised by a string of false : objective forbid the player to play
     * @return string
     */
    public String forbidPlay() {
        return "false-false-false-false-false-false-false-false-false-false-false-false";
    }

    /**
     * Check and eat all the cells that the player is allowed to eat
     * @param lastVisitedCell : id of the last visited cell
     * @param startHoleOpponentId : starting hole location on the opponent side
     * @return gain
     */
    public int eatCell(int lastVisitedCell, int startHoleOpponentId) {
        boolean stopEating = false;
        int gain = 0;
        do {
            if (this.holes[lastVisitedCell].isEatable()) {
                gain += this.holes[lastVisitedCell].getNbSeeds();
                this.holes[lastVisitedCell].setNbSeeds(0);
            } else {
                stopEating = true;
            }
            lastVisitedCell -= 1;
        } while (lastVisitedCell >= startHoleOpponentId && !stopEating);

        return gain;
    }

    /**
     * Get the total of the seeds present on the board
     * @return totalSeeds : int representing the total of seeds on the board
     */
    public int getTotalSeeds() {
        int totalSeeds = 0;
        for (Cell h : this.holes) {
            totalSeeds += h.getNbSeeds();
        }
        return totalSeeds;
    }

}
