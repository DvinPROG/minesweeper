package com.david.dvinskykh.minesweeper.core.data;

import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static com.david.dvinskykh.minesweeper.core.data.CellRelationship.LEFT;
import static com.david.dvinskykh.minesweeper.core.data.CellRelationship.LOWER;
import static com.david.dvinskykh.minesweeper.core.data.CellRelationship.LOWER_LEFT;
import static com.david.dvinskykh.minesweeper.core.data.CellRelationship.LOWER_RIGHT;
import static com.david.dvinskykh.minesweeper.core.data.CellRelationship.RIGHT;
import static com.david.dvinskykh.minesweeper.core.data.CellRelationship.UPPER;
import static com.david.dvinskykh.minesweeper.core.data.CellRelationship.UPPER_LEFT;
import static com.david.dvinskykh.minesweeper.core.data.CellRelationship.UPPER_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class CellRelationshipTest {

    private static final Coordinate UPPER_LEFT_CELL = new Coordinate(0, 0);
    private static final Coordinate UPPER_CELL = new Coordinate(1, 0);
    private static final Coordinate UPPER_RIGHT_CELL = new Coordinate(2, 0);

    private static final Coordinate LEFT_CELL = new Coordinate(0, 1);
    private static final Coordinate CENTER_CELL = new Coordinate(1, 1);
    private static final Coordinate RIGHT_CELL = new Coordinate(2, 1);

    private static final Coordinate LOWER_LEFT_CELL = new Coordinate(0, 2);
    private static final Coordinate LOWER_CELL = new Coordinate(1, 2);
    private static final Coordinate LOWER_RIGHT_CELL = new Coordinate(2, 2);

    private final GameSettings settings = new GameSettings(3, 3, null);

    public static Stream<Arguments> parameters() {

        return Stream.of(
                Arguments.of(UPPER_LEFT_CELL, Map.of(
                        RIGHT, UPPER_CELL,
                        LOWER, LEFT_CELL,
                        LOWER_RIGHT, CENTER_CELL)),
                Arguments.of(UPPER_CELL, Map.of(
                        LEFT, UPPER_LEFT_CELL,
                        RIGHT, UPPER_RIGHT_CELL,
                        LOWER_LEFT, LEFT_CELL,
                        LOWER, CENTER_CELL,
                        LOWER_RIGHT, RIGHT_CELL)),
                Arguments.of(UPPER_RIGHT_CELL, Map.of(
                        LEFT, UPPER_CELL,
                        LOWER_LEFT, CENTER_CELL,
                        LOWER, RIGHT_CELL)
                ),

                Arguments.of(LEFT_CELL, Map.of(
                        UPPER, UPPER_LEFT_CELL,
                        UPPER_RIGHT, UPPER_CELL,
                        RIGHT, CENTER_CELL,
                        LOWER, LOWER_LEFT_CELL,
                        LOWER_RIGHT, LOWER_CELL)),
                Arguments.of(CENTER_CELL, Map.of(
                        UPPER_LEFT, UPPER_LEFT_CELL,
                        UPPER, UPPER_CELL,
                        UPPER_RIGHT, UPPER_RIGHT_CELL,
                        LEFT, LEFT_CELL,
                        RIGHT, RIGHT_CELL,
                        LOWER_LEFT, LOWER_LEFT_CELL,
                        LOWER, LOWER_CELL,
                        LOWER_RIGHT, LOWER_RIGHT_CELL)),
                Arguments.of(RIGHT_CELL, Map.of(
                        UPPER_LEFT, UPPER_CELL,
                        UPPER, UPPER_RIGHT_CELL,
                        LEFT, CENTER_CELL,
                        LOWER_LEFT, LOWER_CELL,
                        LOWER, LOWER_RIGHT_CELL)
                ),

                Arguments.of(LOWER_LEFT_CELL, Map.of(
                        UPPER, LEFT_CELL,
                        UPPER_RIGHT, CENTER_CELL,
                        RIGHT, LOWER_CELL)),
                Arguments.of(LOWER_CELL, Map.of(
                        UPPER_LEFT, LEFT_CELL,
                        UPPER, CENTER_CELL,
                        UPPER_RIGHT, RIGHT_CELL,
                        LEFT, LOWER_LEFT_CELL,
                        RIGHT, LOWER_RIGHT_CELL)),
                Arguments.of(LOWER_RIGHT_CELL, Map.of(
                        UPPER_LEFT, CENTER_CELL,
                        UPPER, RIGHT_CELL,
                        LEFT, LOWER_CELL)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetRelativeCoordinate(Coordinate initalCoordinate, Map<CellRelationship, Coordinate> expectedCoordinates) {
        for (CellRelationship cellRelationship : CellRelationship.values()) {
            Coordinate relativeCoordinate = cellRelationship.getRelativeCoordinate(settings, initalCoordinate);
            assertThat(relativeCoordinate)
                    .as("%s for %s", cellRelationship, initalCoordinate)
                    .isEqualTo(expectedCoordinates.get(cellRelationship));
        }
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testGetRelativeCoordinateXY(Coordinate initalCoordinate, Map<CellRelationship, Coordinate> expectedCoordinates) {
        for (CellRelationship cellRelationship : CellRelationship.values()) {
            Coordinate relativeCoordinate = cellRelationship.getRelativeCoordinate(settings, initalCoordinate.x(), initalCoordinate.y());
            assertThat(relativeCoordinate)
                    .as("%s for %s", cellRelationship, initalCoordinate)
                    .isEqualTo(expectedCoordinates.get(cellRelationship));
        }
    }
}