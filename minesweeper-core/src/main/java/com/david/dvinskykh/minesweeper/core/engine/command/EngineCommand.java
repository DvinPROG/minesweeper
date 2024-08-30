package com.david.dvinskykh.minesweeper.core.engine.command;

import com.david.dvinskykh.minesweeper.core.engine.GameContext;

public interface EngineCommand {

    void execute(GameContext context);
}
