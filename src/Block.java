import java.awt.*;

public class Block implements IGameConstants{
    private int x;
    private int y;
    private int w;
    private int h;
    protected boolean isVisible;

    public Block(int x, int y){
        this.x = x;
        this.y = y;
        w = BLOCK_W;
        h = BLOCK_H;
        isVisible = true;
    }

    public void drawBlock(Graphics graphics){
        graphics.drawRect(x, y, w, h);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, w, h);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
