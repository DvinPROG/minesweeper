package com.david.dvinskykh.minesweeper.core.data;

import com.david.dvinskykh.minesweeper.core.engine.GameSettings;

public record Coordinate(int x, int y) {

    public boolean isValid(GameSettings settings) {
        return x >= 0 && y >= 0 && x < settings.width() && y < settings.height();
    }
}
