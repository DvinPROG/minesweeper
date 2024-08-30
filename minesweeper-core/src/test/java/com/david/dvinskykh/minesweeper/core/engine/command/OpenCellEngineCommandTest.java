package com.david.dvinskykh.minesweeper.core.engine.command;

import com.david.dvinskykh.minesweeper.core.GameComplexity;
import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.data.Grid;
import com.david.dvinskykh.minesweeper.core.engine.GameContext;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import org.junit.jupiter.api.Test;

import static com.david.dvinskykh.minesweeper.core.data.CellVisionState.OPENED;
import static com.david.dvinskykh.minesweeper.core.engine.GameStatus.WIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OpenCellEngineCommandTest {

    @Test
    void testExecuteNullGrid() {
        Coordinate startCoordinate = new Coordinate(0, 0);
        GameSettings settings = new GameSettings(4, 4, GameComplexity.LOW);

        OpenCellEngineCommand openCellEngineCommand = new OpenCellEngineCommand(startCoordinate);
        GameContext context = new GameContext(settings);
        openCellEngineCommand.execute(context);

        assertThat(context.getGrid())
                .isNotNull();
        assertThat(context.getGrid().getCell(startCoordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(OPENED);
    }

    @Test
    void testExecuteAndAllCellsOpened() {
        Coordinate startCoordinate = new Coordinate(0, 0);
        Grid grid = mock(Grid.class);
        when(grid.isAllCellsOpened()).thenReturn(true);
        GameSettings settings = new GameSettings(4, 4, GameComplexity.LOW);

        OpenCellEngineCommand openCellEngineCommand = new OpenCellEngineCommand(startCoordinate);
        GameContext context = new GameContext(settings);
        context.setGrid(grid);
        openCellEngineCommand.execute(context);

        verify(grid).openCell(startCoordinate);
        assertThat(context.getStatus())
                .isEqualTo(WIN);
    }
}