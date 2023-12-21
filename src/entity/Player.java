package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;
import object.OBJ_Ectoblast;
import object.OBJ_Treat;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{

    KeyHandler keyH;
    UtilityTool uTool;

    public final int screenX;
    public final int screenY;
    public int maxScore = 9;
    public int score = 0;
    public boolean hasSkateboard = false;

    BufferedImage skateup1, skateup2, skatedown1, skatedown2, skateleft1, skateleft2, skateright1, skateright2;

    int temp;

//    public BufferedImage lastTreat;

    public Player(GamePanel gp, KeyHandler keyH){
        super (gp);
        this.keyH = keyH;

        screenX = gp.halfScreenWidth - gp.smallTileSize;
        screenY = gp.halfScreenHeight;

        solidArea = new Rectangle(gp.smallTileSize/2, gp.smallTileSize, gp.smallTileSize, (int)(gp.tileSize/1.5));
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        temp = solidAreaDefaultY;

        attackArea.width = gp.tileSize - gp.smallTileSize/2;
        attackArea.height = gp.tileSize - gp.smallTileSize/2;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 41;
        worldY = gp.tileSize * 38;
        speed = defaultSpeed;
        speed2 = speed  * 2;
        direction = "down";
        invincible = false;
        hasSkateboard = false;
        keyH.rideSkateboard = false;


        //PLAYER STATUS
        maxLife = 3;
        life = maxLife;
        attack = 1;
        score = 0;
        projectile = new OBJ_Ectoblast(gp);
    }
    public void getPlayerImage(){

            skateup1 = setUp("/player/fred_up_skate_1", gp.tileSize, gp.bigTileSize);
            skateup2 = setUp("/player/fred_up_skate_2", gp.tileSize, gp.bigTileSize);
            skatedown1 = setUp("/player/fred_down_skate_1", gp.tileSize, gp.bigTileSize);
            skatedown2 = setUp("/player/fred_down_skate_2", gp.tileSize, gp.bigTileSize);
            skateleft1 = setUp("/player/fred_left_skate_1", gp.tileSize, gp.bigTileSize);
            skateleft2 = setUp("/player/fred_left_skate_2", gp.tileSize, gp.bigTileSize);
            skateright1 = setUp("/player/fred_right_skate_1", gp.tileSize, gp.bigTileSize);
            skateright2 = setUp("/player/fred_right_skate_2", gp.tileSize, gp.bigTileSize);
            up1 = setUp("/player/fred_up_1", gp.tileSize, gp.bigTileSize);
            up2 = setUp("/player/fred_up_2", gp.tileSize, gp.bigTileSize);
            down1 = setUp("/player/fred_down_1", gp.tileSize, gp.bigTileSize);
            down2 = setUp("/player/fred_down_2", gp.tileSize, gp.bigTileSize);
            left1 = setUp("/player/fred_left_1", gp.tileSize, gp.bigTileSize);
            left2 = setUp("/player/fred_left_2", gp.tileSize, gp.bigTileSize);
            right1 = setUp("/player/fred_right_1", gp.tileSize, gp.bigTileSize);
            right2 = setUp("/player/fred_right_2", gp.tileSize, gp.bigTileSize);

    }
    public void getPlayerAttackImage(){
        attackUp1 = setUp("/player/fred_up_1_attack", gp.tileSize, gp.bigTileSize);
        attackUp2 = setUp("/player/fred_up_2_attack", gp.tileSize, gp.bigTileSize);
        attackDown1 = setUp("/player/fred_down_1_attack", gp.tileSize, gp.bigTileSize);
        attackDown2 = setUp("/player/fred_down_2_attack", gp.tileSize, gp.bigTileSize);
        attackLeft1 = setUp("/player/fred_left_1_attack", gp.bigTileSize, gp.bigTileSize);
        attackLeft2 = setUp("/player/fred_left_2_attack", gp.bigTileSize, gp.bigTileSize);
        attackRight1 = setUp("/player/fred_right_1_attack", gp.bigTileSize, gp.bigTileSize);
        attackRight2 = setUp("/player/fred_right_2_attack", gp.bigTileSize, gp.bigTileSize);

    }
    public void update() {
        if(keyH.mouse1Pressed && !keyH.rideSkateboard){
            gp.playSE(9);
            attacking = true;
            keyH.mouse1Pressed = false;
        }

        if(hasSkateboard && keyH.rideSkateboard){
                speed = speed2;
            }
        else {
            speed = defaultSpeed;
        }

        if(attacking){
            attacking(direction);
        }
        else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.ePressed){

            // double mag = Math.sqrt(worldX*worldX + worldY*worldY);
           /* if(keyH.upPressed && keyH.rightPressed) {
                direction = "right";
                worldX += (worldX / mag) * speed; worldY -= (worldY / mag) * speed;
            }
            else if(keyH.upPressed && keyH.leftPressed) {
                direction = "left";
                worldX -= (worldX / mag) * speed; worldY -= (worldY / mag) * speed;
            }
            else if(keyH.downPressed && keyH.rightPressed) {
                direction = "right";
                worldX += (worldX / mag) * speed; worldY += (worldY / mag) * speed;
            }
            else if(keyH.downPressed && keyH.leftPressed) {
                direction = "left";
                worldX -= (worldX / mag) * speed; worldY += (worldY / mag) * speed;
            }
            */
            if(keyH.upPressed) {
                direction = "up";
            }
            else if(keyH.downPressed) {
                direction = "down";
            }
            else if(keyH.leftPressed) {
                direction = "left";
            }
            else if(keyH.rightPressed){
                direction = "right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK INTERACTIVE OBJECT COLLISION
            gp.cChecker.checkEntity(this, gp.iObj);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK EVENT
            gp.eHandler.checkEvent();

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.ePressed) {

                switch (direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            gp.keyH.ePressed = false;

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1){
                    spriteNum =2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }
        else{
            standCounter++;

            if (standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if(keyH.shootKeyPressed && !projectile.alive && shotAvailableCounter == 30){
            //mutator
            projectile.set(worldX, worldY, direction, true, this);

            //Check Vacancy
            for(int i = 0; i < gp.projectile.length; i++){
                if(gp.projectile[i] == null){
                    gp.projectile[i] = projectile;
                    break;
                }
            }
            gp.playSE(10);
            shotAvailableCounter = 0;
        }

        //THIS NEEDS TO BE OUTSIDE OF KEY IF STATEMENT
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

        if(life <= 0){
            gp.playSE(13);
            gp.stopMusic();
            gp.gameState = gp.gameOverState;
        }

    }

    public void attacking(String direction){

        spriteCounter++;

        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;

            //SAVE THE CURRENT worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //ADJUST PLAYER'S worldX/worldY for the attackArea
            switch (direction){
                case "up": worldY -= attackArea.height;
                    break;
                case "down": worldY += attackArea.height;
                    break;
                case "left": worldX -= attackArea.width;
                    break;
                case "right": worldX += attackArea.width;
                    break;
            }
            //ATTACK AREA BECOMES SOLID AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //CHECK MONSTER COLLISION WITH UPDATED WORLDX/Y, solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iObjIndex = gp.cChecker.checkEntity(this, gp.iObj);
            damageInteractiveObject(iObjIndex);

            int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

            //RESTORE VALUES
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "treat":
                    gp.playSE(1);
                    score++;
//                    lastTreat = gp.obj[i].image;
                    gp.ui.showMessage("You collected a "+gp.obj[i].nickName+"!");
                    gp.obj[i] = null;

                    if (score == maxScore){
                        gp.ui.gameFinished = true;
                        gp.stopMusic();
                        gp.playSE(4);
                    }

                    break;
                case "Door":
                    if (score > 0){
                        gp.playSE(3);
                        gp.ui.showMessage("You opened the door!");
                        gp.obj[i] = null;
                        score--;
                    }
                    else{
                        gp.playSE(5);
                        gp.ui.showMessage("\"Come back with a candy!\"");
                    }
                    break;
                case "Skateboard":
                    gp.playSE(2);
                    gp.ui.showMessage("You got a skateboard! Press X to ride!");
                    gp.obj[i] = null;
                    hasSkateboard = true;
                    break;
            }
        }
    }

    public void interactNPC(int i){
        if(i != 999){
            gp.ui.showMessage("Press E to talk!");
            if(gp.keyH.ePressed){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i){

        if(i != 999){

            if(!invincible && gp.monster[i].alive){
                gp.playSE(8);
                life-= gp.monster[i].attack;
                invincible = true;
            }
        }

    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (!gp.monster[i].invincible) {
                gp.playSE(7);
                knockBack(gp.monster[i]);
                gp.monster[i].life -= attack;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
    }
    public void knockBack(Entity entity){
        entity.direction = direction;
        speed += 10;
        entity.knockBack = true;
    }

    public void damageInteractiveObject(int i){

        if(i != 999 && gp.iObj[i].destructible && !gp.iObj[i].invincible){
            gp.playSE(11);
            gp.iObj[i].life--;
            gp.iObj[i].invincible = true;

            //generate particle
            generateParticle(gp.iObj[i], gp.iObj[i]);

            if(gp.iObj[i].life <= 0){
                gp.iObj[i] = gp.iObj[i].getDestroyedForm();
            }
        }
    }
    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gp.projectile[i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void draw(Graphics2D g2, KeyHandler keyH) {

        int tempScreenX = screenX;

        this.keyH = keyH;

        BufferedImage image = null;

        switch(direction){
            case "up":
                if(keyH.rideSkateboard && hasSkateboard){
                    if(spriteNum == 1){image = skateup1;}
                    if(spriteNum == 2){image = skateup2;}
                }
                else if(attacking){
                    if(spriteNum == 1){image = attackUp1;}
                    if(spriteNum == 2){image = attackUp2;}
                }
                else{
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                }
                break;
            case "down":
                if(keyH.rideSkateboard && hasSkateboard){
                    if(spriteNum == 1){image = skatedown1;}
                    if(spriteNum == 2){image = skatedown2;}
                }
                else if(attacking){
                    if(spriteNum == 1){image = attackDown1;}
                    if(spriteNum == 2){image = attackDown2;}
                }
                else{
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum == 2){image = down2;}
                }
                break;
            case "left":
                if(keyH.rideSkateboard && hasSkateboard){
                    if(spriteNum == 1){image = skateleft1;}
                    if(spriteNum == 2){image = skateleft2;}
                }
                else if(attacking){
                    tempScreenX -= gp.tileSize;
                    if(spriteNum == 1){image = attackLeft1;}
                    if(spriteNum == 2){image = attackLeft2;}
                }
                else{
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                }
                break;
            case "right":
                if(keyH.rideSkateboard && hasSkateboard){
                    if(spriteNum == 1){image = skateright1;}
                    if(spriteNum == 2){image = skateright2;}
                }
                else if(attacking){
                    if(spriteNum == 1){image = attackRight1;}
                    if(spriteNum == 2){image = attackRight2;}
                }
                else{
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                }
                break;
        }

        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, tempScreenX, screenY- gp.smallTileSize, null);

        //RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG COLLISION
        if (this.keyH.debugOn){

            g2.setColor(Color.RED);
            g2.drawRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);

            //Invincible Time
            g2.setFont(new Font("Arial", Font.PLAIN, 26));
            g2.setColor(Color.WHITE);
            g2.drawString("Invincible Counter: "+invincibleCounter , gp.tileSize, gp.screenHeight-gp.bigTileSize);
        }



    }

}
