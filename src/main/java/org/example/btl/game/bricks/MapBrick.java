package org.example.btl.game.bricks;

import javafx.scene.canvas.GraphicsContext;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.example.btl.game.Brick;
import java.util.ArrayList;
import java.util.List;



public class MapBrick {

    private List<Brick> bricks;
    private int type;

    public static final int BRICK_WIDTH = 32;
    public static final int BRICK_HEIGHT = 16;

    public MapBrick() {
        bricks = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = 0;
    }

    public void createMap(int[][] mapLayout) {
        bricks.clear();

        for (int row = 0; row < mapLayout.length; row++) {
            for (int col = 0; col < mapLayout[row].length; col++) {
                int brickType = mapLayout[row][col];

                if (brickType > 0) {
                    double brickX = col * BRICK_WIDTH;
                    double brickY = row * BRICK_HEIGHT;

                    Brick brick = new Brick(brickX, brickY,BRICK_WIDTH, BRICK_HEIGHT, brickType);
                    bricks.add(brick);
                }
            }
        }
    }
    public void render(GraphicsContext gc) {
        for (Brick brick : bricks) {
            brick.render(gc);
        }
    }
    public void update() {
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public static int[][] loadMap(String filePath) {
        List<List<Integer>> mapList = new ArrayList<>();

        try (InputStream is = MapBrick.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                System.err.println("Không tìm thấy file map tại: " + filePath);
                return null;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                // Bỏ qua các dòng trống
                if (line.trim().isEmpty()) {
                    continue;
                }

                List<Integer> row = new ArrayList<>();
                // Tách dòng thành các chuỗi số dựa trên một hoặc nhiều dấu cách
                String[] numbers = line.trim().split("\\s+");

                for (String numberStr : numbers) {
                    row.add(Integer.parseInt(numberStr));
                }
                mapList.add(row);
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi đọc file map: " + filePath);
            e.printStackTrace();
            return null;
        }

        // Chuyển từ List<List<Integer>> sang mảng int[][]
        if (mapList.isEmpty()) {
            return new int[0][0];
        }

        int numRows = mapList.size();
        int numCols = mapList.get(0).size();
        int[][] mapArray = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                mapArray[i][j] = mapList.get(i).get(j);
            }
        }

        return mapArray;
    }
}

