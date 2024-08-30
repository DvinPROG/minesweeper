package com.david.dvinskykh.minesweeper.console;

import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import com.david.dvinskykh.minesweeper.core.engine.GameStatus;
import com.david.dvinskykh.minesweeper.core.engine.command.EngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.FlagCellEngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.GameEngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.OpenCellEngineCommand;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;

import static com.david.dvinskykh.minesweeper.console.Messages.COMMAND_IS_WRONG;
import static com.david.dvinskykh.minesweeper.console.Messages.WRONG_DATA_FOR_OPEN_MINE;
import static com.david.dvinskykh.minesweeper.console.Patterns.ENGINE_COMMAND_GROUP;

public class CommandFactory {

    private final GameSettings settings;
    private final PatternScanner patternScanner;

    private final Map<String, Function<Matcher, EngineCommand>> commands = Map.of(
            "o", this::openCell,
            "f", this::flagCell,
            "r", t -> restartGame(),
            "c", t -> continueGame(),
            "q", t -> endGame()
    );

    private final Map<String, Supplier<GameEngineCommand>> gameEngineCommands = Map.of(
            "r", this::restartGame,
            "c", this::continueGame,
            "q", this::endGame
    );

    public CommandFactory(GameSettings settings, PatternScanner patternScanner) {
        this.settings = settings;
        this.patternScanner = patternScanner;
    }

    private GameEngineCommand endGame() {
        return new GameEngineCommand(GameStatus.END);
    }

    private GameEngineCommand continueGame() {
        return new GameEngineCommand(GameStatus.IN_PROGRESS);
    }

    private GameEngineCommand restartGame() {
        return new GameEngineCommand(GameStatus.RESTART);
    }

    private OpenCellEngineCommand openCell(Matcher matcher) {
        Coordinate coordinate = getCoordinate(matcher);
        return new OpenCellEngineCommand(coordinate);
    }

    private Coordinate getCoordinate(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Coordinate coordinate = new Coordinate(x, y);
        if (!coordinate.isValid(settings)) {
            throw new IllegalArgumentException("The coordinate is invalid");
        }
        return coordinate;
    }

    private FlagCellEngineCommand flagCell(Matcher matcher) {
        Coordinate coordinate = getCoordinate(matcher);
        return new FlagCellEngineCommand(coordinate);
    }

    public EngineCommand engineCommand() {
        EngineCommand engineCommand = null;
        do {
            String result = patternScanner.read(Patterns.ENGINE_COMMAND_PATTERN, COMMAND_IS_WRONG);
            Matcher matcher = Patterns.ENGINE_COMMAND_PATTERN.matcher(result);
            if (!matcher.matches()) {
                System.out.println(COMMAND_IS_WRONG);
                continue;
            }
            Function<Matcher, EngineCommand> function = commands.get(matcher.group(ENGINE_COMMAND_GROUP).toLowerCase());
            if (function == null) {
                System.out.println(COMMAND_IS_WRONG);
                continue;
            }
            try {
                engineCommand = function.apply(matcher);
            } catch (Exception exception) {
                System.out.println(COMMAND_IS_WRONG);
            }
        } while (engineCommand == null);

        return engineCommand;
    }

    public GameEngineCommand gameEngineCommand() {
        String result = patternScanner.read(Patterns.GAME_ENGINE_COMMAND_PATTERN, WRONG_DATA_FOR_OPEN_MINE);
        Supplier<GameEngineCommand> supplier = gameEngineCommands.get(result.toLowerCase());
        return supplier.get();
    }
}
