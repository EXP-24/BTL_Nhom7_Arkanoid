package org.example.btl.lives;

import java.util.ArrayList;
import java.util.List;

import static org.example.btl.GameApplication.*;

public class LifeManage {
    private List<Life> liveIcons;
    private int lives;

    public LifeManage(int lives) {
        liveIcons = new ArrayList<>();
        this.lives = lives;
        liveGenerator();
    }

    private void liveGenerator() {
        liveIcons.clear();
        for (int i = 0; i < this.lives; i++) {
            double iconWidth = 32;
            double iconHeight = 32;
            double spacing = 5;
            double iconX = PLAY_AREA_X - 32 + (i + 1) * (iconWidth + spacing);
            double iconY = PLAY_AREA_HEIGHT + 36;

            liveIcons.add(new Life(iconX, iconY, iconWidth, iconHeight));
        }
    }

    public List<Life> getLiveIcons() {
        return liveIcons;
    }

    public int getLives() {
        return lives;
    }
}
