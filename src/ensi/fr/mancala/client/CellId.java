package ensi.fr.mancala.client;

public enum CellId {
    MINUSONE("-1"),
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    ELEVEN("11");

    public final String label;

    private CellId(String label){
        this.label = label;
    }

}
