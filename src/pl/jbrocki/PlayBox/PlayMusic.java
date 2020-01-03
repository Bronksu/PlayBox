package pl.jbrocki.PlayBox;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class PlayMusic {
    public Boolean isPLaying=false;
    private Clip clip;

    public void playMusic(File file) {
        if (isPLaying = false) {
            try {
                clip = AudioSystem.getClip();
                clip.addLineListener(event -> {
                    if (LineEvent.Type.STOP.equals(event.getType())) {
                        clip.close();
                    }
                });
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                clip.open(inputStream);
                clip.start();
                while(clip.isRunning()){
                    isPLaying=true;
                }
                isPLaying = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

