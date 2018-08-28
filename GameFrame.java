import javax.swing.*;

public class GameFrame extends JFrame implements IGameConstants {
    GameFrame() {
        setSize(GWIDTH, GHEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        Board b = new Board(this);
        add(b);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String args[]){
        GameFrame g = new GameFrame();
    }
}


