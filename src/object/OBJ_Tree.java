package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Tree extends Entity {

    public OBJ_Tree(GamePanel gp){

        super(gp);

        name = "Tree";

        solidAreaDefaultY = gp.tileSize;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidArea.y = solidAreaDefaultY;

        collision = true;

        down1 = setUp("/objects/tree", gp.tileSize, gp.bigTileSize);
    }

}