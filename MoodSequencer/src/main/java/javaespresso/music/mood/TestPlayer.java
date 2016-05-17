package javaespresso.music.mood;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TestPlayer {

    public static void main(String[] args) throws Exception {
        play();
        play();
    }

    private static void play() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //InputStream dataIn = new BufferedInputStream(new FileInputStream("C:\\Users\\ed_ze\\Music\\m01_media_00001.ogg"));
        //AudioInputStream audioIn = AudioSystem.getAudioInputStream(dataIn);

        File file = new File("C:\\Users\\ed_ze\\Music\\m01_media_00001.ogg");
        AudioInputStream in = AudioSystem.getAudioInputStream(file);

        AudioFormat baseFormat = in.getFormat();

        /*
         AudioFormat decodedFormat = new AudioFormat(
         AudioFormat.Encoding.PCM_SIGNED,
         baseFormat.getSampleRate(),
         16,
         baseFormat.getChannels(),
         baseFormat.getChannels() * 2,
         baseFormat.getSampleRate(),
         false);
         */
        AudioFormat decodedFormat = new AudioFormat(baseFormat.getSampleRate(), 16, baseFormat.getChannels(), true, false);
        AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, in);

        //GetLine
        Info info = new Info(SourceDataLine.class, decodedFormat, AudioSystem.NOT_SPECIFIED);
        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(decodedFormat);

        line.start();

        byte[] data = new byte[1024];
        for (int n = 0; n != -1; n = din.read(data, 0, data.length)) {
            line.write(data, 0, n);
        }
        line.drain();

        line.stop();
        line.close();
        din.close();
        in.close();
    }
}
