package org.example.btl.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreManagerTest {

    private static final String SCORE_FILE_PATH = System.getProperty("user.home") + "/arkanoid_scores.txt";
    private Path scorePath;

    private ScoreManager scoreManager;

    @BeforeEach
    void setUp() throws IOException {
        // Xóa file điểm cũ
        this.scorePath = Paths.get(SCORE_FILE_PATH);
        Files.deleteIfExists(scorePath);

        // Khởi tạo ScoreManager
        this.scoreManager = new ScoreManager();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Dọn dẹp file sau khi test xong
        Files.deleteIfExists(scorePath);
    }

    private void writeFakeScores(List<String> lines) throws IOException {
        Files.write(scorePath, lines);
    }

    @Test
    void testTrangThaiKhoiTao() {
        assertNotNull(scoreManager);
        assertEquals(0, scoreManager.getScore(), "Điểm ban đầu phải là 0");
        assertEquals(0, scoreManager.getTopScore(), "Điểm cao nhất ban đầu phải là 0");
        assertTrue(scoreManager.getScoreBoard().isEmpty(), "Bảng điểm ban đầu phải rỗng");
    }

    @Test
    void testAddScore() {
        scoreManager.addScore(100);
        assertEquals(100, scoreManager.getScore());

        scoreManager.addScore(50);
        assertEquals(150, scoreManager.getScore());
    }

    @Test
    void testAddScoreCapNhatTopScoreTrongBoNho() {
        scoreManager.addScore(100);
        // topScore được cập nhật
        assertEquals(100, scoreManager.getTopScore(), "Top score (memory) nên được cập nhật");

        scoreManager.addScore(50); // Tổng 150
        assertEquals(150, scoreManager.getTopScore());
    }

    @Test
    void testResetScore() {
        scoreManager.addScore(500);
        assertEquals(500, scoreManager.getScore());
        assertEquals(500, scoreManager.getTopScore());

        scoreManager.resetScore();
        assertEquals(0, scoreManager.getScore(), "Điểm hiện tại phải reset về 0");
        assertEquals(500, scoreManager.getTopScore(), "Top score không bị ảnh hưởng bởi resetScore");
    }

    @Test
    void testLuuDiemVaTaiLai() {
        scoreManager.addScore(200);
        scoreManager.saveCurrentScoreToBoard();

        // Kiểm tra trạng thái hiện tại
        assertEquals(200, scoreManager.getTopScore());
        assertEquals(1, scoreManager.getScoreBoard().size());
        assertEquals(200, scoreManager.getScoreBoard().get(0));

        //Giai đoạn 2: Tải lại
        ScoreManager newScoreManager = new ScoreManager();

        assertEquals(200, newScoreManager.getTopScore(), "Phải tải được top score từ file");
        assertEquals(1, newScoreManager.getScoreBoard().size(), "Phải tải được bảng điểm từ file");
        assertEquals(200, newScoreManager.getScoreBoard().get(0));
    }

    @Test
    void testLuuDiemVaSapXep() {
        scoreManager.addScore(100);
        scoreManager.saveCurrentScoreToBoard();
        scoreManager.resetScore();

        scoreManager.addScore(300);
        scoreManager.saveCurrentScoreToBoard();
        scoreManager.resetScore();

        scoreManager.addScore(200);
        scoreManager.saveCurrentScoreToBoard();

        // Bảng điểm phải được sắp xếp giảm dần
        List<Integer> scoreBoard = scoreManager.getScoreBoard();
        assertEquals(3, scoreBoard.size());
        assertEquals(300, scoreBoard.get(0));
        assertEquals(200, scoreBoard.get(1));
        assertEquals(100, scoreBoard.get(2));

        // Top score phải là 300
        assertEquals(300, scoreManager.getTopScore());
    }

    @Test
    void testLuuDiemVuotQuaGioiHan() {
        for (int i = 1; i <= 12; i++) {
            scoreManager.addScore(i * 10);
            scoreManager.saveCurrentScoreToBoard();
            scoreManager.resetScore();
        }

        List<Integer> scoreBoard = scoreManager.getScoreBoard();
        assertEquals(10, scoreBoard.size(), "Bảng điểm chỉ nên giữ 10 điểm cao nhất");
        assertEquals(120, scoreBoard.get(0), "Điểm cao nhất phải là 120");
        assertEquals(30, scoreBoard.get(9), "Điểm thấp nhất trong top 10 phải là 30");
        assertFalse(scoreBoard.contains(20), "Điểm 20 không được có trong bảng");
        assertFalse(scoreBoard.contains(10), "Điểm 10 không được có trong bảng");
    }

    @Test
    void testKhongLuuDiemTrungLap() {
        scoreManager.addScore(100);
        scoreManager.saveCurrentScoreToBoard();
        scoreManager.resetScore();

        scoreManager.addScore(100);
        scoreManager.saveCurrentScoreToBoard();

        assertEquals(1, scoreManager.getScoreBoard().size(), "Không nên lưu điểm trùng lặp");
    }

    @Test
    void testKhongLuuDiemBangKhong() {
        scoreManager.saveCurrentScoreToBoard(); // score đang là 0
        assertTrue(scoreManager.getScoreBoard().isEmpty(), "Không nên lưu điểm 0");
    }

    @Test
    void testTaiDiemTuFileCoSan() throws IOException {
        // Tạo file điểm "giả"
        writeFakeScores(List.of("500", "150", "20"));
        ScoreManager sm = new ScoreManager();

        assertEquals(500, sm.getTopScore(), "Phải đọc được top score 500");
        assertEquals(3, sm.getScoreBoard().size());
        assertEquals(150, sm.getScoreBoard().get(1));
    }

    @Test
    void testTaiDiemTuFileLoi() throws IOException {
        writeFakeScores(List.of("500", "abc", "20"));

        ScoreManager sm = new ScoreManager();
        assertEquals(0, sm.getTopScore(), "Top score phải là 0 vì file lỗi");
        assertTrue(sm.getScoreBoard().isEmpty(), "Bảng điểm phải rỗng vì file lỗi");
    }
}