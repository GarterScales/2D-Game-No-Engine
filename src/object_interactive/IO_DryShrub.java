package object_interactive;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class IO_DryShrub extends InteractiveObject{

    GamePanel gp;

    public IO_DryShrub(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        collision = true;
        alive = false;

        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;

        down1 = setUp("/objects/dry_shrub");
        destructible = true;

        Random r = new Random();

        life = r.nextInt(3)+1;

    }
    public InteractiveObject getDestroyedForm(){
        InteractiveObject object = new IO_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return object;
    }

    public Color getParticleColor(){
        Color color = new Color(65, 50, 30);
        return color;
    }

    public int getParticleSize(){
        int size = 6; // 6 pixels
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
