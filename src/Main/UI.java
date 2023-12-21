package Main;

import entity.Entity;
import entity.Player;
import object.OBJ_Heart;
import object.OBJ_Pumpkin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Player player;
    Font orangeKid, maruMonica;
    BufferedImage p_empty, p_1, p_2, p_3, heart, empty_heart;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int subState = 0;
    public String currentDialogue;

    public int commandNum = 0;

//    double playTime;
//    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;

        try{
            InputStream is = getClass().getResourceAsStream("/font/orange kid.ttf");
            orangeKid = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException | IOException e){
            e.printStackTrace();
        }

        //CREATE HUD OBJECT
        Entity heartObj = new OBJ_Heart(gp);
        Entity pumpkin = new OBJ_Pumpkin(gp);
        heart = heartObj.image;
        empty_heart = heartObj.image2;
        p_empty = pumpkin.image;
        p_1 = pumpkin.image2;
        p_2 = pumpkin.image3;
        p_3 = pumpkin.image4;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(orangeKid);
        g2.setColor(Color.WHITE);
        //TITLE STATE UI
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //PLAY STATE UI
        if (gp.gameState == gp.playState){
            drawPlayerLife();
            drawPlayerScore();
            drawMessage();
        }
        //PAUSE STATE UI
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //DIALOGUE STATE UI
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawPlayerScore();
            drawDialogueScreen();
        }
        //GAME OVER STATE UI
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }


