package com.david.dvinskykh.minesweeper.core.data;

import com.david.dvinskykh.minesweeper.core.CellType;
import com.david.dvinskykh.minesweeper.core.exception.MineCellOpenException;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.david.dvinskykh.minesweeper.core.CellType.EMPTY;
import static com.david.dvinskykh.minesweeper.core.CellType.MINE;
import static com.david.dvinskykh.minesweeper.core.data.CellVisionState.FLAGGED;
import static com.david.dvinskykh.minesweeper.core.data.CellVisionState.HIDDEN;
import static com.david.dvinskykh.minesweeper.core.data.CellVisionState.OPENED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GridTest {

    private final GameSettings settings = new GameSettings(4, 4, null);

    private final CellType[][] initialGrid = new CellType[][]{
            {MINE, MINE, EMPTY, EMPTY},

            {MINE, MINE, EMPTY, EMPTY},

            {EMPTY, MINE, EMPTY, EMPTY},

            {EMPTY, EMPTY, MINE, EMPTY}};

    private final int[][] mineCount = new int[][]{
            {3, 3, 2, 0},
            {4, 4, 3, 0},
            {3, 3, 3, 1},
            {1, 2, 1, 1}};

    @Test
    void testGridConstructor() {
        Grid grid = new Grid(initialGrid, settings);

        for (int y = 0; y < settings.height(); y++) {
            for (int x = 0; x < settings.width(); x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Grid.Cell cell = grid.getCell(coordinate);
                assertThat(cell)
                        .as(coordinate.toString())
                        .hasFieldOrPropertyWithValue("type", initialGrid[y][x])
                        .hasFieldOrPropertyWithValue("mineAroundCount", mineCount[y][x])
                        .hasFieldOrPropertyWithValue("cellVisionState", HIDDEN);
            }
        }
        assertThat(grid.isAllCellsOpened())
                .isEqualTo(false);
    }

    @Test
    void testFlagCell() {
        Grid grid = new Grid(initialGrid, settings);

        Coordinate coordinate = new Coordinate(0, 0);
        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(HIDDEN);

        grid.flagCell(coordinate);

        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(FLAGGED);
    }

    @Test
    void testUnFlagCell() {
        Grid grid = new Grid(initialGrid, settings);

        Coordinate coordinate = new Coordinate(0, 0);
        grid.flagCell(coordinate);
        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(FLAGGED);

        grid.flagCell(coordinate);

        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(HIDDEN);
    }

    @Test
    void testFlagOpenedCell() {
        Grid grid = new Grid(initialGrid, settings);

        Coordinate coordinate = new Coordinate(2, 0);
        grid.openCell(coordinate);
        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(OPENED);

        grid.flagCell(coordinate);

        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(OPENED);
    }

    @Test
    void testOpenCell() {
        Grid grid = new Grid(initialGrid, settings);

        Coordinate coordinate = new Coordinate(2, 0);
        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(HIDDEN);

        grid.openCell(coordinate);

        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(OPENED);
    }

    @Test
    void testOpenFlaggedCell() {
        Grid grid = new Grid(initialGrid, settings);

        Coordinate coordinate = new Coordinate(2, 0);
        grid.flagCell(coordinate);
        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(FLAGGED);

        grid.openCell(coordinate);

        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(FLAGGED);
    }

    @Test
    void testOpenMineCell() {
        Grid grid = new Grid(initialGrid, settings);

        Coordinate coordinate = new Coordinate(0, 0);
        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(HIDDEN);

        assertThatThrownBy(() -> grid.openCell(coordinate))
                .isInstanceOf(MineCellOpenException.class);

        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(OPENED);
    }

    @Test
    void testOpenEmptyCellWithZeroMineAroundCount() {
        Grid grid = new Grid(initialGrid, settings);

        Coordinate coordinate = new Coordinate(3, 1);

        grid.flagCell(new Coordinate(2, 2));
        assertThat(grid.getCell(coordinate))
                .extracting(Grid.Cell::getCellVisionState)
                .isEqualTo(HIDDEN);

        grid.openCell(coordinate);

        CellVisionState[][] expectedStates = new CellVisionState[][]{
                {HIDDEN, HIDDEN, OPENED, OPENED},

                {HIDDEN, HIDDEN, OPENED, OPENED},

                {HIDDEN, HIDDEN, FLAGGED, OPENED},

                {HIDDEN, HIDDEN, HIDDEN, HIDDEN}};

        for (int y = 0; y < settings.height(); y++) {
            for (int x = 0; x < settings.width(); x++) {
                assertThat(grid.getCell(new Coordinate(x, y)))
                        .extracting(Grid.Cell::getCellVisionState)
                        .isEqualTo(expectedStates[y][x]);
            }
        }
    }

    @Test
    void testIsAllCellsOpened() {
        Grid grid = new Grid(initialGrid, settings);

        List<Coordinate> openCells = Arrays.asList(
                new Coordinate(3,1),
                new Coordinate(0,2),
                new Coordinate(1, 1),
                new Coordinate(0,3),
                new Coordinate(1,3)
        );

        for (Coordinate openCell : openCells) {
            assertThat(grid.isAllCellsOpened())
                    .isEqualTo(false);
            try {
                 grid.openCell(openCell);
            } catch (MineCellOpenException ignore) {}
        }

        assertThat(grid.isAllCellsOpened())
                .isEqualTo(false);
    }
}