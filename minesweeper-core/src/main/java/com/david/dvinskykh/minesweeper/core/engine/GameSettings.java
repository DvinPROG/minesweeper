package com.david.dvinskykh.minesweeper.core.engine;

import com.david.dvinskykh.minesweeper.core.GameComplexity;

public record GameSettings(int width, int height, GameComplexity complexity) {
}
