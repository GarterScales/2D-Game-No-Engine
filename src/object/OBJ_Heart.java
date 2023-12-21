package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp){

        super(gp);

        name = "Heart";

        image = setUp("/objects/heart");
        image2 = setUp("/objects/empty_heart");

    }
}
