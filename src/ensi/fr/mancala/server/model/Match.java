package ensi.fr.mancala.server.model;

public class Match {

    private Game game;
    private int scoreJ1;
    private int scoreJ2;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScoreJ1() {
        return scoreJ1;
    }

    public void setScoreJ1(int scoreJ1) {
        this.scoreJ1 = scoreJ1;
    }

    public int getScoreJ2() {
        return scoreJ2;
    }

    public void setScoreJ2(int scoreJ2) {
        this.scoreJ2 = scoreJ2;
    }

    public Match(String name1, String name2){
        this.game = new Game(new Player(name1),new Player(name2));
        this.scoreJ1 = this.scoreJ2 = 0;
    }

    public Match(String matchString){

    }




}
