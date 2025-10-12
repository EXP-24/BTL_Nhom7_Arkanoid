package org.example.btl.game.bricks;

import javafx.scene.canvas.GraphicsContext;
import org.example.btl.game.Brick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapBrick {

    private List<Brick> bricks;
    private int mapLevel;

    public static final int BRICK_WIDTH = 32;
    public static final int BRICK_HEIGHT = 16;

    public MapBrick() {
        bricks = new ArrayList<>();
    }

    public void setMapLevel(int level) {
        this.mapLevel = level;
    }

    public int getMapLevel() {
        return mapLevel;
    }

    public void createMap(int[][] mapLayout, double offsetX, double offsetY) {
        bricks.clear();

        for (int row = 0; row < mapLayout.length; row++) {
            for (int col = 0; col < mapLayout[row].length; col++) {
                int brickType = mapLayout[row][col];

                if (brickType > 0) {
                    double brickX = offsetX + col * BRICK_WIDTH;
                    double brickY = offsetY + row * BRICK_HEIGHT;

                    Brick brick = new Brick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT, brickType);
                    bricks.add(brick);
                }
            }
        }
    }

    public void update() {

    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public static int[][] loadMap(String filePath) {
        List<int[]> mapList = new ArrayList<>();

        try (InputStream is = MapBrick.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                System.err.println("Không tìm thấy file map tại: " + filePath);
                return new int[0][0]; // Trả về mảng trống
            }

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] numbers = line.split("\\s+");
                int[] row = new int[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    row[i] = Integer.parseInt(numbers[i]);
                }
                mapList.add(row);
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println("Lỗi khi đọc file map: " + filePath);
            e.printStackTrace();
            return new int[0][0]; // Trả về mảng trống khi có lỗi
        }

        // Chuyển List<int[]> thành int[][]
        return mapList.toArray(new int[0][]);
    }
}