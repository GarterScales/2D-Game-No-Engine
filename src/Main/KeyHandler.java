package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, rideSkateboard, debugOn, ePressed, mouse1Pressed, shootKeyPressed, enterPressed;

    GamePanel gp;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //TITLE STATE
        if(gp.gameState == gp.titleState){
            titleState(code);
        }

        //PLAYSTATE
        else if(gp.gameState == gp.playState){
            playState(code);
        }
        //PAUSE STATE
        else if(gp.gameState == gp.pauseState){
            pauseState(code);
        }
        //DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            dialogueState(code);
        }
        //GAME OVER STATE
        else if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
    }

    public void titleState(int code){
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 2;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.stopMusic();
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 1){
                //ADD LATER
            }
            if(gp.ui.commandNum == 2){
                System.exit(0);
            }
        }
    }
    public void playState(int code){
        //MOVEMENT
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        //SKATEBOARD
        if (code == KeyEvent.VK_X){
            if (!rideSkateboard && gp.player.hasSkateboard) {
                rideSkateboard = true;
            }
            else {
                rideSkateboard = false;
            }
        }
        //ENTER DIALOGUE
        if(code == KeyEvent.VK_E) {
            ePressed = true;
        }
        //PAUSE
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }
        //PROJECTILE
        if(code == KeyEvent.VK_F){
            shootKeyPressed = true;
        }

        //DEBUG
        if (code == KeyEvent.VK_BACK_QUOTE){
            if (!debugOn){
                debugOn = true;
            }
            else {
                debugOn = false;
            }

        }
        //RELOAD MAP
        if(debugOn && code == KeyEvent.VK_R){
            gp.tileM.loadMap("/maps/editorWorld01.txt");
        }
    }
    public void pauseState(int code){
        //PLAY
        if(code == KeyEvent.VK_ESCAPE) {
            gp.ui.commandNum = 0;
            if(gp.ui.subState == 0){
                gp.gameState = gp.playState;
            }
            else if(gp.ui.subState == 1 || gp.ui.subState == 4){
                gp.ui.subState = 0;
            }
            else{
                gp.ui.subState = 1;
            }
        }
        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0:
                maxCommandNum = 2;
                break;
            case 1:
                maxCommandNum = 4;
                break;
            case 4:
                maxCommandNum = 1;
        }
        if(code == KeyEvent.VK_W){
//            gp.playSE(12);
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S){
//            gp.playSE(12);
            gp.ui.commandNum++;
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.subState == 1){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0){
                    gp.playSE(12);
                    gp.se.volumeScale--;
                }
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 1){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5){
                    gp.playSE(12);
                    gp.se.volumeScale++;
                }
            }
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }
    public void dialogueState(int code){
        //PLAY
        if(code == KeyEvent.VK_SPACE) {
            gp.gameState = gp.playState;
        }
    }
    public void gameOverState(int code){
        if(code == KeyEvent.VK_W){
//            gp.playSE(12);
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_S){
//            gp.playSE(12);
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F){
            shootKeyPressed = false;//maybe change this to charge up bigger blast
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
    }

    public void mousePressed(MouseEvent e){
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if(code == MouseEvent.BUTTON1){
            mouse1Pressed = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
