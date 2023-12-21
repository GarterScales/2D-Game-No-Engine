package Main;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Sound {

    Clip clip;
    public URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {

        newSound(0, "mainTheme");
        newSound(1, "getTreat");
        newSound(2, "getSkateboard");
        newSound(3, "openDoor");
        newSound(4, "funSong");
        newSound(5, "dialogue1");
        newSound(6, "Fred");
        newSound(7, "bat2");
        newSound(8, "hit");
        newSound(9, "attack");
        newSound(10, "ectoblast");
        newSound(11, "chop");
        newSound(12, "menu");
        newSound(13, "gameOver");

    }

    private void newSound(int index, String soundName){

        soundURL[index] = getClass().getResource("/sound/"+soundName+".wav");

    }

    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        }catch(Exception e) {

        }
    }
    public void play() {
        clip.start();

    }
    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }
    public void stop() {

        clip.stop();

    }
    public void checkVolume(){
        switch (volumeScale){
            case 0: volume = -80F; break;
            case 1: volume = -20F; break;
            case 2: volume = -12F; break;
            case 3: volume = -5F; break;
            case 4: volume = 1F; break;
            case 5: volume = 6F; break;
        }
        fc.setValue(volume);
    }

}
