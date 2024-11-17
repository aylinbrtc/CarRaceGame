import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class CarRace extends JFrame {

    private Player player1;
    private Player player2;
    private int speed;

    Timer gameTimer;
    private int minutes = 0;
    private int seconds = 0;
    private int milliseconds = 0;
    private long collisionStartTime = 0;

    Bot b1, b2, b3, b4, b5;

    ArrayList<Bot> bots = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Thread> collidingObjects = new ArrayList<>();

    ArrayList<Bot> collidingBots = new ArrayList<>();
    private long botsCollisionStartTime=0;
    Object o1= new Object();


    public CarRace() {
        setTitle("Car Race Game");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        player1 = new Player(Color.RED, 25, 385, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_W);
        player2 = new Player(Color.GREEN, 45, 385, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_UP);


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                player1.handleKeyPress(e.getKeyCode());
                player2.handleKeyPress(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player1.handleKeyRelease(e.getKeyCode());
                player2.handleKeyRelease(e.getKeyCode());
            }
        });


       // Timer display
        JLabel timerLabel = new JLabel("0:00:00");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        timerPanel.add(timerLabel);

        add(timerPanel, BorderLayout.NORTH);

        // Game timer logic
        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                milliseconds += 10;
                if (milliseconds >= 1000) {
                    milliseconds = 0;
                    seconds++;
                    if (seconds >= 60) {
                        seconds = 0;
                        minutes++;
                    }
                }

                updateTimerLabel(timerLabel);

            }
        });

        gameTimer.start();
        setVisible(true);
    }

    /**
     * Starts the game logic with bots and players.
     */
    public void game(int speed) {
        players.add(player1);
        players.add(player2);

        player1.start();
        player2.start();

        this.speed = speed;

        b1 = new Bot(65, 385, speed);
        b2 = new Bot(85, 385, speed);
        b3 = new Bot(105, 385, speed);
        b4 = new Bot(125, 385, speed);
        b5 = new Bot(145, 385, speed);


        bots.add(b1);
        bots.add(b2);
        bots.add(b3);
        bots.add(b4);
        bots.add(b5);

        b1.start();
        b2.start();
        b3.start();
        b4.start();
        b5.start();

        // Main game loop
        while (!isGameOver()) {
            collisionController();
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                 e.printStackTrace();
            }
        }
           
    }

    private void updateTimerLabel(JLabel timerLabel) {
        String time = String.format("Time: %d:%02d:%02d", minutes, seconds, milliseconds / 10);
        timerLabel.setText(time);
    }

    private boolean isGameOver() {


        if (finishController(player1)) {
            if (player1.getY() <= 390 && player1.getY() >= 350 && player1.getX() >= 70 && player1.getX() <= 170) {
                b1.setGO(true);
                b2.setGO(true);
                b3.setGO(true);
                b4.setGO(true);
                b5.setGO(true);
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "1. Oyuncu Kazandı! Süresi " + String.format("%d:%02d:%02d", minutes, seconds, milliseconds / 10));
                return true;
            }
        }if (finishController(player2)) {
            if (player2.getY() <= 390 && player2.getY() >= 350 && player2.getX() >= 70 && player2.getX() <= 170) {
                b1.setGO(true);
                b2.setGO(true);
                b3.setGO(true);
                b4.setGO(true);
                b5.setGO(true);
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "2. Oyuncu Kazandı! Süresi " + String.format("%d:%02d:%02d", minutes, seconds, milliseconds / 10));
                return true;
            }
        }if (finishController(b1)) {
            if (b1.getY() <= 390 && b1.getY() >= 348 && b1.getX() >= 68 && b1.getX() <= 172) {
                b1.setGO(true);
                b2.setGO(true);
                b3.setGO(true);
                b4.setGO(true);
                b5.setGO(true);
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "Bot Kazandı! Süresi " + String.format("%d:%02d:%02d", minutes, seconds, milliseconds / 10));
                return true;
            }
        }if (finishController(b2)) {

            if (b2.getY() <= 390 && b2.getY() >= 348 && b2.getX() >= 68 && b2.getX() <= 172) {
                b1.setGO(true);
                b2.setGO(true);
                b3.setGO(true);
                b4.setGO(true);
                b5.setGO(true);
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "Bot Kazandı! Süresi " + String.format("%d:%02d:%02d", minutes, seconds, milliseconds / 10));
                return true;
            }
        }if (finishController(b3)) {

            if (b3.getY() <= 390 && b3.getY() >= 348 && b3.getX() >= 68 && b3.getX() <= 172) {
                b1.setGO(true);
                b2.setGO(true);
                b3.setGO(true);
                b4.setGO(true);
                b5.setGO(true);
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "Bot Kazandı! Süresi " + String.format("%d:%02d:%02d", minutes, seconds, milliseconds / 10));
                return true;
            }
        }if (finishController(b4)) {

            if (b4.getY() <= 390 && b4.getY() >= 348 && b4.getX() >= 68 && b4.getX() <= 172) {
                b1.setGO(true);
                b2.setGO(true);
                b3.setGO(true);
                b4.setGO(true);
                b5.setGO(true);
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "Bot Kazandı! Süresi " + String.format("%d:%02d:%02d", minutes, seconds, milliseconds / 10));
                return true;
            }
        }if (finishController(b5)) {
            if (b5.getY() <= 390 && b5.getY() >= 348 && b5.getX() >= 68 && b5.getX() <= 172) {
                b1.setGO(true);
                b2.setGO(true);
                b3.setGO(true);
                b4.setGO(true);
                b5.setGO(true);
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "Bot Kazandı! Süresi " + String.format("%d:%02d:%02d", minutes, seconds, milliseconds / 10));
                return true;
            }
        }

        return false;
    }

    private void collisionController() {
        collidingObjects.clear();
        collidingBots.clear();
        boolean p = Math.pow(player1.getCenterX() - player2.getCenterX(), 2) + Math.pow(player1.getCenterY() - player2.getCenterY(), 2) < Math.pow(10, 2);
        if (p) {
            collidingObjects.add(player1);
            collidingObjects.add(player2);
            collisionStartTime = System.currentTimeMillis();
            if (player1.getCenterX() > player2.getCenterX()) {
                if (player1.roadController(player1.getX() + 3, player1.getY()) == 0)
                    player1.setX(player1.getX() + 3);
                if ((player2.roadController(player2.getX() - 3, player2.getY()) == 0))
                    player2.setX(player2.getX() - 3);
            } else {
                if (player1.roadController(player1.getX() - 3, player1.getY()) == 0)
                    player1.setX(player1.getX() - 3);
                if ((player2.roadController(player2.getX() + 3, player2.getY()) == 0))
                    player2.setX(player2.getX() + 3);
            }
        }
        for (int i = 0; i < bots.size(); i++) {
            for (int j = 0; j < bots.size(); j++) {
                if (i != j) {
                    boolean b = Math.pow(bots.get(i).getCenterX() - bots.get(j).getCenterX(), 2) + Math.pow(bots.get(i).getCenterY() - bots.get(j).getCenterY(), 2) < Math.pow(10, 2);
                    if (b) {
                        synchronized (this) {
                            collidingBots.add(bots.get(i));
                            collidingBots.add(bots.get(j));
                            botsCollisionStartTime = System.currentTimeMillis();
                            if (bots.get(i).getCenterX() > bots.get(j).getCenterX()) {
                                if (bots.get(i).roadController(bots.get(i).getX() + 3, bots.get(i).getY()) == 0)
                                    bots.get(i).setX(bots.get(i).getX() + 3);
                                if ((bots.get(j).roadController(bots.get(j).getX() - 3, bots.get(j).getY()) == 0))
                                    bots.get(j).setX(bots.get(j).getX() - 3);
                                repaint();

                            } else {
                                if (bots.get(i).roadController(bots.get(i).getX() - 3, bots.get(i).getY()) == 0)
                                    bots.get(i).setX(bots.get(i).getX() - 3);
                                if ((bots.get(j).roadController(bots.get(j).getX() + 3, bots.get(j).getY()) == 0))
                                    bots.get(j).setX(bots.get(j).getX() + 3);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < bots.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                boolean bp = Math.pow(bots.get(i).getCenterX() - players.get(j).getCenterX(), 2) + Math.pow(bots.get(i).getCenterY() - players.get(j).getCenterY(), 2) < Math.pow(10, 2);
                if (bp) {
                    collidingObjects.add(bots.get(i));
                    collidingObjects.add(players.get(j));
                    collisionStartTime = System.currentTimeMillis();
                    if (bots.get(i).getCenterX() > players.get(j).getCenterX()) {
                        if (bots.get(i).roadController(bots.get(i).getX() + 3, bots.get(i).getY()) == 0)
                            bots.get(i).setX(bots.get(i).getX() + 3);
                        if ((players.get(j).roadController(players.get(j).getX() - 3, players.get(j).getY()) == 0))
                            players.get(j).setX(players.get(j).getX() - 3);
                    } else {
                        if (bots.get(i).roadController(bots.get(i).getX() - 3, bots.get(i).getY()) == 0)
                            bots.get(i).setX(bots.get(i).getX() - 3);
                        if ((players.get(j).roadController(players.get(j).getX() + 3, players.get(j).getY()) == 0))
                            players.get(j).setX(players.get(j).getX() + 3);
                    }
                }
            }
        }

    }

    private boolean finishController(Bot b) {
        int X = b.getX();
        int Y = b.getY();
        if (X > 300 && X < 440 && Y <= 210 && Y >= 30) {
            b.setCondition1(true);
        } else if (X > 480 && X <= 740 && Y >= 320 && Y <= 440) {
            b.setCondition2(true);
        } else if (X > 270 && X < 450 && Y <= 800 && Y >= 490) {
            b.setCondition3(true);
        }

        return (b.isCondition1() && b.isCondition2() && b.isCondition3());
    }

    private boolean finishController(Player p){
        int X= p.getX();
        int Y= p.getY();


        if(X>320&& X<420 && Y<=190 && Y>=10){
            p.setCondition1(true);
        }else if(X>500&& X<=720 && Y>=340 && Y<=420) {
            p.setCondition2(true);
        }else if(X>320&& X<420 && Y<=740 && Y>=530) {
            p.setCondition3(true);
        }

        return (p.isCondition1() && p.isCondition2() && p.isCondition3());
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.drawOval(20, 40, 700, 700);
        g.drawOval(170, 190, 400, 400);
        g.drawLine(20, 390, 170, 390);


        player1.draw(g);
        player2.draw(g);



        b1.draw(g);
        b2.draw(g);
        b3.draw(g);
        b4.draw(g);
        b5.draw(g);

    }




}

