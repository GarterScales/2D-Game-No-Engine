package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Skateboard extends Entity {

    public OBJ_Skateboard(GamePanel gp){

        super(gp);

        name = "Skateboard";

        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;

        down1 = setUp("/objects/skateboard");

    }

}