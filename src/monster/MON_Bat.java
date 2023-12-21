package monster;

import Main.GamePanel;
import Main.Main;
import entity.Entity;
import object.OBJ_Guano;
import object.OBJ_Treat;

import java.util.Random;

public class MON_Bat extends Entity {

    GamePanel gp;

    public MON_Bat(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = 2;
        name = "Bat";
        defaultSpeed = 3;
        speed = defaultSpeed;
        maxLife = 3;
        life = maxLife;
        attack = 1;
        projectile = new OBJ_Guano(gp);

        solidAreaDefaultY = gp.smallTileSize/2;
        solidAreaDefaultX = gp.smallTileSize/4;

        solidArea.x = solidAreaDefaultX;
        solidArea.y = solidAreaDefaultY;
        solidArea.width = gp.smallTileSize+gp.smallTileSize/2;
        solidArea.height = gp.smallTileSize+ gp.smallTileSize/2;
        getImage();
    }
    public void getImage(){
        up1 = setUp("/monster/bat_1");
        up2 = setUp("/monster/bat_2");
        down1 = setUp("/monster/bat_1");
        down2 = setUp("/monster/bat_2");
        left1 = setUp("/monster/bat_1");
        left2 = setUp("/monster/bat_2");
        right1 = setUp("/monster/bat_1");
        right2 = setUp("/monster/bat_2");
        death1 = setUp("/monster/pop_1");
        death2 = setUp("/monster/pop_2");
        death3 = setUp("/monster/pop_3");
    }
    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);

        int tileDistance = (xDistance + yDistance)/gp.tileSize;

        if(!onPath && tileDistance < 5){
            int i = new Random().nextInt(100)+1;
            if(i > 50){
                onPath = true;
            }
        }
        if(onPath && tileDistance > 20){
            onPath = false;
        }
    }
    public void setAction(){
        if(onPath){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);

            int r = new Random().nextInt(200);
            if(r > 198 && !projectile.alive && shotAvailableCounter == 30){
                projectile.set(worldX, worldY, direction, true, this);
                //Check Vacancy
                for(int i = 0; i < gp.projectile.length; i++){
                    if(gp.projectile[i] == null){
                        gp.projectile[i] = projectile;
                        break;
                    }
                }
                shotAvailableCounter = 0;
            }
        }
        else{
            actionLockCounter++;

            if(actionLockCounter == 120){
                Random r = new Random();
                int i = r.nextInt(100)+1;   //PICK A NUMBER FROM 1 to 100

                if (i <= 25){
                    direction = "up";
                }
                if (i > 25 && i <= 50){
                    direction = "down";
                }
                if (i > 50 && i <= 75){
                    direction = "left";
                }
                if (i > 75){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
        onPath = true;
    }
    public void checkDrop(){
        //use random number here if you want chance
        dropItem(new OBJ_Treat(gp));
    }
}
