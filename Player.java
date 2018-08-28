import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player implements IGameConstants{
    private int x;
    private int y;
    private int w;
    private int h;
    public int life;
    private BufferedImage image;
    private int speed;
    private JFrame frame;

    public Player(JFrame jFrame){
        life = 4;
        this.x = 20;
        this.y = FLOOR;
        this.h = PLAYER_H;
        this.w = PLAYER_W;
        this.frame = jFrame;
        try {
            image = ImageIO.read(Player.class.getResource(PLAYER_IMG));
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(frame, "Backgraound Image not found");
            System.exit(0);
        }
        catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(frame, "Backgraound Image not found");
            System.exit(0);
        }

    }

    public void drawPlayer(Graphics graphics){
        graphics.drawImage(image, x, FLOOR, w, h, null);
    }

    public void direction(int dir){
        speed = 5;
        speed = speed * dir;
    }

    public void move(){
        if (x < LEFT_CONSTRAINT){
            x = LEFT_CONSTRAINT;
        }

        else if (x > RIGHT_CONSTRAINT - w){
            x = RIGHT_CONSTRAINT - w;
        }
        else{
            x += speed;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
