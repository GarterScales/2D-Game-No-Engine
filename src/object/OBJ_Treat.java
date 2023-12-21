package object;

import Main.GamePanel;
import entity.Entity;
import java.util.Random;

public class OBJ_Treat extends Entity {


    public OBJ_Treat(GamePanel gp){

        super(gp);

        solidAreaDefaultX = gp.smallTileSize/2;
        solidAreaDefaultY = gp.smallTileSize/2;
        solidArea.width = gp.smallTileSize;
        solidArea.height = gp.smallTileSize;
        solidArea.x = solidAreaDefaultX;
        solidArea.y = solidAreaDefaultY;

        name = "treat";

        Random r = new Random();

        int type = r.nextInt(4);

        switch (type) {
            case 0 -> {
                nickName = "Chocolate";
                down1 = setUp("/objects/chocolate");
            }
            case 1 -> {
                nickName = "Lollipop";
                down1 = setUp("/objects/lollipop");
            }
            case 2 -> {
                nickName = "Candy Bar";
                down1 = setUp("/objects/pinkbar");
            }
            case 3 -> {
                nickName = "Strawberry Candy";
                down1 = setUp("/objects/redcandy");
            }
        }

    }

}
