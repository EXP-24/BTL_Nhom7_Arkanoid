package org.example.btl.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {

    private int score;
    private int topScore;
    private List<Integer> scoreBoard;

    private static final String SCORE_FILE = System.getProperty("user.home") + "/arkanoid_scores.txt";
    private static final int MAX_SCORES_ON_BOARD = 10;

    public ScoreManager() {
        this.score = 0;
        this.scoreBoard = new ArrayList<>();
        loadScoresFromFile();
    }

    private void loadScoresFromFile() {
        scoreBoard.clear();
        try {
            Path path = Paths.get(SCORE_FILE);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        scoreBoard.add(Integer.parseInt(line.trim()));
                    }
                }
            }
            updateTopScore();
        } catch (IOException | NumberFormatException e) {
            System.err.println("Lỗi khi tải điểm: " + e.getMessage());
            this.topScore = 0;
            this.scoreBoard.clear();
        }
    }

    private void saveScoreBoardToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SCORE_FILE))) {
            for (Integer s : scoreBoard) {
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu điểm: " + e.getMessage());
        }
    }

    public void addScore(int amount) {
        this.score += amount;
        if (this.score > this.topScore) {
            this.topScore = this.score;
        }
    }

    public void saveCurrentScoreToBoard() {
        // chỉ thêm nếu khác 0
        if (this.score > 0 && !scoreBoard.contains(this.score)) {
            scoreBoard.add(this.score);
        }

        // sắp xếp giảm dần
        scoreBoard.sort(Collections.reverseOrder());

        // giữ lại tối đa 10 điểm cao nhất
        if (scoreBoard.size() > MAX_SCORES_ON_BOARD) {
            scoreBoard = new ArrayList<>(scoreBoard.subList(0, MAX_SCORES_ON_BOARD));
        }

        saveScoreBoardToFile();
        updateTopScore();
    }

    private void updateTopScore() {
        if (scoreBoard.isEmpty()) {
            topScore = 0;
        } else {
            topScore = scoreBoard.get(0);
        }
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getTopScore() {
        return topScore;
    }

    public List<Integer> getScoreBoard() {
        return Collections.unmodifiableList(scoreBoard);
    }
}