import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Represents a player in the car race game.
 * Players can move using keyboard controls and are subject to track constraints.
 */
public class Player extends Thread {
    private Color color;
    private int x;
    private int y;

    private int centerX;
    private int centerY;

    private int speedX=0;
    private int speedY=0;
    private int leftKey;
    private int downKey;
    private int rightKey;
    private int upKey;




    private boolean isLeftKeyPressed;
    private boolean isDownKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;

    private boolean isCrashed;

    private boolean condition1=false;
    private boolean condition2=false;
    private boolean condition3=false;

    /**
     * Constructor to initialize player attributes.
     */
    public Player(Color color, int x, int y, int leftKey, int downKey, int rightKey, int upKey) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.leftKey = leftKey;
        this.downKey = downKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
    }

    /**
     * Handles key press events to determine movement direction.
     */
    public void handleKeyPress(int keyCode) {
        if (keyCode == leftKey) {
            isLeftKeyPressed = true;
        } else if (keyCode == downKey) {
            isDownKeyPressed = true;
        } else if (keyCode == rightKey) {
            isRightKeyPressed = true;
        } else if (keyCode == upKey) {
            isUpKeyPressed = true;
        }
    }

    /**
     * Handles key release events to stop movement in the corresponding direction.
     */
    public void handleKeyRelease(int keyCode) {
        if (keyCode == leftKey) {
            isLeftKeyPressed = false;
        } else if (keyCode == downKey) {
            isDownKeyPressed = false;
        } else if (keyCode == rightKey) {
            isRightKeyPressed = false;
        } else if (keyCode == upKey) {
            isUpKeyPressed = false;
        }
    }

    /**
     * Updates the player's speed based on key presses.
     */
    private void updateSpeed() {
        speedX = 0;
        speedY = 0;

        if (isLeftKeyPressed) {
            speedX = -2;
        }
        if (isDownKeyPressed) {
            speedY = 2;
        }
        if (isRightKeyPressed) {
            speedX = 2;
        }
        if (isUpKeyPressed) {
            speedY = -2;
        }
    }




    @Override
    public void run() {
        while (!isCrashed) {
            updateSpeed();
            // Move the player if within track boundaries
            if(roadController(x += speedX,y += speedY)==0) {
                x += speedX;
                y += speedY;
            }else{
                speedX = 0;
                speedY = 0;
                try {
                    Thread.sleep(500);// Pause if out of bounds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                move(x += speedX,y += speedY);
            }

            try {
                Thread.sleep(50);// Game loop delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Validates if the player's position is within the racetrack boundaries.
     */
    public int roadController(int x, int y) {
        int outerCenterX = 370;
        int outerCenterY = 390;
        int outerRadius = 350;


        int innerCenterX = 370;
        int innerCenterY = 390;
        int innerRadius = 200;


        boolean outsideInnerCircle = Math.pow(getCenterX() - innerCenterX, 2) + Math.pow(getCenterY() - innerCenterY, 2) > Math.pow(innerRadius+ 5*Math.sqrt(2), 2);
        boolean insideOuterCircle = Math.pow(getCenterX() - outerCenterX, 2)  + Math.pow(getCenterY() - outerCenterY, 2) < Math.pow(outerRadius - 5*Math.sqrt(2), 2);
        if(outsideInnerCircle && insideOuterCircle)
            return 0;
        else if(outsideInnerCircle)
            return -1;
        else if(insideOuterCircle)
            return 1;
        else return  0;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, 10, 10);
    }

    /**
     * Adjusts the player's position when out of bounds.
     */
    public void move(int newX,int newY){
        if(roadController(newX, newY)<0) {
            if(x<365)
                x = x+3;
            else if(x>375)
                x=x-3;
            else {
                if (y > 370)
                    y = y -3;
                else
                    y = y + 3;
            }
        }else if(roadController(newX, newY)>0) {
            if(x<365)
                x = x-3;
            else if(x>375)
                x=x+3;
            else {
                if (y > 370)
                    y = y + 3;
                else
                    y = y - 3;
            }
        }
    }

    public boolean isCondition1() {
        return condition1;
    }

    public boolean isCondition2() {
        return condition2;
    }

    public boolean isCondition3() {
        return condition3;
    }

    public void setCondition1(boolean condition1) {
        this.condition1 = condition1;
    }
    public void setCondition2(boolean condition2) {
        this.condition2 = condition2;
    }
    public void setCondition3(boolean condition3) {
        this.condition3 = condition3;
    }

    public int getCenterX() {
        centerX=x+5;
        return centerX;
    }
    public int getCenterY() {
        centerY=y+5;
        return centerY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



}
