package com.david.dvinskykh.minesweeper.core.data;

import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import lombok.Getter;

@Getter
public enum CellRelationship {
    UPPER_LEFT(-1, -1), UPPER(0, -1), UPPER_RIGHT(1, -1),
    LEFT(-1, 0), RIGHT(1, 0),
    LOWER_LEFT(-1, 1), LOWER(0, 1), LOWER_RIGHT(1, 1);

    private final int xCoefficient;
    private final int yCoefficient;

    CellRelationship(int x, int y) {
        this.xCoefficient = x;
        this.yCoefficient = y;
    }

    public Coordinate getRelativeCoordinate(GameSettings settings, int x, int y) {
        int resultX = x + xCoefficient;
        int resultY = y + yCoefficient;
        if (resultX < 0 || resultY < 0 || resultX >= settings.width() || resultY >= settings.height()) {
            return null;
        }
        return new Coordinate(resultX, resultY);
    }

    public Coordinate getRelativeCoordinate(GameSettings settings, Coordinate coordinate) {
        return getRelativeCoordinate(settings, coordinate.x(),  coordinate.y());
    }
}
