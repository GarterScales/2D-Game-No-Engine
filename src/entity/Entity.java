package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, upStand, downStand, rightStand, leftStand;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage death1, death2, death3;
    public BufferedImage image, image2, image3, image4;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String[] dialogues = new String[20];

    //STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;

    //COUNTER
    public int spriteCounter = 0;
    public int standCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;
    int lastNum;

    //ENTITY ATTRIBUTES
    public int maxLife;
    public int life;
    public int attack;
    public int maxMana;
    public int mana;
    public String name, nickName;
    public int type; // 0 = player, 1 = NPC, 2 = Monster
    public int defaultSpeed, speed, speed2;
    public Projectile projectile;

    //ITEM ATTRIBUTES
    public int useCost;

    public Entity(GamePanel gp){
        this.gp = gp;
        defaultSpeed = gp.tileSize/14;

    }
    public void setAction(){

    }
    public void damageReaction(){

    }
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void checkDrop(){

    }
    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] == null){
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }

    public Color getParticleColor(){
        Color color = null;
        return color;
    }

    public int getParticleSize(){
        int size = 0;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);


    }
    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iObj);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer){
            damagePlayer(attack);
        }
    }

    public void update(){

        if(knockBack){
            checkCollision();

            if(collisionOn){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else {
                switch (gp.player.direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 15){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else{
            setAction();

            checkCollision();

            //IF COLLISION IS FALSE, ENTITY CAN MOVE
            if (!collisionOn) {

                switch (direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
        }
        spriteCounter++;
        if(upStand != null){
            if(spriteCounter > 12) {
                if(spriteNum == 1){
                    spriteNum =3;
                    lastNum = 1;
                }
                else if(spriteNum == 2){
                    spriteNum = 3;
                    lastNum = 2;
                }
                else if(spriteNum == 3 && lastNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 3 && lastNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
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

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

    }
    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            //entity damages player
            gp.playSE(8);
            gp.player.life -= this.attack;
            gp.player.invincible = true;
        }
    }
    public void draw(Graphics2D g2, KeyHandler keyH){
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX - gp.bigTileSize &&
                worldX - gp.tileSize< gp.player.worldX + gp.player.screenX + gp.bigTileSize &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY - gp.bigTileSize &&
                worldY - gp.tileSize< gp.player.worldY + gp.player.screenY + gp.bigTileSize) {

            switch(direction){
                case "up":
                    if(spriteNum == 1){ image = up1;}
                    if(spriteNum == 2){ image = up2;}
                    if(spriteNum == 3) { image = upStand;}
                    break;
                case "down":
                    if(spriteNum == 1){ image = down1;}
                    if(spriteNum == 2){image = down2;}
                    if(spriteNum == 3) {image = downStand;}
                    break;
                case "left":
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                    if(spriteNum == 3) {image = leftStand;}
                    break;
                case "right":
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                    if(spriteNum == 3) {image = rightStand;}
                    break;
            }

            //MONSTER HP BAR
            double oneScale = (double) gp.tileSize/maxLife;
            double hpBarValue = oneScale*life;

            if(type == 2 && hpBarOn){
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX-1, screenY-gp.tileSize/3-1, gp.tileSize+2, gp.smallTileSize/2);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY-(gp.tileSize/3), (int)hpBarValue, gp.tileSize/3-gp.smallTileSize/4);

                hpBarCounter++;

                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if(invincible && !dying && alive){
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            if(dying){
                deathAnimation(g2, screenX, screenY);
            }
            else {
                g2.drawImage(image, screenX, screenY, null);
            }

            //RESET ALPHA
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            //DEBUG COLLISION
            if (keyH.debugOn){
                g2.setColor(Color.RED);
                g2.drawRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);
            }
        }
    }
    public void deathAnimation(Graphics2D g2, int screenX, int screenY){
        int i = 5;

        dyingCounter++;

        if(dyingCounter <= i){
            image = death1;
        }
        if(dyingCounter > i && dyingCounter <= i*2){
            image = death2;
        }
        if(dyingCounter > i*2){
            image = death3;
        }
        if(dyingCounter > i*3){
            alive = false;
            dying = false;
        }
        g2.drawImage(image, screenX, screenY, null);
    }

    public BufferedImage setUp(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public BufferedImage setUp(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaleImage(image, width, height);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()){
            //Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            //Entity's solid area position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                //Left or Right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {
                //up or left
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                //up or right
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                //down or left
                direction = "down";
                checkCollision();
                if (collisionOn){
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                //down or right
                direction = "down";
                checkCollision();
                if (collisionOn){
                    direction = "right";
                }
            }

//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//            if(nextCol == goalCol && nextRow == goalRow){
//                onPath = false;
//            }

        }
    }
}
