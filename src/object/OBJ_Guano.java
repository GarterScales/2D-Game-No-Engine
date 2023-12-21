package object;

import Main.GamePanel;
import entity.Projectile;

import java.awt.*;

public class OBJ_Guano extends Projectile {
        GamePanel gp;

        public OBJ_Guano(GamePanel gp) {
            super(gp);
            this.gp = gp;

            name = "Guano";

            speed = defaultSpeed*2;

            maxLife = 80;
            life = maxLife;
            attack = 1;
            useCost = 1;
            alive = false;
            getImage();
        }

        public void getImage(){
            up1 = setUp("/projectile/guano");
            up2 = setUp("/projectile/guano");
            down2 = setUp("/projectile/guano");
            down1 = setUp("/projectile/guano");
            left1 = setUp("/projectile/guano");
            left2 = setUp("/projectile/guano");
            right1 = setUp("/projectile/guano");
            right2 = setUp("/projectile/guano");
        }
    public Color getParticleColor(){
        Color color = new Color(100, 70, 40);
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
