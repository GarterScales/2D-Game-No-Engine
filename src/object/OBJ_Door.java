package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Door extends Entity{

    public OBJ_Door(GamePanel gp){
        super(gp);

        name = "Door";
        solidArea.width = gp.bigTileSize;
        solidArea.height = gp.bigTileSize;
        collision = true;

        down1 = setUp("/objects/bigdoor", gp.bigTileSize, gp.bigTileSize);
    }

}
