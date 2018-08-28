import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Enemy implements IGameConstants{
    private int x;
    private int y;
    private int w;
    private int h;
    private Image image;
    private int speed;
    public int points;
    public boolean isVisible = true;

    public Enemy(int x, int y, int points, int dir){
        this.points = points;
        this.x = x;
        this.y = y;
        this.h = ENEMY_H;
        this.w = ENEMY_W;
        image = new ImageIcon(Enemy.class.getResource(ENEMY_IMG)).getImage();
        this.speed = this.speed * dir;
    }

    public void drawEnemy(Graphics graphics){
        if (isVisible == true) {
            graphics.drawImage(image, x, y, w, h, null);
        }
    }

    public void changeDirection(int dir, int speed){
        this.speed = speed;
        this.speed = this.speed * dir;
    }

    public void move(){
        x += speed;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}


