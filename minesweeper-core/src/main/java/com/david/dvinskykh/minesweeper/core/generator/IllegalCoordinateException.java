package com.david.dvinskykh.minesweeper.core.generator;

import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import lombok.Getter;

@Getter
public class IllegalCoordinateException extends RuntimeException {

    private final Coordinate coordinate;

    public IllegalCoordinateException(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
