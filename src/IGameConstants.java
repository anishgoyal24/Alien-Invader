public interface IGameConstants {
    int GWIDTH = 600;
    int GHEIGHT = 600;
    String BG_IMAGE = "bgimage.jpg";
    String TITLE = "Alien Invader";
    String PLAYER_IMG = "ship.png";
    int PLAYER_H = 40;
    int PLAYER_W = 40;
    int FLOOR = GHEIGHT - 100;
    String ENEMY_IMG = "enemy.gif";
    int LEFT_CONSTRAINT = 10;
    int RIGHT_CONSTRAINT = 590;
    int UPPER_CONSTRAINT = 10;
    int DELAY = 40;
    int ENEMY_H = 30;
    int ENEMY_W = 30;
    int GAP_W = ENEMY_W + 10;
    int GAP_H = ENEMY_H + 10;
    int MAX_ENEMY = 32;
    int ENEMY_PER_LINE = 8;
    int BLOCK_H = 10;
    int BLOCK_W = 15;
    int WALL_Y = FLOOR - PLAYER_H;
    int NUM_WALL = 3;
}
