package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_RSG extends Entity{

    public NPC_RSG(GamePanel gp){
        super(gp);

        direction = "down";
        speed = defaultSpeed-defaultSpeed/2;
        solidAreaDefaultX = gp.smallTileSize/2;
        solidAreaDefaultY = gp.tileSize;
        solidArea = new Rectangle(gp.smallTileSize/2, gp.tileSize, 30, 30);

        getImage();
        setDialogue();
    }
    public void getImage(){

        upStand = setUp("/npc/rsg_up_stand", gp.tileSize, gp.bigTileSize);
        downStand = setUp("/npc/rsg_down_stand", gp.tileSize, gp.bigTileSize);
        rightStand = setUp("/npc/rsg_right_stand", gp.tileSize, gp.bigTileSize);
        leftStand = setUp("/npc/rsg_left_stand", gp.tileSize, gp.bigTileSize);
        up1 = setUp("/npc/rsg_right_step_up", gp.tileSize, gp.bigTileSize);
        up2 = setUp("/npc/rsg_left_step_up", gp.tileSize, gp.bigTileSize);
        down1 = setUp("/npc/rsg_right_step_down", gp.tileSize, gp.bigTileSize);
        down2 = setUp("/npc/rsg_left_step_down", gp.tileSize, gp.bigTileSize);
        left1 = setUp("/npc/rsg_right_step_left", gp.tileSize, gp.bigTileSize);
        left2 = setUp("/npc/rsg_left_step_left", gp.tileSize, gp.bigTileSize);
        right1 = setUp("/npc/rsg_right_step_right", gp.tileSize, gp.bigTileSize);
        right2 = setUp("/npc/rsg_left_step_right", gp.tileSize, gp.bigTileSize);

    }
    public void setDialogue(){
        dialogues[0] = "Happy Halloween!";
        dialogues[1] = "Nice costume, are you looking for candy too?";
        dialogues[2] = "How did you manage to hide your feet so well?";
        dialogues[3] = "Have you seen my skateboard?";
        dialogues[4] = "Have a good night!";
    }

    public void setAction(){

        if(onPath){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);
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
    public void speak() {
        super.speak();
        onPath = true;
    }
}
