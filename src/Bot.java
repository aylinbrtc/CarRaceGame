import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a bot in the car race game.
 * Bots move randomly within the allowed track boundaries.
 */

public class Bot extends Thread {
    private int speed; // Bot's movement speed
    private int x, y; // Bot's current position
    private boolean condition1 = false, condition2 = false, condition3 = false; // Conditions for game logic
    private boolean GO = false; // Flag to stop bot movement
    private int centerX, centerY; // Center coordinates for collision checks
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();


    // Constructor to initialize bot's starting position and speed
    public Bot(int startLineX, int startLineY, int botSpeed) {
        this.speed=botSpeed;
        this.x=startLineX;
        this.y=startLineY;

    }

    @Override
    public void run() {
        while (!GO) {
            lock1.lock();
            try {

                moveRandomDirection();
            } finally {
                lock1.unlock();
            }

            try {
                Thread.sleep(1000 / speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Determines the bot's random movement direction and updates its position.
     */
    private void moveRandomDirection() {
        Random random = new Random();
        int direction = random.nextInt(5); // Random direction: 0-4

        int newX = x;
        int newY = y;

        // Determine movement based on current position and random direction
        if (x > 320 && x < 420 && y <= 190 && y >= 10) {
            switch (direction) {
                case 0:
                    newX += 1;
                case 3:
                    newX += 1;
                case 1:
                    newY += 1;
                case 2:
                    newY += 1;
                case 4:
                    newY -= 1;
            }
        } else if (x > 500 && x <= 720 && y >= 340 && y <= 420) {
            switch (direction) {
                case 0:
                    newX += 1;
                case 3:
                    newX -= 1;
                case 1:
                    newX -= 1;
                case 2:
                    newY += 1;
                case 4:
                    newY += 1;
            }
        } else if (x > 320 && x < 420 && y <= 740 && y >= 530) {
            switch (direction) {
                case 0:
                    newX += 1;
                case 3:
                    newX -= 1;
                case 1:
                    newX -= 1;
                case 2:
                    newY -= 1;
                case 4:
                    newY -= 1;
            }
        } else if (x < 370 && y < 370) {
            switch (direction) {
                case 0:
                    newX += 1;
                case 3:
                    newX += 1;
                case 1:
                    newX += 1;
                case 2:
                    newY -= 1;
                case 4:
                    newY -= 1;
            }
        } else if (x < 370 && y > 370) {
            switch (direction) {
                case 0:
                    newX += 1;
                case 3:
                    newX -= 1;
                case 1:
                    newX -= 1;
                case 2:
                    newY -= 1;
                case 4:
                    newY -= 1;
            }
        } else if (x > 370 && y > 370) {
            switch (direction) {
                case 0:
                    newX += 1;
                case 3:
                    newX -= 1;
                case 1:
                    newX -= 1;
                case 2:
                    newY += 1;
                case 4:
                    newY += 1;
            }
        } else if (x > 370 && y < 370) {
            switch (direction) {
                case 0:
                    newX += 1;
                case 3:
                    newX += 1;
                case 1:
                    newX -= 1;
                case 2:
                    newY += 1;
                case 4:
                    newY += 1;
            }
        } else{
            switch (direction) {
                case 0:
                    newX += 1;
                case 3:
                    newY -= 1;
                case 1:
                    newX -= 1;
                case 2:
                    newX += 1;
                case 4:
                    newY -= 1;
            }
        }
        lock2.lock();
        try {
            if (roadController(newX, newY) == 0) {
                x = newX;
                y = newY;
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
                move(newX, newY);
            }
        } finally {
            lock2.unlock();
        }

    }

    /**
     * Validates if the bot's position is within the racetrack boundaries.
     */
    public int roadController(int x, int y) {
        int outerCenterX = 370;
        int outerCenterY = 390;
        int outerRadius = 350;

        int innerCenterX = 370;
        int innerCenterY = 390;
        int innerRadius = 200;


        boolean outsideInnerCircle = Math.pow(getCenterX()- innerCenterX, 2) + Math.pow(getCenterY() - innerCenterY, 2) > Math.pow(innerRadius+ 5*Math.sqrt(2), 2);
        boolean insideOuterCircle = Math.pow(getCenterX() - outerCenterX, 2)  + Math.pow(getCenterY() - outerCenterY, 2) < Math.pow(outerRadius - 5*Math.sqrt(2), 2);
        if(outsideInnerCircle && insideOuterCircle)
            return 0;
        else if(outsideInnerCircle)
            return -1;
        else if(insideOuterCircle)
            return 1;
        else return  0;
    }
    public int getCenterX() {
        centerX=x+5;
        return centerX;
    }
    public int getCenterY() {
        centerY=y+5;
        return centerY;
    }

    /**
     * Draws the bot on the game window.
     */
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        if(roadController(x,y)==0)
            g.fillRect(x, y, 10, 10);
        else {
            move(x, y);
            g.fillRect(x, y, 10, 10);
        }

    }

    /**
     * Adjusts bot's position when out of bounds.
     */
    public void move(int newX,int newY) {
        if (roadController(newX, newY) < 0) {
            if (x < 365)
                x = x + 3;
            else if (x > 375)
                x = x - 3;
            else {
                if (y > 370)
                    y = y - 3;
                else
                    y = y + 3;
            }
        } else if (roadController(newX, newY) > 0) {
            if (x < 365)
                x = x - 3;
            else if (x > 375)
                x = x + 3;
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

    public boolean isGO() {
        return GO;
    }

    public void setGO(boolean GO) {
        this.GO = GO;
    }

    
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
