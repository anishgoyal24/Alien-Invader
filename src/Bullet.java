import java.awt.*;

public class Bullet implements IGameConstants{
    private int x;
    private int y;
    private int w;
    private int h;
    private int speed;
    public boolean isVisible;

    public Bullet(int x, int dir){
        isVisible = true;
        this.x = x + (PLAYER_W/2);
        this.y = FLOOR;
        this.w = this.h = 3;
        this.speed = 20 * dir;
    }

    public Bullet(int x, int y, int dir){
        isVisible = true;
        this.x = x;
        this.y = y;
        this.w = this.h = 3;
        this.speed = 20 * dir;
    }

    public void drawBullet(Graphics graphics){
        if (isVisible) {
            graphics.drawRect(x, y, w, h);
        }
    }

    public void move(){
        y += speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
