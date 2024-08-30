package com.david.dvinskykh.minesweeper.core.engine;

import com.david.dvinskykh.minesweeper.core.data.Grid;
import lombok.Data;

@Data
public class GameContext {
    private final GameSettings settings;
    private Grid grid;
    private GameStatus status;

    public GameContext(GameSettings settings) {
        this.settings = settings;
        status = GameStatus.IN_PROGRESS;
    }
}
