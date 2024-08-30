package com.david.dvinskykh.minesweeper.core.data;

import com.david.dvinskykh.minesweeper.core.GameComplexity;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateTest {

    private static final GameSettings SETTINGS = new GameSettings(2, 2, GameComplexity.LOW);

    public static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(new Coordinate(0, 0), true),
                Arguments.of(new Coordinate(-1, 0), false),
                Arguments.of(new Coordinate(0, -1), false),
                Arguments.of(new Coordinate(2, 0), false),
                Arguments.of(new Coordinate(0, 2), false)
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    void testIsValid(Coordinate coordinate, boolean expectedResult) {
        assertThat(coordinate.isValid(SETTINGS))
                .isEqualTo(expectedResult);
    }
}