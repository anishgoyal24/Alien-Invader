import java.awt.*;

public class Wall implements IGameConstants {
    private Block block[] = new Block[8];
    private int x;
    private int y;

    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
        createWall();
    }

    public void createWall() {
        int xPos = x;
        int yPos = y;
        block[0] = new Block(xPos, yPos);
        for (int i = 1; i < 8; i++) {
            if (i % 4 == 0) {
                xPos = x;
                yPos += BLOCK_H;
                block[i] = new Block(xPos, yPos);
            } else {
                xPos += BLOCK_W;
                block[i] = new Block(xPos, yPos);
            }
        }
    }

    public void drawWall(Graphics g) {
        for (Block b : block) {
            b.drawBlock(g);
        }
    }

   /* public void wallCollision(Bullet b) {
        for (Block bl : block) {
            if ((b.getY() > bl.getY() && (b.getY() < bl.getY() + BLOCK_H))){
                 if ((b.getX() > bl.getX() && (b.getX() < bl.getX() + BLOCK_W))) {
                     b.isVisible = false;
                }
            }
        }
    } */
}
