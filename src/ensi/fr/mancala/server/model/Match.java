package ensi.fr.mancala.server.model;

public class Match {

    public static final int nbGames = 6;
    private Game game;
    private int[] scores;
    private int matchNum;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public int getScore(int playerId) {
        return scores[playerId];
    }

    public void setScore(int playerId, int score) {
        this.scores[playerId] = score;
    }

    public void incScore(int playerId){
        this.scores[playerId]++;
    }

    public void incMatchNum(){
        this.matchNum++;
    }

    public Match(){
        this.scores = new int[2];
        scores[0] = scores[1] = 0;
        this.matchNum =1;
        this.game = new Game();
    }

    public Match(Player player1, Player player2){
        this.game = new Game(player1,player2);
        this.scores = new int[2];
        this.matchNum =1;
        scores[0] = scores[1] = 0;
    }


    public void print(){
        System.out.println("J1 : " + this.game.activePlayer.name + "score :" + this.scores[0]);
        System.out.println("J2 : " + this.game.passivePlayer.name + "score :" + this.scores[1]);
        System.out.println(this.game);
    }

    public String toString(){
        String prepareData = "ME0:"+this.getMatchNum() +":";
        if(this.getGame().activePlayer.id == 1){
            prepareData+= this.getScore(0) +":" +this.getScore(1) +":";
            prepareData += this.getGame().activePlayer.name + ";" + this.getGame().activePlayer.granary + ";" + this.getGame().passivePlayer.name + ";" + this.getGame().passivePlayer.granary;
        }else{
            prepareData += this.getScore(1) +":"+this.getScore(0)+":";
            prepareData += this.getGame().passivePlayer.name + ";" +this.getGame().passivePlayer.granary + ";" + this.getGame().activePlayer.name + ";" + this.getGame().activePlayer.granary;
        }
        prepareData +=";"+ this.getGame().activePlayer.id+";";
        prepareData += this.getGame().board.toString();
        return prepareData;
    }


    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }
}
