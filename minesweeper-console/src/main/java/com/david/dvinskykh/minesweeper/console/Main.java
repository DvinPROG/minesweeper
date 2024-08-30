package com.david.dvinskykh.minesweeper.console;

import com.david.dvinskykh.minesweeper.core.engine.GameEngine;
import com.david.dvinskykh.minesweeper.core.ui.UITemplate;

public class Main {
    public static void main(String[] args) {
        UITemplate uiTemplate = new ConsoleUI();

        GameEngine gameEngine = new GameEngine(uiTemplate);
        gameEngine.start();
    }
}