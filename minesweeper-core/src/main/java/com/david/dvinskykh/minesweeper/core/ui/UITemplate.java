package com.david.dvinskykh.minesweeper.core.ui;

import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import com.david.dvinskykh.minesweeper.core.engine.command.EngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.GameEngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.GameContext;

/**
 * Common template for UI implementation
 */
public interface UITemplate {

    /**
     * Starts the game and taking game settings from a player
     * @return
     */
    GameSettings startGame();

    /**
     * Takes command from a player
     * @param context with grid and grid settings
     * @return player's command
     */
    EngineCommand nextCommand(GameContext context);

    /**
     * Creates grid on UI with specified width and height
     * @param settings with specified width and height
     */
    void initializeUI(GameSettings settings);

    /**
     * Updates grid on UI
     * @param context with grid and grid settings
     */
    void updateUI(GameContext context);

    /**
     * Notifies player that the cell with a mine was opened
     * @return command for continue game, restart game or etc.
     */
    GameEngineCommand playerOpenMineCell();

    /**
     * Notifies player that the player wins
     * @return command for restart game or quit game
     */
    GameEngineCommand playerWin();

    /**
     * Notifies player that the player lost
     * @return command for restart game or quit game
     */
    GameEngineCommand playerLost();

    /**
     * Notifies player that the game is end.
     */
    void endGame();
}


