package com.david.dvinskykh.minesweeper.core.engine.command;

import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.data.Grid;
import com.david.dvinskykh.minesweeper.core.engine.GameContext;

public record FlagCellEngineCommand(Coordinate coordinate) implements EngineCommand {
    @Override
    public void execute(GameContext context) {
        Grid grid = context.getGrid();
        grid.flagCell(coordinate);
    }
}
