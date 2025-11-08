package org.example.btl.game.sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

public class MusicManager {

    private static MediaPlayer currentPlayer;

    public static void playMusic(String fileName, boolean loop) {
        stopMusic();
        try {
            Media media = new Media(Objects.requireNonNull(
                    MusicManager.class.getResource("/org/example/btl/M&S/" + fileName)
            ).toExternalForm());

            currentPlayer = new MediaPlayer(media);
            currentPlayer.setVolume(0.6);

            if (loop) {
                currentPlayer.setOnEndOfMedia(() -> currentPlayer.seek(Duration.ZERO));
            }

            currentPlayer.play();

        } catch (Exception e) {
            System.err.println("Không thể phát nhạc: " + fileName);
            e.printStackTrace();
        }
    }

    /** Tạm dừng nhạc nền */
    public static void pauseMusic() {
        if (currentPlayer != null) {
            currentPlayer.pause();
        }
    }

    /** Tiếp tục nhạc nền */
    public static void resumeMusic() {
        if (currentPlayer != null) {
            currentPlayer.play();
        }
    }

    /** Dừng nhạc nền và giải phóng tài nguyên */
    public static void stopMusic() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer.dispose();
            currentPlayer = null;
        }
    }
}
