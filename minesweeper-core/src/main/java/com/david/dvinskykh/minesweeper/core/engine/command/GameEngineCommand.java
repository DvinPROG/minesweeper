package com.david.dvinskykh.minesweeper.core.engine.command;

import com.david.dvinskykh.minesweeper.core.engine.GameContext;
import com.david.dvinskykh.minesweeper.core.engine.GameStatus;

/**
 * Command that change the current status of the game
 * @param gameStatus - a new status of the game
 */
public record GameEngineCommand(GameStatus gameStatus) implements EngineCommand {
    @Override
    public void execute(GameContext context) {
        context.setStatus(gameStatus);
    }
}
