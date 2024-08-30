package com.david.dvinskykh.minesweeper.console;

import com.david.dvinskykh.minesweeper.core.GameComplexity;
import com.david.dvinskykh.minesweeper.core.engine.GameContext;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import com.david.dvinskykh.minesweeper.core.engine.command.EngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.GameEngineCommand;
import com.david.dvinskykh.minesweeper.core.ui.UITemplate;

import java.util.regex.Matcher;

import static com.david.dvinskykh.minesweeper.console.Messages.GAME_OVER;
import static com.david.dvinskykh.minesweeper.console.Messages.GREETING_MESSAGE;
import static com.david.dvinskykh.minesweeper.console.Messages.OPEN_MINE;
import static com.david.dvinskykh.minesweeper.console.Messages.RESTART_GAME;
import static com.david.dvinskykh.minesweeper.console.Messages.WRONG_DATA_FOR_GREETING;
import static com.david.dvinskykh.minesweeper.console.Messages.YOU_LOST;
import static com.david.dvinskykh.minesweeper.console.Messages.YOU_WIN;

public class ConsoleUI implements UITemplate {
    private final static String WIDTH_GROUP_NAME = "width";
    private final static String HEIGHT_GROUP_NAME = "height";
    private final static String COMPLEXITY_GROUP_NAME = "complexity";

    private final PatternScanner patternScanner;
    private final GridPrinter gridPrinter;
    private CommandFactory commandFactory;

    public ConsoleUI() {
        patternScanner = new PatternScanner(System.in, System.out);
        gridPrinter = new GridPrinter();
    }

    @Override
    public GameSettings startGame() {
        GameSettings settings = null;
        System.out.println(GREETING_MESSAGE);
        do {
            String result = patternScanner.read(Patterns.GAME_SETTINGS_PATTERN, WRONG_DATA_FOR_GREETING);
            Matcher matcher = Patterns.GAME_SETTINGS_PATTERN.matcher(result);
            if (!matcher.matches()) {
                System.out.println(WRONG_DATA_FOR_GREETING);
                continue;
            }
            int width = Integer.parseInt(matcher.group(WIDTH_GROUP_NAME));
            int height = Integer.parseInt(matcher.group(HEIGHT_GROUP_NAME));
            GameComplexity complexity = GameComplexity.valueOf(matcher.group(COMPLEXITY_GROUP_NAME));
            settings = new GameSettings(width, height, complexity);
        } while (settings == null);
        commandFactory = new CommandFactory(settings, patternScanner);
        return settings;
    }

    public EngineCommand nextCommand(GameContext context) {
        return new CommandFactory(context.getSettings(), patternScanner).engineCommand();
    }

    @Override
    public void updateUI(GameContext context) {
        gridPrinter.printGrid(context);
    }

    @Override
    public void endGame() {
        System.out.println(GAME_OVER);
    }

    @Override
    public void initializeUI(GameSettings settings) {
        gridPrinter.printEmptyGrid(settings);
    }

    @Override
    public GameEngineCommand playerOpenMineCell() {
        System.out.println(OPEN_MINE);
        return commandFactory.gameEngineCommand();
    }

    @Override
    public GameEngineCommand playerWin() {
        System.out.println(YOU_WIN);
        System.out.println(RESTART_GAME);
        return commandFactory.gameEngineCommand();
    }

    @Override
    public GameEngineCommand playerLost() {
        System.out.println(YOU_LOST);
        System.out.println(RESTART_GAME);
        return commandFactory.gameEngineCommand();
    }
}
