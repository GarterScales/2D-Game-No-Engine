package object_interactive;

import Main.GamePanel;

public class IO_Trunk extends InteractiveObject {

    GamePanel gp;

    public IO_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;

        down1 = setUp("/objects/trunk");

        solidArea.height = 0;
        solidArea.width = 0;

    }

}
