//package object;
//
//import Main.GamePanel;
//import Main.KeyHandler;
//import Main.UtilityTool;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//public class SuperObject {
//
//    KeyHandler keyH;
//
//    public BufferedImage image, image2, image3, image4;
//    public String name, nickName;
//    public boolean collision = false;
//    public int worldX, worldY;
//    public Rectangle solidArea = new Rectangle(0,0, 48, 48);
//    public int solidAreaDefaultX = 0;
//    public int solidAreaDefaultY = 0;
//    UtilityTool uTool = new UtilityTool();
//
//
//    public void draw(Graphics2D g2, GamePanel gp, KeyHandler keyH) {
//
//        int screenX = worldX - gp.player.worldX + gp.player.screenX;
//        int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//        if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX - gp.bigTileSize &&
//                worldX - gp.tileSize< gp.player.worldX + gp.player.screenX + gp.bigTileSize &&
//                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY - gp.bigTileSize &&
//                worldY - gp.tileSize< gp.player.worldY + gp.player.screenY + gp.bigTileSize) {
//
//            g2.drawImage(image, screenX, screenY, null);
//
//            if (keyH.debugOn){
//                g2.setColor(Color.RED);
//                g2.drawRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);
//            }
//
//        }
//
//    }
//
//}
