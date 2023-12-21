package Main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import object_interactive.InteractiveObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int smallTileSize = tileSize /2;
    public final int bigTileSize = tileSize * 2;
    public int maxScreenCol = 32;
    public int maxScreenRow = 18;
    public final int screenWidth = (tileSize * maxScreenCol);
    public final int screenHeight = (tileSize * maxScreenRow);
    public final int halfScreenWidth = screenWidth/2;
    public final int halfScreenHeight = screenHeight/2;
    final int billion = 1000000000;

    // WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;

    ///FOR FULL SCREEN
    public boolean fullScreenOn = false;
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    // FPS
    int FPS = 60;

    //SYSTEM
    public TileManager tileM = new TileManager(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[] obj = new Entity[100];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    public InteractiveObject[] iObj = new InteractiveObject[10];
    public Entity[] projectile = new Entity[20];
//    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int gameOverState = 4;

    //UI
    public UI ui = new UI(this, player);

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setInteractiveObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(6);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if(fullScreenOn){
            setFullScreen();
        }
    }
    //CAN ALSO HAVE A RETRY METHOD
    public void restart(){
        player.setDefaultValues();
        aSetter.setObject();
        aSetter.setInteractiveObject();
        aSetter.setNPC();
        aSetter.setMonster();
    }
    public void setFullScreen(){
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //GET FULL SCREEN AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        double drawInterval = (double)billion/FPS;
        double delta=0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }

            if (timer >= billion){
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        if (gameState == playState){
            //PLAYER
            player.update();
            //NPC
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null && !monster[i].dying){
                    if(monster[i].alive){
                        monster[i].update();
                    }
                    if(!monster[i].alive){
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for(int i = 0; i < projectile.length; i++){
                if(projectile[i] != null) {
                    if(projectile[i].alive){
                        projectile[i].update();
                    }
                    if(!projectile[i].alive){
                        projectile[i] = null;
                    }
                }
            }
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive){
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive){
                        particleList.remove(i);
                    }
                }
            }
            for(int i = 0; i < iObj.length; i++){
                if(iObj[i] != null && !iObj[i].dying){
                    iObj[i].update();
                }
            }
        }
        if (gameState == pauseState){
            //nothing
        }
    }
    public void drawToTempScreen(){
        //DEBUG
        long drawStart = 0;
        if(keyH.debugOn){
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        //OTHER STATES
        else{
            //TILE
            tileM.draw(g2);

            //ADD ENTITIES TO LIST
            entityList.add(player);

            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < iObj.length; i++){
                if(iObj[i] != null){
                    entityList.add(iObj[i]);
                }
            }

            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            for(int i = 0; i < projectile.length; i++){
                if(projectile[i] != null){
                    entityList.add(projectile[i]);
                }
            }
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            //SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY+e.solidArea.height));

            //DRAW ENTITIES
            for(int i = 0; i < entityList.size(); i++){
                if(entityList.get(i) == player){
                    player.draw(g2, keyH);
                }
                else{
                    entityList.get(i).draw(g2, keyH);
                }
            }

            //EMPTY LIST
            entityList.clear();

            //UI
            ui.draw(g2);
        }

        //DEBUG
        if(keyH.debugOn) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            int x = smallTileSize;
            int y = halfScreenHeight-smallTileSize;
            int lineHeight = tileSize;

            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));

            g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
            g2.drawString("Draw Time: " + passed, x, y);
            System.out.println("Draw Time: " + passed);
        }
    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2,  null);
        g.dispose();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
