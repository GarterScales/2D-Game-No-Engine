package object;

import Main.GamePanel;
import entity.Projectile;

import java.awt.*;

public class OBJ_Ectoblast extends Projectile {

    GamePanel gp;

    public OBJ_Ectoblast(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Ectoblast";

        speed = defaultSpeed*2-defaultSpeed/4;

        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setUp("/projectile/ectoblast_up_1");
        up2 = setUp("/projectile/ectoblast_up_2");
        down2 = setUp("/projectile/ectoblast_down_1");
        down1 = setUp("/projectile/ectoblast_down_2");
        left1 = setUp("/projectile/ectoblast_left_1");
        left2 = setUp("/projectile/ectoblast_left_2");
        right1 = setUp("/projectile/ectoblast_right_1");
        right2 = setUp("/projectile/ectoblast_right_2");
    }
    public Color getParticleColor(){
        Color color = new Color(150, 250, 150);
        return color;
    }

    public int getParticleSize(){
        int size = 10; // 6 pixels
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
