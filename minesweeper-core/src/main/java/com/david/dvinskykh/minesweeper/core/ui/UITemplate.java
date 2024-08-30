package com.david.dvinskykh.minesweeper.core.ui;

import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import com.david.dvinskykh.minesweeper.core.engine.command.EngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.GameEngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.GameContext;

public interface UITemplate {

    GameSettings startGame();

    EngineCommand nextCommand(GameContext context);

    void initializeUI(GameSettings context);

    void updateUI(GameContext context);

    GameEngineCommand playerOpenMineCell();

    GameEngineCommand playerWin();

    GameEngineCommand playerLost();

    void endGame();
}


