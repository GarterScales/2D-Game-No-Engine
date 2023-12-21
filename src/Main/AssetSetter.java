package Main;

import entity.NPC_RSG;
import monster.MON_Bat;
import object.OBJ_Door;
import object.OBJ_Skateboard;
import object.OBJ_Treat;
import object.OBJ_Tree;
import object_interactive.IO_DryShrub;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setInteractiveObject(){

        int i = 0;
        gp.iObj[i] = new IO_DryShrub(gp, 71, 60);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 71, 66);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 78, 69);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 78, 70);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 78, 71);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 77, 71);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 77, 72);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 77, 73);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 77, 74);
        i++;
        gp.iObj[i] = new IO_DryShrub(gp, 77, 75);
        i++;

        //editorMap01
//        int i = 0;
//        gp.iObj[i] = new IO_DryShrub(gp, 4, 8);
//        i++;
//        gp.iObj[i] = new IO_DryShrub(gp, 4, 7);
//        i++;
//        gp.iObj[i] = new IO_DryShrub(gp, 4, 6);
//        i++;
//        gp.iObj[i] = new IO_DryShrub(gp, 4, 5);
    }

    public void setObject() {

        int i = 0;
        newObject(i, "door", 24, 31); i++;
        newObject(i, "door", 50, 31); i++;
        newObject(i, "door", 68, 31); i++;
        newObject(i, "door", 54, 47); i++;
        newObject(i, "treat", 50, 32); i++;
        newObject(i, "treat", 68, 32); i++;
        newObject(i, "treat", 54, 49); i++;
        newObject(i, "treat", 36, 42); i++;
        newObject(i, "treat", 72, 42); i++;
        newObject(i, "treat", 28, 56); i++;
        newObject(i, "treat", 24, 33); i++;
        newObject(i, "treat", 51, 33); i++;
        newObject(i, "treat", 68, 33); i++;
        newObject(i, "treat", 48, 56); i++;
        newObject(i, "treat", 29, 72); i++;
        newObject(i, "treat", 62, 59); i++;
        newObject(i, "treat", 62, 65); i++;
        newObject(i, "treat", 67, 76); i++;
        newObject(i, "treat", 79, 79); i++;
        newObject(i, "skateboard", 40, 40);

        //editorMap01
//        int listLength = 7;
//        int temp = 0;
//
//        newObject(0, "treat", 6, 20);
//        newObject(1, "treat", 32, 20);
//        newObject(2, "treat", 22, 29);
//        newObject(3, "treat", 40, 29);
//        newObject(4, "door", 6, 18);
//        newObject(5, "door", 32, 18);
//        newObject(6, "skateboard", 23, 24);
//
//        for(int i = listLength; i < 50+listLength-8; i++){
//            if(i != 29-listLength && i != 30-listLength){
//                newObject(i, "tree", 0, i - listLength + 8);
//                temp++;
//            }
//        }
//        for(int i = listLength; i < 50+listLength-8; i++){
//            if(i != 29-listLength && i != 30-listLength){
//                newObject(i+temp, "tree", 49, i - listLength + 8);
//            }
//        }

    }

    public void setNPC(){

        gp.npc[0] = new NPC_RSG(gp);
        gp.npc[0].worldX = gp.tileSize * 76;
        gp.npc[0].worldY = gp.tileSize * 32;

//        editorMap01
//        gp.npc[0] = new NPC_RSG(gp);
//        gp.npc[0].worldX = gp.tileSize * 20;
//        gp.npc[0].worldY = gp.tileSize * 20;
    }

    public void setMonster(){

        gp.monster[0] = new MON_Bat(gp);
        gp.monster[0].worldX = gp.tileSize * 75;
        gp.monster[0].worldY = gp.tileSize * 62;

        gp.monster[1] = new MON_Bat(gp);
        gp.monster[1].worldX = gp.tileSize * 77;
        gp.monster[1].worldY = gp.tileSize * 61;

        gp.monster[2] = new MON_Bat(gp);
        gp.monster[2].worldX = gp.tileSize * 63;
        gp.monster[2].worldY = gp.tileSize * 84;

        gp.monster[3] = new MON_Bat(gp);
        gp.monster[3].worldX = gp.tileSize * 62;
        gp.monster[3].worldY = gp.tileSize * 85;

//        editorMap01
//        gp.monster[0] = new MON_Bat(gp);
//        gp.monster[0].worldX = gp.tileSize * 5;
//        gp.monster[0].worldY = gp.tileSize * 22;
//
//        gp.monster[1] = new MON_Bat(gp);
//        gp.monster[1].worldX = gp.tileSize * 8;
//        gp.monster[1].worldY = gp.tileSize * 25;
    }

    private void newObject(int index, String name, double col, double row ){

        switch (name) {
            case "treat":
                gp.obj[index] = new OBJ_Treat(gp);
                gp.obj[index].worldX = (int)(col * gp.tileSize);
                gp.obj[index].worldY = (int)(row * gp.tileSize);
                break;
            case "door":
                gp.obj[index] = new OBJ_Door(gp);
                gp.obj[index].worldX = (int)(col * gp.tileSize);
                gp.obj[index].worldY = (int)(row * gp.tileSize);
                break;
            case "skateboard":
                gp.obj[index] = new OBJ_Skateboard(gp);
                gp.obj[index].worldX = (int)(col * gp.tileSize);
                gp.obj[index].worldY = (int)(row * gp.tileSize);
                break;
            case "tree":
                gp.obj[index] = new OBJ_Tree(gp);
                gp.obj[index].worldX = (int)(col * gp.tileSize);
                gp.obj[index].worldY = (int)(row * gp.tileSize);
                break;

        }

    }

}
