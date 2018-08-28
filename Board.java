import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements IGameConstants {
    private Player player;
    private int score;
    private Timer timer;
    private Enemy enemies[] = new Enemy[MAX_ENEMY];
    private int enemySpeed = 1;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();
    private ArrayList<Enemy> specialEnemy= new ArrayList<>();
    private int specialEnemyCounter = 1;
    private int specialEnemyIndex = 0;
    private int specialEnemyDirection = 1;
    private BufferedImage bgImage;
    private int count;

    public Board(JFrame jFrame){
        try{
            bgImage = ImageIO.read(Board.class.getResource(BG_IMAGE));
        }
        catch (Exception e){
            System.out.println(e);
        }
      player = new Player(jFrame);
      loadEnemy();
      loadSpecialEnemy();
      setBackground(Color.BLACK);
      setFocusable(true);
      gameLoop();
      bindActions();
      count = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawScore(g);
        player.drawPlayer(g);
        paintEnemy(g);
        drawLife(g);
        for (Bullet b : bullets){
            b.drawBullet(g);
        }
        for (Bullet bull : enemyBullets){
            bull.drawBullet(g);
        }
        checkCollision();
        checkGameOver(g);
        playerCollision();
        if (player.life == 0){
           gameOver(g);
        }
        if (count == MAX_ENEMY){
            drawWin(g);
        }
    }

    private void drawBackground(Graphics graphics){
        graphics.drawImage(bgImage, 0, 0, GWIDTH, GHEIGHT, null);
    }

    private void drawScore(Graphics graphics){
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.drawString("Score is: " + score, 10, 25);
    }

    private void gameLoop(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                repaint();
                player.move();
                moveEnemy();
                checkMayday();
                for (Enemy e: enemies){
                    e.move();
                }
                for (Bullet b : bullets){
                    b.move();
                }
                for (Bullet bl : enemyBullets){
                    bl.move();
                }
                for (Enemy e : specialEnemy){
                    e.move();
                }
                addEnemyBullet();
            }
        };
        timer = new Timer(DELAY, actionListener);
        timer.start();
    }

    private void bindActions(){
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
                    player.direction(1);
                }

                else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
                    player.direction(-1);
                }

                else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
                    bullets.add(new Bullet(player.getX(), -1));
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if ((keyEvent.getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)){
                    player.direction(0);
                }
            }
        };
        this.addKeyListener(keyListener);
    }

    private void loadEnemy(){
        int x = 50;
        int y = UPPER_CONSTRAINT;
        for (int i = 0; i < MAX_ENEMY; i++){
            if ((i % ENEMY_PER_LINE) == 0){
                x = 50;
                y += GAP_H;
            }
            enemies[i] = new Enemy(x, y, 30, 1);
            x += GAP_W;
        }
        for (Enemy e : enemies){
            e.changeDirection(1, enemySpeed);
        }
    }

    private void paintEnemy(Graphics graphics){
        for (Enemy e : enemies){
            e.drawEnemy(graphics);
        }
        for (Enemy e : specialEnemy){
            e.drawEnemy(graphics);
        }
        if (specialEnemyIndex < 2) {
            if (specialEnemyCounter % 400 == 0) {
                specialEnemy.get(specialEnemyIndex).isVisible = true;
                specialEnemy.get(specialEnemyIndex).changeDirection(specialEnemyDirection, 3);
                specialEnemyDirection = -1;
                specialEnemyIndex++;
            }
            specialEnemyCounter++;
        }
    }

    private void moveEnemy() {
        for (int i = 0; i < ENEMY_PER_LINE; i++) {
            if ((enemies[i].isVisible) || (enemies[i + ENEMY_PER_LINE].isVisible) || (enemies[i + 2 * ENEMY_PER_LINE].isVisible) || (enemies[i + 3 * ENEMY_PER_LINE].isVisible)) {
                if (enemies[i].getX() < LEFT_CONSTRAINT) {
                    for (Enemy e : enemies) {
                        e.setY(e.getY() + GAP_H);
                        e.changeDirection(1, enemySpeed + 1);
                    }
                }
            }
        }
        for (int i = ENEMY_PER_LINE - 1; i >= 0 ; i--) {
            if ((enemies[i].isVisible) || (enemies[i + ENEMY_PER_LINE].isVisible) || (enemies[i + 2 * ENEMY_PER_LINE].isVisible) || (enemies[i + 3 * ENEMY_PER_LINE].isVisible)) {
                if (enemies[i].getX() > RIGHT_CONSTRAINT - ENEMY_W) {
                    for (Enemy e : enemies) {
                        e.setY(e.getY() + GAP_H);
                        e.changeDirection(-1, enemySpeed);
                    }
                }
            }
        }
    }

    private boolean collision(Bullet b, Enemy e){
        if ((b.isVisible) && (e.isVisible)) {
            if ((b.getX() > e.getX() && (b.getX() < e.getX() + ENEMY_W))) {
                if ((b.getY() > e.getY() && (b.getY() < e.getY() + ENEMY_H))) {
                    b.isVisible = false;
                    e.isVisible = false;
                    score += e.points;
                    return true;
                }
            }
        }
        return false;
    }

    private void checkCollision(){
        for (Enemy e : enemies){
            for (Bullet b : bullets){
                if (collision(b, e)){
                    count++;
                }
            }
        }
        for (Enemy e : specialEnemy){
            for (Bullet b : bullets){
                collision(b, e);
            }
        }
    }

    private void checkGameOver(Graphics g){
        for (int i = MAX_ENEMY - 1; i >= 0; i--){
            if ((enemies[i].getY() + ENEMY_H) >=  WALL_Y){
                gameOver(g);
            }
        }
    }

    private int bulletDelay = 0;

    private void addEnemyBullet(){
        if (bulletDelay == 12) {
            int enemyIndex = new Random().nextInt(32);
            bulletDelay = 0;
            if (enemies[enemyIndex].isVisible) {
                enemyBullets.add(new Bullet(enemies[enemyIndex].getX() + ENEMY_W/2, enemies[enemyIndex].getY() + ENEMY_H/2, 1));
            }
        }
        else {
            bulletDelay++;

        }
    }

    private void playerCollision() {
        for (Bullet b : enemyBullets) {
            if ((b.getX() > player.getX() && (b.getX() < player.getX() + ENEMY_W))) {
                if ((b.getY() > player.getY() && (b.getY() < player.getY() + ENEMY_H))) {
                    b.isVisible = false;
                    player.life--;
                }
            }
        }
    }

    private void drawLife(Graphics graphics){
        graphics.setColor(Color.WHITE);
        graphics.drawString("Life : " + player.life, 500, 25);
    }

    private void gameOver(Graphics g){
        g.drawString("Game Over", 250, 300);
        drawLife(g);
        timer.stop();
    }

    private void loadSpecialEnemy(){
        specialEnemy.add(new Enemy(0 - ENEMY_W, 30, 300, 0));
        specialEnemy.add(new Enemy(GWIDTH, 30, 300, 0));
        for (Enemy e: specialEnemy){
            e.isVisible = false;
        }
    }
    
    private void checkMayday(){
        if (count == MAX_ENEMY - 5){
            for (Enemy e : enemies){
                enemySpeed = 7;
                e.setSpeed(enemySpeed);
            }
        }
    }

    private void drawWin(Graphics graphics){
        for(Enemy e: enemies){
            e.isVisible = false;
        }
        graphics.drawString("You Win", 250, 300);
        timer.stop();
    }

}
