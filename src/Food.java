import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

/***
 *
 */
public class Food {

    private int foodX = 0;
    private int foodY = 0;
    private Random rand = new Random();
    private boolean foodBig = false;
    boolean foodNormal = true;
    boolean isFoodBig = false;
    private Image[] images = { //Image array for the food
            new Image((Main.class.getResourceAsStream("fruit.png"))),
            new Image((Main.class.getResourceAsStream("fruitBig.png"))),
            new Image((Main.class.getResourceAsStream("fruitSpeed.png")))};
    private int num;

    /***
     * @return num used to see what fruit has spawned
     */
    public int getNum(){return num;}

    /***
     * @return foodX
     */
    public int getFoodX() {
        return foodX;
    }

    /***
     * @return foodY
     */
    public int getFoodY() {
        return foodY;
    }

    /***
     * @return boolean for if food is big type
     */
    public boolean getFoodBig(){return foodBig;}

    /***
     * @param foodBig1 sets boolean
     */
    public void setFoodBig(boolean foodBig1){this.foodBig = foodBig1;}

    /***
     * Generates new food
     * @param width width of grid
     * @param height height of grid
     * @param snake snake object
     */
    public void newFood(int width,int height,Snake snake){
        num = rand.nextInt(6)+1; // random num
        switch (num){ // switch case for what food to spawn
            case 1:
            case 2:
            case 3:
            case 4:
                foodNormal = true;
                isFoodBig = false;
                break;
            case 5:
                foodNormal = false;
                isFoodBig = true;
                break;
            case 6:
                foodNormal = false;
                isFoodBig = false;
                break;
        }
        start: while (true) // sets food x and y randomly
        {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (Grid c : snake.snakeList){
                if (c.cols==foodX&&c.rows==foodY) {
                    continue start;
                }
            }
            break;
        }
    }

    /***
     * Draws food
     * @param gc graphic context
     * @param x width
     * @param y height
     */
    public void drawFood(GraphicsContext gc, int x, int y){
        if (foodNormal) {
            gc.drawImage(images[0], x, y);
        }else if (isFoodBig){
            gc.drawImage(images[1], x, y);
        }else {
            gc.drawImage(images[2], x, y);
        }
    }

}
