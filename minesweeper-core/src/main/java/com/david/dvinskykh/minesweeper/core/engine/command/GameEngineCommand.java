package com.david.dvinskykh.minesweeper.core.engine.command;

import com.david.dvinskykh.minesweeper.core.engine.GameContext;
import com.david.dvinskykh.minesweeper.core.engine.GameStatus;

public record GameEngineCommand(GameStatus gameStatus) implements EngineCommand {
    @Override
    public void execute(GameContext context) {
        context.setStatus(gameStatus);
    }
}