//        if (gameFinished){
//
//            g2.setFont(arial_40);
//            g2.setColor(Color.white);
//
//            String text;
//            int textLength;
//            int x;
//            int y;
//
//            text = "You found all the candy!";
//            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gp.halfScreenWidth - (textLength/2);
//            y = gp.halfScreenHeight - gp.tileSize*3;
//            g2.drawString(text, x, y);
//
//            text = "Time: "+ dFormat.format(playTime)+"!";
//            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gp.halfScreenWidth  - (textLength/2);
//            y = gp.halfScreenHeight- gp.bigTileSize;
//            g2.drawString(text, x, y);
//
//            g2.setFont(arial_80B);
//            g2.setColor(Color.yellow);
//            text = "Congratulations!";
//            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gp.halfScreenWidth - (textLength/2);
//            y = gp.halfScreenHeight + gp.bigTileSize;
//            g2.drawString(text, x, y);
//
//            gp.gameThread = null;
//
//        }
//        else{
//            //SCORE
//            g2.setFont(arial_40);
//            g2.setColor(Color.white);
//            g2.drawImage(player.lastTreat, gp.smallTileSize, gp.smallTileSize, gp.tileSize, gp.tileSize, null);
//            g2.drawString("x "+gp.player.score, gp.tileSize+gp.smallTileSize, gp.tileSize+(gp.smallTileSize)/2);
//
//            //TIME
//            playTime += (double)1/gp.FPS;
//            g2.drawString("Time: "+dFormat.format(playTime), gp.tileSize * 10, gp.tileSize+(gp.smallTileSize)/2);
//
//        }
    }
    public void drawMessage(){
        //MESSAGE
        if (messageOn){

            int x;

            g2.setFont(g2.getFont().deriveFont(48F));

            x = getXforCenteredText(message);

            g2.drawString(message, x, gp.screenHeight-gp.tileSize);

            messageCounter++;

            if (messageCounter > gp.FPS * 2){
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
    public void drawPlayerLife(){
        int x = gp.smallTileSize;
        int y = gp.smallTileSize;
        int i = 0;

        //DRAW MAX LIFE
        while(i < gp.player.maxLife){
            g2.drawImage(empty_heart, x, y, null);
            i++;
            x += gp.tileSize + gp.smallTileSize;
        }

        //RESET
        x = gp.smallTileSize;
        y = gp.smallTileSize;
        i = 0;

        //DRAW CURRENT LIFE
        while(i < gp.player.life){
            g2.drawImage(heart, x, y, null);
            i++;
            x += gp.tileSize + gp.smallTileSize;
        }
    }
    public void drawPlayerScore(){
        int x = gp.smallTileSize;
        int y = gp.smallTileSize+ gp.tileSize;
        int i = 0;

        //DRAW MAX SCORE
        while(i < gp.player.maxScore/3){
            g2.drawImage(p_empty, x, y, null);
            i++;
            x += gp.tileSize + gp.smallTileSize;
        }

        //RESET
        x = gp.smallTileSize;
        i = 0;

        //DRAW CURRENT SCORE
        while(i < gp.player.score){
            g2.drawImage(p_1, x, y, null);
            i++;
            if (i < gp.player.score){
                g2.drawImage(p_2, x, y, null);
            }
            i++;
            if (i < gp.player.score){
                g2.drawImage(p_3, x, y, null);
            }
            i++;
            x += gp.tileSize + gp.smallTileSize;
        }
    }
    public void drawTitleScreen(){
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.WHITE);
        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Fred's Trick or Treat";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        //SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);
        //MAIN COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //FRED IMAGE
        x = gp.halfScreenWidth - gp.tileSize;
        y = y + gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.bigTileSize, gp.bigTileSize*2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 6;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }

    }
    public void drawGameOverScreen(){

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over!";

        //Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        //Text
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //Restart
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Restart";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">", x -gp.tileSize, y);
            if(gp.keyH.enterPressed){
                gp.playMusic(0);
                gp.restart();
                gp.gameState = gp.playState;
            }
        }


        //Return to title
        text = "Quit to Title";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">", x -gp.tileSize, y);
            if(gp.keyH.enterPressed){
                gp.restart();
                gp.music.stop();
                gp.playMusic(6);
                gp.gameState = gp.titleState;
            }
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 120F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        g2.drawString(text, x, y);

        //WINDOW
        int frameX = gp.halfScreenWidth/2;
        int frameY = gp.bigTileSize*2;
        int width = gp.screenWidth/2;
        int height = gp.screenHeight - gp.bigTileSize*2;
        drawSubWindow(frameX, frameY, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70F));

        switch (subState){
            case 0:
                pauseMain();
                break;
            case 1:
                optionsMain(frameX, frameY);
                break;
            case 2:
                options_FullScreenNotification(frameX, frameY);
                break;
            case 3:
                options_Controls(frameX, frameY);
                break;
            case 4:
                quitConfirmation(frameX, frameY);
                break;
        }
        gp.keyH.enterPressed = false;
    }
    public void pauseMain(){
        String text = "Resume";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        y += gp.tileSize * 6;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">", x -gp.tileSize, y);
            if(gp.keyH.enterPressed){
                gp.gameState = gp.playState;
            }
        }

        text = "Options";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">", x -gp.tileSize, y);
            if(gp.keyH.enterPressed){
                subState = 1;
                commandNum = 0;
            }
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if (commandNum == 2){
            g2.drawString(">", x -gp.tileSize, y);
            if(gp.keyH.enterPressed){
                subState = 4;
                commandNum = 0;
            }
        }
    }
    public void optionsMain(int frameX, int frameY){
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.bigTileSize;
        g2.drawString(text, textX, textY);

        //FULLSCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*3;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX-gp.smallTileSize, textY);
            if(gp.keyH.enterPressed){
                if(!gp.fullScreenOn){
                    gp.fullScreenOn = true;
                }
                else {
                    gp.fullScreenOn = false;
                }
                subState = 2;
            }
        }

        //MUSIC
        textY += gp.bigTileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1){
            g2.drawString(">", textX-gp.smallTileSize, textY);
        }
        //SE
        textY += gp.bigTileSize;
        g2.drawString("Sound Effects", textX, textY);
        if (commandNum == 2){
            g2.drawString(">", textX-gp.smallTileSize, textY);
        }
        //CONTROLS
        textY += gp.bigTileSize;
        g2.drawString("Controls", textX, textY);
        if (commandNum == 3){
            g2.drawString(">", textX-gp.smallTileSize, textY);
            if(gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }
        //BACK
        textY += gp.bigTileSize;
        g2.drawString("Back", textX, textY);
        if (commandNum == 4){
            g2.drawString(">", textX-gp.smallTileSize, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 1;
            }
        }

        //FULLSCREEN CHECKBOX
        textX = frameX + gp.tileSize*11;
        textY= frameY + gp.tileSize*4+gp.smallTileSize/4;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, gp.tileSize, gp.tileSize);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, gp.tileSize, gp.tileSize);
        }

        //MUSIC VOLUME
        textY += gp.bigTileSize;
        g2.drawRect(textX-gp.bigTileSize, textY+gp.smallTileSize/4, gp.screenWidth/6, gp.smallTileSize); //width/5
        int volumeWidth = ((gp.screenWidth/6)/5) * gp.music.volumeScale;
        g2.fillRect(textX-gp.bigTileSize, textY+gp.smallTileSize/4, volumeWidth, gp.smallTileSize); //width/5

        //SE VOLUME
        textY += gp.bigTileSize;
        g2.drawRect(textX-gp.bigTileSize, textY+gp.smallTileSize/4, gp.screenWidth/6, gp.smallTileSize);
        volumeWidth = ((gp.screenWidth/6)/5) * gp.se.volumeScale;
        g2.fillRect(textX-gp.bigTileSize, textY+gp.smallTileSize/4, volumeWidth, gp.smallTileSize);

        gp.config.saveConfig();
    }
    public void options_FullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Changes will take affect on \nrestarting the game.";
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += gp.bigTileSize;
        }
        //BACK
        textY = frameY + gp.bigTileSize*6;
        g2.drawString("Back", textX, textY);
        g2.drawString(">", textX-gp.smallTileSize, textY);
        if(gp.keyH.enterPressed){
            subState = 1;
        }
    }
    public void options_Controls(int frameX, int frameY){
        int textX;
        int textY;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));

        String text = "Controls";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.bigTileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize + gp.smallTileSize;
        g2.drawString("Skate", textX, textY); textY += gp.tileSize + gp.smallTileSize;
        g2.drawString("Attack", textX, textY); textY += gp.tileSize + gp.smallTileSize;
        g2.drawString("Ectoblast", textX, textY); textY += gp.tileSize + gp.smallTileSize;
        g2.drawString("Interact", textX, textY); textY += gp.tileSize + gp.smallTileSize;
        g2.drawString("Exit Dialogue", textX, textY); textY += gp.tileSize  + gp.smallTileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize  + gp.smallTileSize;
        //BACK
        g2.drawString("Back", textX, textY);
        g2.drawString(">", textX-gp.smallTileSize, textY);
        if(gp.keyH.enterPressed){
            subState = 1;
            commandNum = 3;
        }

        textX = frameX + gp.tileSize*12;
        textY = frameY + gp.tileSize*3;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize  + gp.smallTileSize;
        g2.drawString("X", textX, textY); textY += gp.tileSize + gp.smallTileSize;
        g2.drawString("Left Click", textX, textY); textY += gp.tileSize  + gp.smallTileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize  + gp.smallTileSize;
        g2.drawString("E", textX, textY); textY += gp.tileSize  + gp.smallTileSize;
        g2.drawString("Space", textX, textY); textY += gp.tileSize  + gp.smallTileSize;
        g2.drawString("Escape", textX, textY); textY += gp.tileSize  + gp.smallTileSize;
    }
    public void quitConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += gp.tileSize;
        }

        //YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX-gp.smallTileSize, textY);
            if(gp.keyH.enterPressed){
                gp.restart();
                gp.music.stop();
                gp.playMusic(6);
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        //NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1){
            g2.drawString(">", textX-gp.smallTileSize, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 2;
            }
        }

    }
    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.bigTileSize;
        int y = gp.halfScreenHeight + gp.bigTileSize;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize * 5;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += gp.tileSize;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255, 200);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.halfScreenWidth - length/2;
        return x;
    }
}
