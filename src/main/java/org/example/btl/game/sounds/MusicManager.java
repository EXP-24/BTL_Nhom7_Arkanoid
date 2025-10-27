package org.example.btl.game.sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;

public class MusicManager {

    private static MediaPlayer mediaPlayer;

    // 🔹 Đường dẫn nhạc nền
    private static final String MENU_MUSIC_PATH = "/org/example/btl/M&S/menu.mp3";
    private static final String GAME_MUSIC_PATH = "/org/example/btl/M&S/game.mp3";
    private static final String PAUSE_MUSIC_PATH = "/org/example/btl/M&S/pause.mp3";

    // 🔹 Phát nhạc (chung)
    private static void playMusic(String path, boolean loop) {
        stopMusic(); // dừng nhạc cũ nếu đang phát
        try {
            Media media = new Media(Objects.requireNonNull(MusicManager.class.getResource(path)).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.6); // âm lượng 60%
            if (loop) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // phát lặp
            }
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Không thể phát nhạc: " + path);
            e.printStackTrace();
        }
    }

    // 🔹 Phát nhạc menu
    public static void playMenuMusic() {
        playMusic(MENU_MUSIC_PATH, true);
    }

    // 🔹 Phát nhạc khi chơi game
    public static void playGameMusic() {
        playMusic(GAME_MUSIC_PATH, true);
    }

    // 🔹 Phát nhạc khi tạm dừng game
    public static void playPauseMusic() {
        playMusic(PAUSE_MUSIC_PATH, true);
    }

    // 🔹 Tạm dừng nhạc hiện tại
    public static void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    // 🔹 Tiếp tục phát nhạc
    public static void resumeMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    // 🔹 Dừng hoàn toàn nhạc hiện tại
    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}
