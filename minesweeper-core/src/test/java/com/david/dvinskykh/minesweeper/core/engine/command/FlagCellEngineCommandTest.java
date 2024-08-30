package com.david.dvinskykh.minesweeper.core.engine.command;

import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.data.Grid;
import com.david.dvinskykh.minesweeper.core.engine.GameContext;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FlagCellEngineCommandTest {
    @Test
    void testExecuteNullGrid() {
        Coordinate startCoordinate = new Coordinate(0, 0);

        FlagCellEngineCommand flagCellEngineCommand = new FlagCellEngineCommand(startCoordinate);
        Grid grid = mock(Grid.class);
        GameContext context = new GameContext(mock(GameSettings.class));
        context.setGrid(grid);
        flagCellEngineCommand.execute(context);

        verify(grid).flagCell(startCoordinate);
    }
}