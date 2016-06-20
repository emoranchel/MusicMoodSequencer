/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaespresso.music.mood;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author ed_ze
 */
public class AudioPlayer {

    private ExecutorService closeWorker = Executors.newSingleThreadExecutor();

    private final SourceDataLine line;
    private final AudioInputStream din;
    private final AudioInputStream in;

    public AudioPlayer(String fileName) throws Exception {
        File file = new File(fileName);
        this.in = AudioSystem.getAudioInputStream(file);

        AudioFormat baseFormat = in.getFormat();

        AudioFormat decodedFormat = new AudioFormat(baseFormat.getSampleRate(), 16, baseFormat.getChannels(), true, false);
        this.din = AudioSystem.getAudioInputStream(decodedFormat, in);

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat, AudioSystem.NOT_SPECIFIED);
        this.line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(decodedFormat);

        line.start();
    }

    public void play() throws Exception {
        byte[] data = new byte[1024];
        for (int n = 0; n != -1; n = din.read(data, 0, data.length)) {
            line.write(data, 0, n);
        }
        line.drain();
        closeWorker.submit(() -> {
            try {
                line.stop();
                line.close();
                din.close();
                in.close();
            } catch (Exception e) {
            }
        });

    }
}
