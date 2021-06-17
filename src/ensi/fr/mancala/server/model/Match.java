package ensi.fr.mancala.server.model;
/**
 * Managing Match
 * @author Guillaume Haseneyer
 * @author Clemence Le Roux
 **/
public class Match {

    public static final int NB_GAMES = 6;
    private Game game;
    private int[] scores;
    private int matchNum;

    /**
     * Match constructor
     */
    public Match(){
        this.scores = new int[2];
        this.matchNum =1;
        this.game = new Game();
    }

    /**
     * Match constructor
     * @param player1 : player 1
     * @param player2 : player 2
     */
    public Match(Player player1, Player player2){
        this.game = new Game(player1,player2);
        this.scores = new int[2];
        this.matchNum =1;
    }

    /**
     * get the game of the match
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * set the game of the match
     * @param game : game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * get the score of the match
     * @param playerId : player id
     * @return int
     */
    public int getScore(int playerId) {
        return scores[playerId];
    }

    /**
     *
     * set the score of the player
     *
     * @param playerId : int player id
     * @param score : int score
     */
    public void setScore(int playerId, int score) {
        this.scores[playerId] = score;
    }

    /**
     * increase the score of a player
     * @param playerId : int
     */
    public void incScore(int playerId){
        this.scores[playerId]++;
    }

    /**
     * increase the score of a player
     */
    public void incMatchNum(){
        this.matchNum++;
    }

    /**
     * Return a string to represent the match
     * @return string
     */
    public String toString(){
        String prepareData = "ME0:"+this.getMatchNum() +":";
        if(this.getGame().activePlayer.getId() == 1){
            prepareData+= this.getScore(0) +":" +this.getScore(1) +":";
            prepareData += this.getGame().activePlayer.getName() + ";" + this.getGame().activePlayer.getGranary() + ";" + this.getGame().passivePlayer.getName() + ";" + this.getGame().passivePlayer.getGranary();
        }else{
            prepareData += this.getScore(1) +":"+this.getScore(0)+":";
            prepareData += this.getGame().passivePlayer.getName() + ";" +this.getGame().passivePlayer.getGranary() + ";" + this.getGame().activePlayer.getName() + ";" + this.getGame().activePlayer.getGranary();
        }
        prepareData +=";"+ this.getGame().activePlayer.getId()+";";
        prepareData += this.getGame().board.toString();
        return prepareData;
    }

    /**
     * Get the num of the match
     * @return int
     */
    public int getMatchNum() {
        return matchNum;
    }

    /**
     * Set the num of the match
     * @param matchNum : int number of the match
     */
    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }
}
