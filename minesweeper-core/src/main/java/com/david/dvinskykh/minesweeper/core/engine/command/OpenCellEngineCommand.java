package com.david.dvinskykh.minesweeper.core.engine.command;

import com.david.dvinskykh.minesweeper.core.data.Grid;
import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.engine.GameContext;
import com.david.dvinskykh.minesweeper.core.engine.GameStatus;
import com.david.dvinskykh.minesweeper.core.generator.GridGenerator;

/**
 * Command to open a cell
 * @param coordinate of the cell
 */
public record OpenCellEngineCommand(Coordinate coordinate) implements EngineCommand {

    @Override
    public void execute(GameContext context) {
        Grid grid = context.getGrid();
        if (grid == null) {
            GridGenerator gridGenerator = new GridGenerator(context.getSettings());
            grid = gridGenerator.generateGrid(coordinate);
            context.setGrid(grid);
        }
        context.getGrid().openCell(coordinate);
        if (context.getGrid().isAllCellsOpened()) {
            context.setStatus(GameStatus.WIN);
        }
    }
}
