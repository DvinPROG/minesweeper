package com.david.dvinskykh.minesweeper.core.generator;

import com.david.dvinskykh.minesweeper.core.CellType;
import com.david.dvinskykh.minesweeper.core.data.Grid;
import com.david.dvinskykh.minesweeper.core.data.CellRelationship;
import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GridGenerator {

    private final GameSettings settings;
    private final Random random;

    public GridGenerator(GameSettings settings) {
        this.settings = settings;
        this.random = new Random();
    }

    GridGenerator(GameSettings settings, Random random) {
        this.settings = settings;
        this.random = random;
    }

    public Grid generateGrid(Coordinate startCoordinate) {
        if (startCoordinate == null || !startCoordinate.isValid(settings)) {
            throw new IllegalCoordinateException(startCoordinate);
        }

        Set<Coordinate> emptyCoordinates = getMandatoryEmptyCells(startCoordinate);
        double complexityCoefficient = settings.complexity().getCoefficient();
        int totalCells = settings.width() * settings.height();
        CellType[][] cells = new CellType[settings.height()][settings.width()];
        for (int y = 0; y < settings.height(); y++) {
            for (int x = 0; x < settings.width(); x++) {
                if (emptyCoordinates.contains(new Coordinate(x, y))) {
                    cells[y][x] = CellType.EMPTY;
                } else {
                    int rand = random.nextInt(totalCells);
                    if (rand < complexityCoefficient * totalCells) {
                        cells[y][x] = CellType.MINE;
                    } else {
                        cells[y][x] = CellType.EMPTY;
                    }
                }
            }
        }
        return new Grid(cells, settings);
    }

    private Set<Coordinate> getMandatoryEmptyCells(Coordinate startCoordinate) {
        Set<Coordinate> emptyCoordinates = new HashSet<>();
        emptyCoordinates.add(startCoordinate);
        Arrays.stream(CellRelationship.values())
                .map(cellRelationship -> cellRelationship.getRelativeCoordinate(settings, startCoordinate))
                .forEach(emptyCoordinates::add);
        return emptyCoordinates;
    }
}
