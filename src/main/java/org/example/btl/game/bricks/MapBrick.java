package org.example.btl.game.bricks;

import org.example.btl.game.Brick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapBrick {

    private List<Brick> bricks;
    private int mapLevel;
    private Random randomGenerator;


    public static final int BRICK_WIDTH = 32;
    public static final int BRICK_HEIGHT = 16;


    public MapBrick() {

        bricks = new ArrayList<>();
        randomGenerator = new Random();
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

                if (brickType > 0 ) {
                    double brickX = offsetX + col * BRICK_WIDTH;
                    double brickY = offsetY + row * BRICK_HEIGHT;

                    int powerUpType = 0;

                    if (brickType == 2) {
                        int numberOfPowerUpType = 5;
                        powerUpType = randomGenerator.nextInt(numberOfPowerUpType) + 1;
                    }

                    Brick brick = new Brick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT, brickType, powerUpType);
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

    // Trong file MapBrick.java
    public static int[][] loadMap(int level) {

        List<int[]> mapList = new ArrayList<>();
        String filePath = "/org/example/btl/Map/Map" + level + ".txt"; // Đường dẫn này CÓ VẺ ĐÚNG
        InputStream is = null;

        try {
            is = MapBrick.class.getResourceAsStream(filePath);

            if (is == null) {
                System.err.println("Không tìm thấy file map tại: " + filePath);
                return null; // <--- SỬA 1: Trả về null
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
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
            }

        } catch (IOException | NumberFormatException | NullPointerException e) { // Bắt cả NullPointer (nếu is null)
            System.err.println("Lỗi khi đọc file map: " + filePath);
            e.printStackTrace();
            return null; // <--- SỬA 2: Trả về null
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // THÊM KIỂM TRA NÀY: Nếu file có tồn tại nhưng trống rỗng
        if (mapList.isEmpty()) {
            System.err.println("File map rỗng: " + filePath);
            return null; // <--- SỬA 3: Trả về null
        }

        // Chuyển List<int[]> thành int[][]
        return mapList.toArray(new int[0][]);
    }
}