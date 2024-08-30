package com.david.dvinskykh.minesweeper.console;

import com.acidmanic.consoletools.table.Table;
import com.acidmanic.consoletools.table.builders.TableBuilder;
import com.david.dvinskykh.minesweeper.core.CellType;
import com.david.dvinskykh.minesweeper.core.data.CellVisionState;
import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.data.Grid;
import com.david.dvinskykh.minesweeper.core.engine.GameContext;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;

import java.util.function.Function;

public class GridPrinter {

    public void printEmptyGrid(GameSettings settings) {
        printGrid(settings, coordinate -> "");
    }

    public void printGrid(GameContext context) {
        printGrid(context.getSettings(), coordinate -> {
            Grid.Cell cellType = context.getGrid().getCell(coordinate);
            if (cellType.getCellVisionState() == CellVisionState.FLAGGED) {
                return "#";
            } else if (cellType.getCellVisionState() == CellVisionState.OPENED) {
                if (cellType.getType() == CellType.MINE) {
                    return "%";
                } else if (cellType.getType() == CellType.EMPTY) {
                    return String.valueOf(cellType.getMineAroundCount());
                }
            }
            return "";
        });
    }

    private void printGrid(GameSettings settings, Function<Coordinate, String> dataExtractor) {
        Table table = new TableBuilder()
                .row().cell((TableBuilder builder) -> {
                    builder.cell("y\\x");
                    for (int x = 0; x < settings.width(); x++) {
                        builder.cell(String.valueOf(x)).padding(3, 0);
                    }
                    for (int y = 0; y < settings.height(); y++) {
                        builder.row()
                                .cell(String.valueOf(y))
                                .padding(2, 1);
                        for (int x = 0; x < settings.width(); x++) {
                            builder
                                    .cell(dataExtractor.apply(new Coordinate(x, y)))
                                    .border().padding(2, 0);
                        }
                    }
                    builder.tableBorder();
                }).build();
        System.out.println(table.render());
    }
}
