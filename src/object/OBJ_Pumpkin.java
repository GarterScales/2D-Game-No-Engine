package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Pumpkin extends Entity {

    public OBJ_Pumpkin(GamePanel gp){

        super(gp);

        name = "Pumpkin";

        image = setUp("/objects/p_empty");
        image2 = setUp("/objects/p_1");
        image3 = setUp("/objects/p_2");
        image4 = setUp("/objects/p_3");

    }
}
