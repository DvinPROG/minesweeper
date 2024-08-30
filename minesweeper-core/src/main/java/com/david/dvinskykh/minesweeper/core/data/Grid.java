package com.david.dvinskykh.minesweeper.core.data;

import com.david.dvinskykh.minesweeper.core.CellType;
import com.david.dvinskykh.minesweeper.core.exception.MineCellOpenException;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Grid {
    private final Cell[][] grid;
    private final GameSettings settings;
    private final int totalMines;
    private int openedCells;
    private int openedMines;

    public Grid(CellType[][] initialGrid, GameSettings settings) {
        this.settings = settings;
        this.grid = new Cell[settings.height()][settings.width()];
        this.openedCells = 0;

        int minesCount = 0;
        for (int y = 0; y < settings.height(); y++) {
            for (int x = 0; x < settings.width(); x++) {
                Coordinate coordinate = new Coordinate(x, y);
                CellType cellType = initialGrid[y][x];
                if (cellType == CellType.MINE) {
                    minesCount++;
                }
                this.grid[y][x] = new Cell(cellType, getMineCount(settings, initialGrid, coordinate));
            }
        }

        this.totalMines = minesCount;
    }

    public void flagCell(Coordinate coordinate) {
        Cell cell = getCell(coordinate);
        cell.flag();
    }

    public void openCell(Coordinate coordinate) {
        Cell cell = getCell(coordinate);
        if (cell.open()) {
            openedCells++;
            if (cell.mineAroundCount == 0) {
                openRelativeCells(coordinate);
            }
        }
    }

    private int getMineCount(GameSettings settings, CellType[][] initialGrid, Coordinate initialCoordinate) {
        int count = 0;
        for (CellRelationship value : CellRelationship.values()) {
            Coordinate relativeCoordinate = value.getRelativeCoordinate(settings, initialCoordinate.x(), initialCoordinate.y());
            if (relativeCoordinate != null) {
                CellType relativeCell = initialGrid[relativeCoordinate.y()][relativeCoordinate.x()];
                if (relativeCell == CellType.MINE) {
                    count++;
                }
            }
        }
        return count;
    }

    public Cell getCell(Coordinate relativeCoordinate) {
        return grid[relativeCoordinate.y()][relativeCoordinate.x()];
    }

    public boolean isAllCellsOpened() {
        return totalMines + openedCells == settings.width() * settings.height();
    }

    private void openRelativeCells(Coordinate coordinate) {
        Arrays.stream(CellRelationship.values())
                .map(cellRelationship -> cellRelationship.getRelativeCoordinate(settings, coordinate))
                .filter(Objects::nonNull)
                .forEach(this::openCell);
    }

    @Getter
    public static class Cell {
        private final CellType type;
        private final int mineAroundCount;
        private CellVisionState cellVisionState;

        private Cell(CellType type, int mineAroundCount) {
            this.type = requireNonNull(type);
            this.mineAroundCount = mineAroundCount;
            this.cellVisionState = CellVisionState.HIDDEN;
        }

        private boolean open() {
            if (cellVisionState != CellVisionState.HIDDEN) {
                return false;
            }
            cellVisionState = CellVisionState.OPENED;

            if (type == CellType.MINE) {

                throw new MineCellOpenException();
            }

            return true;
        }

        private void flag() {
            if (cellVisionState == CellVisionState.HIDDEN) {
                cellVisionState = CellVisionState.FLAGGED;
            } else if (cellVisionState == CellVisionState.FLAGGED) {
                cellVisionState = CellVisionState.HIDDEN;
            }
        }
    }
}
