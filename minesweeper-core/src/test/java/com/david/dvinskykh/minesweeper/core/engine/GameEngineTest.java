package com.david.dvinskykh.minesweeper.core.engine;

import com.david.dvinskykh.minesweeper.core.CellType;
import com.david.dvinskykh.minesweeper.core.GameComplexity;
import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.data.Grid;
import com.david.dvinskykh.minesweeper.core.engine.command.EngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.FlagCellEngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.GameEngineCommand;
import com.david.dvinskykh.minesweeper.core.engine.command.OpenCellEngineCommand;
import com.david.dvinskykh.minesweeper.core.ui.UITemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.david.dvinskykh.minesweeper.core.CellType.EMPTY;
import static com.david.dvinskykh.minesweeper.core.CellType.MINE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameEngineTest {

    private final CellType[][] initialGrid = new CellType[][]{
            {MINE, MINE, EMPTY, EMPTY},

            {MINE, MINE, EMPTY, EMPTY},

            {EMPTY, EMPTY, EMPTY, EMPTY},

            {EMPTY, EMPTY, MINE, MINE}};
    private final OpenCellEngineCommand FIRST_EMPTY_CELL = new OpenCellEngineCommand(new Coordinate(3, 0));
    private final Coordinate FIRST_MINE_CELL = new Coordinate(0, 0);
    private final Coordinate SECONG_EMPTY_CELL = new Coordinate(0, 3);
    private final Coordinate SECOND_MINE_CELL = new Coordinate(1, 0);
    @Mock
    private UITemplate uiTemplate;
    @InjectMocks
    private GameEngine gameEngine;

    @Test
    void testStartPlayerWin() {
        GameSettings settings = new GameSettings(4, 4, GameComplexity.LOW);
        when(uiTemplate.startGame()).thenReturn(settings);

        Grid grid = new Grid(initialGrid, settings);

        EngineCommand firstGameCommand = context -> {
            context.setGrid(grid);
            FIRST_EMPTY_CELL.execute(context);
        };
        EngineCommand secondGameCommand = new OpenCellEngineCommand(FIRST_MINE_CELL);
        EngineCommand thirdGameCommand = new OpenCellEngineCommand(SECONG_EMPTY_CELL);

        when(uiTemplate.nextCommand(any()))
                .thenReturn(firstGameCommand, secondGameCommand, thirdGameCommand);

        when(uiTemplate.playerOpenMineCell()).thenReturn(new GameEngineCommand(GameStatus.IN_PROGRESS));

        when(uiTemplate.playerWin()).thenReturn(new GameEngineCommand(GameStatus.END));

        gameEngine.start();

        InOrder inOrder = Mockito.inOrder(uiTemplate);

        inOrder.verify(uiTemplate).startGame();
        inOrder.verify(uiTemplate).initializeUI(settings);

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).playerOpenMineCell();
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());
        inOrder.verify(uiTemplate).playerWin();
        inOrder.verify(uiTemplate).endGame();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void testStartPlayerLost() {
        GameSettings settings = new GameSettings(4, 4, GameComplexity.LOW);
        when(uiTemplate.startGame()).thenReturn(settings);
        when(uiTemplate.nextCommand(any()))
                .thenReturn(context -> {
                    context.setGrid(new Grid(initialGrid, settings));
                    FIRST_EMPTY_CELL.execute(context);
                }, new OpenCellEngineCommand(FIRST_MINE_CELL), new OpenCellEngineCommand(SECONG_EMPTY_CELL));

        when(uiTemplate.playerOpenMineCell()).thenReturn(new GameEngineCommand(GameStatus.LOST));

        when(uiTemplate.playerLost()).thenReturn(new GameEngineCommand(GameStatus.END));

        gameEngine.start();

        InOrder inOrder = Mockito.inOrder(uiTemplate);

        inOrder.verify(uiTemplate).startGame();
        inOrder.verify(uiTemplate).initializeUI(settings);

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).playerOpenMineCell();
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).playerLost();
        inOrder.verify(uiTemplate).endGame();

        inOrder.verifyNoMoreInteractions();
    }


    @Test
    void testStartPlayerRunNewGame() {
        GameSettings settings = new GameSettings(4, 4, GameComplexity.LOW);
        when(uiTemplate.startGame()).thenReturn(settings);
        when(uiTemplate.nextCommand(any()))
                .thenReturn(
                        context -> {
                            context.setGrid(new Grid(initialGrid, settings));
                            FIRST_EMPTY_CELL.execute(context);
                        },
                        new GameEngineCommand(GameStatus.RESTART),
                        context -> {
                            context.setGrid(new Grid(initialGrid, settings));
                            FIRST_EMPTY_CELL.execute(context);
                        },
                        new OpenCellEngineCommand(FIRST_MINE_CELL),
                        new FlagCellEngineCommand(SECOND_MINE_CELL),
                        new OpenCellEngineCommand(SECONG_EMPTY_CELL));

        when(uiTemplate.playerOpenMineCell()).thenReturn(new GameEngineCommand(GameStatus.IN_PROGRESS));

        when(uiTemplate.playerWin()).thenReturn(new GameEngineCommand(GameStatus.END));

        gameEngine.start();

        InOrder inOrder = Mockito.inOrder(uiTemplate);

        inOrder.verify(uiTemplate).startGame();
        inOrder.verify(uiTemplate).initializeUI(settings);

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).initializeUI(settings);
        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).playerOpenMineCell();
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).playerWin();
        inOrder.verify(uiTemplate).endGame();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void testStartPlayerRunNewGameAfterLost() {
        GameSettings settings = new GameSettings(4, 4, GameComplexity.LOW);
        when(uiTemplate.startGame()).thenReturn(settings);
        when(uiTemplate.nextCommand(any()))
                .thenReturn(
                        context -> {
                            context.setGrid(new Grid(initialGrid, settings));
                            FIRST_EMPTY_CELL.execute(context);
                        },
                        new OpenCellEngineCommand(SECONG_EMPTY_CELL),
                        context -> {
                            context.setGrid(new Grid(initialGrid, settings));
                            FIRST_EMPTY_CELL.execute(context);
                        },
                        new FlagCellEngineCommand(SECOND_MINE_CELL),
                        new OpenCellEngineCommand(FIRST_MINE_CELL));

        when(uiTemplate.playerOpenMineCell()).thenReturn(new GameEngineCommand(GameStatus.LOST));

        when(uiTemplate.playerWin()).thenReturn(new GameEngineCommand(GameStatus.RESTART));
        when(uiTemplate.playerLost()).thenReturn(new GameEngineCommand(GameStatus.END));

        gameEngine.start();

        InOrder inOrder = Mockito.inOrder(uiTemplate);

        inOrder.verify(uiTemplate).startGame();
        inOrder.verify(uiTemplate).initializeUI(settings);

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).playerWin();

        inOrder.verify(uiTemplate).initializeUI(settings);

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).nextCommand(any());
        inOrder.verify(uiTemplate).updateUI(any());

        inOrder.verify(uiTemplate).playerLost();

        inOrder.verify(uiTemplate).endGame();

        inOrder.verifyNoMoreInteractions();
    }
}