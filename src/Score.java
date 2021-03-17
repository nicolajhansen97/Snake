public class Score {

    /***
     * Score class
     */

    private int score = 0;
    private int highScore = 0;

    /***
     * Is keeping track of the score
     */
    public Score(){

    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void resetScore(){
        if (score>highScore)
        {
            highScore = score;
        }
        score = 0;
    }
}
