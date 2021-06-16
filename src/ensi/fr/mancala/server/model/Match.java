package ensi.fr.mancala.server.model;

public class Match {

    public static final int nbGames = 6;
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

    public Match(){
        this.scoreJ1 = 0;
        this.scoreJ2 = 0;
        this.game = new Game();
    }

    public Match(Player player1, Player player2){
        this.game = new Game(player1,player2);
        this.scoreJ1 = this.scoreJ2 = 0;
    }

    public Match(String matchString){
        Match m = ManageFile.loadMatchFromString(matchString);
        this.scoreJ1 = m.getScoreJ1();
        this.scoreJ2 = m.getScoreJ2();
        this.game = m.getGame();
    }

    public void print(){
        System.out.println("J1 : " + this.game.activePlayer.name + "score :" + this.scoreJ1);
        System.out.println("J2 : " + this.game.passivePlayer.name + "score :" + this.scoreJ2);
        System.out.println(this.game);
    }

    public String toString(){
        String prepareData = "ME0:";
        if(this.getGame().activePlayer.id == 1){
            prepareData+= this.getScoreJ1() +":" +this.getScoreJ2() +":";
            prepareData += this.getGame().activePlayer.name + ";" + this.getGame().activePlayer.granary + ";" + this.getGame().passivePlayer.name + ";" + this.getGame().passivePlayer.granary;
        }else{
            prepareData += this.getScoreJ2() +":"+this.getScoreJ1()+":";
            prepareData += this.getGame().passivePlayer.name + ";" +this.getGame().passivePlayer.granary + ";" + this.getGame().activePlayer.name + ";" + this.getGame().activePlayer.granary;
        }
        prepareData +=";"+ this.getGame().activePlayer.id+";";
        prepareData += this.getGame().board.toString();
        return prepareData;
    }




}
