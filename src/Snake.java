import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/***
 * Snake class
 */
public class Snake {

    List<Grid> snakeList = new ArrayList<>();
    private int speed = 4;
    private Image[] images = {new Image(Main.class.getResourceAsStream("snakeBody.png")),
            new Image(Main.class.getResourceAsStream("snakeHead.png")),
            new Image(Main.class.getResourceAsStream("snakeHeadR.png")),
            new Image(Main.class.getResourceAsStream("snakeHeadL.png")),
            new Image(Main.class.getResourceAsStream("snakeHeadD.png")),};

    /***
     * Snake constructor
     * @param x coordinate
     * @param y coordinate
     */
    public Snake(int x,int y)
    {   //creates three parts for the snake
        snakeList.add(new Grid(x/2,y/2));
        snakeList.add(new Grid(x/2,y/2));
        snakeList.add(new Grid(x/2,y/2));
    }

    /***
     * @return snake speed
     */
    public int getSpeed(){
        return speed;
    }

    /***
     * @param speed sets speed
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }

    /***
     * makes snake parts follow each other
     */
    public void snakeFollow(){
        for (int i = snakeList.size()-1; i >0 ; i--) {
            snakeList.get(i).cols = snakeList.get(i-1).cols;
            snakeList.get(i).rows = snakeList.get(i-1).rows;
        }
    }

    /***
     * Draw snakes
     * @param gc Graphic thing
     * @param cornerSize //grid calculator
     * @param direction // direction for snake
     * @param foodBig // is snake big
     */
    public void drawSnake(GraphicsContext gc, int cornerSize, Main.Dir direction,boolean foodBig){
        for (int i = 0; i <snakeList.size(); i++) {
            if (i==0){
                if (!foodBig) {//draws normal snake
                    switch (direction) {
                        case Up:
                            gc.drawImage(images[1], snakeList.get(i).cols * cornerSize, snakeList.get(i).rows * cornerSize);
                            break;
                        case Down:
                            gc.drawImage(images[4], snakeList.get(i).cols * cornerSize, snakeList.get(i).rows * cornerSize);
                            break;
                        case Right:
                            gc.drawImage(images[2], snakeList.get(i).cols * cornerSize, snakeList.get(i).rows * cornerSize);
                            break;
                        case Left:
                            gc.drawImage(images[3], snakeList.get(i).cols * cornerSize, snakeList.get(i).rows * cornerSize);
                            break;
                    }
                }else {switch (direction) { //draws snake with big head
                    case Up:
                        gc.drawImage(images[1], (snakeList.get(i).cols * cornerSize)-12, (snakeList.get(i).rows * cornerSize)-12,48,48);
                        break;
                    case Down:
                        gc.drawImage(images[4], (snakeList.get(i).cols * cornerSize)-12, (snakeList.get(i).rows * cornerSize)-12,48,48);
                        break;
                    case Right:
                        gc.drawImage(images[2], (snakeList.get(i).cols * cornerSize)-12, (snakeList.get(i).rows * cornerSize)-12,48,48);
                        break;
                    case Left:
                        gc.drawImage(images[3], (snakeList.get(i).cols * cornerSize)-12, (snakeList.get(i).rows * cornerSize)-12,48,48);
                        break;
                }}
            }else { //draws body parts snake
                gc.drawImage(images[0], snakeList.get(i).cols * cornerSize, snakeList.get(i).rows * cornerSize);
            }
        }
    }
}
