package org.example.btl.game;

import org.example.btl.game.bricks.MapBrick;
import org.example.btl.game.Brick;
import static org.example.btl.Config.*;

public class LevelManager {

    private MapBrick map;
    private int currentLevel;
    private boolean gameWon;

    public LevelManager() {
        this.map = new MapBrick();
        this.currentLevel = 0;
        this.gameWon = false;
    }

    public void loadLevel(int levelNumber) {
        if (levelNumber == 11) {
            map.createBossMap(MAX_WIDTH, MAX_HEIGHT);
            return;
        }

        int[][] layout = MapBrick.loadMap(levelNumber);
        map.createMap(layout, PLAY_AREA_X, PLAY_AREA_Y);
    }

    public void nextLevel() {
        currentLevel++;
        loadLevel(currentLevel);
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public MapBrick getMap() {
        return map;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public boolean isLevelCleared() {
        for (Brick brick : map.getBricks()) {
            if (brick.getBrickType() != 9 && !brick.isDestroyed()) {
                return false;
            }
        }
        return true;
    }
}
