package com.david.dvinskykh.minesweeper.core.engine;

import com.david.dvinskykh.minesweeper.core.engine.command.EngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.GameEngineCommand;
import com.david.dvinskykh.minesweeper.core.exception.MineCellOpenException;
import com.david.dvinskykh.minesweeper.core.ui.UITemplate;

public class GameEngine {

    private final UITemplate ui;
    private GameContext context;

    public GameEngine(UITemplate ui) {
        this.ui = ui;
    }

    public void start() {
        GameSettings settings = ui.startGame();

        do {
            ui.initializeUI(settings);
            context = new GameContext(settings);

            mainLoop();

            if (context.getStatus() == GameStatus.WIN) {
                GameEngineCommand gameEngineCommand = ui.playerWin();
                gameEngineCommand.execute(context);
            } else if (context.getStatus() == GameStatus.LOST){
                GameEngineCommand gameEngineCommand = ui.playerLost();
                gameEngineCommand.execute(context);
            }
        } while (context.getStatus() != GameStatus.END);

        ui.endGame();
    }

    private void mainLoop() {
        while (context.getStatus() == GameStatus.IN_PROGRESS) {
            EngineCommand engineCommand = ui.nextCommand(context);
            try {
                engineCommand.execute(context);
            } catch (MineCellOpenException exception) {
                GameEngineCommand gameEngineCommand = ui.playerOpenMineCell();
                gameEngineCommand.execute(context);
            }
            ui.updateUI(context);
        }
    }
}
