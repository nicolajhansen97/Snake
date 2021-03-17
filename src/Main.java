import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/***
 * @author Niels,Rasmus Hedemann,Nicolaj Hansen
 * @version 0.6
 * @since 11/03/2021
 */
public class Main extends Application {
    private final int width = 20;
    private final int height = 20;
    private final int cornerSize = 25;
    private Snake snake;
    private Dir direction = Dir.Left;
    private Food food = new Food();
    private boolean gameOver = false;
    private boolean done = false;
    private boolean done1 = false;
    private int foodEaten = 0;
    private RotateTransition rot,rot2;
    private boolean bool = false;

    enum Dir{
        Left,Right,Up,Down;
    }


    /***
     * Application start
     * @param primaryStage Stage for showing application
     * @throws Exception Stops errors
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Canvas c = new Canvas(width*cornerSize,height*cornerSize);
        GraphicsContext gc = c.getGraphicsContext2D();
        rot = new RotateTransition(Duration.seconds(1),c);
        rot2 = new RotateTransition(Duration.seconds(1),c);
        //random boolean decider
        if (bool){
            rot.setToAngle(90);
        }
        else {
            rot.setToAngle(-90);
        }
        rot2.setToAngle(0);
        pane.getChildren().add(c);

        //Update every tick and decides speed of program
        new AnimationTimer(){
            long lastTick;

            @Override
            public void handle(long now){
                if (lastTick==0){
                    lastTick = now;
                    draw(gc);
                    return;
                }
                if (now - lastTick > 1000000000 / snake.getSpeed()){
                    lastTick = now;
                    draw(gc);
                }
            }
        }.start();
        //declaring scene with a grid calculation
        Scene scene = new Scene(pane, width*cornerSize, height*cornerSize);

        //Controls
        scene.addEventFilter(KeyEvent.KEY_PRESSED,key ->{
            if (key.getCode() == KeyCode.W) {
                direction = Dir.Up;
            }
            if (key.getCode() == KeyCode.S) {
                direction = Dir.Down;
            }
            if (key.getCode() == KeyCode.D) {
                direction = Dir.Right;
            }
            if (key.getCode() == KeyCode.A) {
                direction = Dir.Left;
            }
        });

        //adds snake to program
        snake = new Snake(width,height);
        //timer for the food
        FoodTimer();

        primaryStage.setTitle("snake test");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    int tempSpeed = 0;
    boolean tempB = false;

    /***
     * Graphics method get called in every tick and then draws all it needs to
     * also contains a lot logic for objects
     * @param gc GraphicsContext class used to draw.
     */
    public void draw(GraphicsContext gc){
        //gameOver checker
        if (gameOver){
            gc.setFill(Color.RED);
            gc.setFont(new Font("",50));
            gc.fillText("Game over",100,250);
            done1 = true;
            return;
        }

        //Calls upon snake method
        snake.snakeFollow();

        //make snake move
        switch (direction) {
            case Up:
                snake.snakeList.get(0).rows--;
                if (snake.snakeList.get(0).rows <0){gameOver = true;} //if head goes outside grid it dies
                break;
            case Down:
                snake.snakeList.get(0).rows++;
                if (snake.snakeList.get(0).rows >height){gameOver = true;}
                break;
            case Left:
                snake.snakeList.get(0).cols--;
                if (snake.snakeList.get(0).cols <0){gameOver = true;}
                break;
            case Right:
                snake.snakeList.get(0).cols++;
                if (snake.snakeList.get(0).cols >height){gameOver = true;}
                break;
        }

        //snake eat
        if (!done) {
            //giant if statement that checks where food and where the snakes head is
            //also used for when the head gets big where we check around the food to
            if (food.getFoodX() == snake.snakeList.get(0).cols && food.getFoodY() == snake.snakeList.get(0).rows ||food.getFoodBig()&&
                    food.getFoodX()+1 == snake.snakeList.get(0).cols && food.getFoodY()+1 == snake.snakeList.get(0).rows||food.getFoodBig()&&
                    food.getFoodX()-1 == snake.snakeList.get(0).cols && food.getFoodY()-1 == snake.snakeList.get(0).rows||food.getFoodBig()&&
                    food.getFoodX()-1 == snake.snakeList.get(0).cols && food.getFoodY()+1 == snake.snakeList.get(0).rows||food.getFoodBig()&&
                    food.getFoodX()+1 == snake.snakeList.get(0).cols && food.getFoodY()-1 == snake.snakeList.get(0).rows) {
                //makes snake longer
                snake.snakeList.add(new Grid(-1, -1));
                snake.setSpeed(snake.getSpeed() + 1); //makes snake faster
                count = 0;
                randN = rand.nextInt(6)+5;
                tempSpeed = snake.getSpeed(); //puts current speed into a temp to make the snake normal
                //again when it has eaten speed fruit

                //checks what fruit the snake ate
                if (food.getNum()<=4){
                    food.setFoodBig(false);
                    snake.setSpeed(tempSpeed);
                    if (tempB){
                        snake.setSpeed(snake.getSpeed()-10);
                        tempB = false;
                    }
                    //big food
                }else if (food.getNum()==5){
                    food.setFoodBig(true);
                    snake.setSpeed(tempSpeed);
                    if (tempB){
                        snake.setSpeed(snake.getSpeed()-10);
                        tempB = false;
                    }
                    //speed food
                }else {
                    food.setFoodBig(false);
                    if (!tempB) {
                        snake.setSpeed(snake.getSpeed() + 10);
                    }
                    tempB = true;
                }
                //create new food
                food.newFood(width, height, snake);
                //makes score bigger
                foodEaten++;
            }
            //if time runs out for the food it will then generate new food
        }else{
            food.newFood(width,height,snake);
            done = false;
            randN = rand.nextInt(6)+5;
        }

        //rotate screen every 10 food
        if (foodEaten%10==0&&foodEaten!=0) {
            bool = rand.nextBoolean();
            rot.play();
        }
        else {
            rot2.play();
        }

        //self destroys the snake if it hits it self
        for (int i = 1; i <snake.snakeList.size() ; i++) {
            if (snake.snakeList.get(0).cols == snake.snakeList.get(i).cols && snake.snakeList.get(0).rows == snake.snakeList.get(i).rows) {
                gameOver = true;
                break;
            }
        }

        //fill background in one color
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,width*cornerSize,height*cornerSize);

        //puts the score on the screen
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("",30));
        gc.fillText("score: "+foodEaten,10,30);

        //draw the Food
        food.drawFood(gc, food.getFoodX() * cornerSize, food.getFoodY() * cornerSize);

        //draw the snake
        snake.drawSnake(gc,cornerSize,direction,food.getFoodBig());
    }

    private int count = 0;
    private Random rand = new Random();
    private int randN;

    /***
     * Starts a thread for timer
     * so it can run at the same time as
     * the program
     */
    public void FoodTimer() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!done1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    if (count>randN){
                        done = true;
                        count = 0;
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}

