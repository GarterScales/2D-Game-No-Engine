package object_interactive;

import Main.GamePanel;
import entity.Entity;

public class InteractiveObject extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public InteractiveObject(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public void update(){
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 20){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public InteractiveObject getDestroyedForm(){
        InteractiveObject object = null;
        return object;
    }
}
