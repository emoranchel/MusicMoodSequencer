package javaespresso.music.mood;

public class TestPlayer {

    public static void main(String[] args) throws Exception {
        AudioPlayer track1 = new AudioPlayer("D:\\temp\\m01_media_00001.ogg");
        AudioPlayer track2 = new AudioPlayer("D:\\temp\\m01_media_00001.ogg");
        track1.play();
        track2.play();
    }

}
