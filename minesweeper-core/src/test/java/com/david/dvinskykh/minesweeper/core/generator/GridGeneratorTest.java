package com.david.dvinskykh.minesweeper.core.generator;

import com.david.dvinskykh.minesweeper.core.GameComplexity;
import com.david.dvinskykh.minesweeper.core.data.CellRelationship;
import com.david.dvinskykh.minesweeper.core.data.Coordinate;
import com.david.dvinskykh.minesweeper.core.data.Grid;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.david.dvinskykh.minesweeper.core.CellType.EMPTY;
import static com.david.dvinskykh.minesweeper.core.CellType.MINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GridGeneratorTest {

    @Mock
    private Random random;

    @Test
    void testStartCoordinatesIsNull() {
        GameSettings settings = new GameSettings(4, 4, GameComplexity.LOW);

        GridGenerator gridGenerator = new GridGenerator(settings);

        assertThatThrownBy(() -> gridGenerator.generateGrid(null))
                .isInstanceOf(IllegalCoordinateException.class);
    }

    @Test
    void testStartCoordinatesIsInvalid() {
        GameSettings settings = new GameSettings(4, 4, GameComplexity.LOW);

        GridGenerator gridGenerator = new GridGenerator(settings);

        assertThatThrownBy(() -> gridGenerator.generateGrid(new Coordinate(5, 5)))
                .isInstanceOf(IllegalCoordinateException.class);
    }

    @ParameterizedTest
    @EnumSource(GameComplexity.class)
    void testGenerateGrid(GameComplexity gameComplexity) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        when(random.nextInt(16))
                .thenAnswer(invocation -> atomicInteger.getAndIncrement());

        GameSettings settings = new GameSettings(4, 4, gameComplexity);

        GridGenerator gridGenerator = new GridGenerator(settings, random);

        Grid grid = gridGenerator.generateGrid(new Coordinate(3, 3));

        int cellCount = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                if (cellCount++ < gameComplexity.getCoefficient() * 16) {
                    assertThat(grid.getCell(coordinate))
                            .as(coordinate.toString())
                            .extracting(Grid.Cell::getType)
                            .isEqualTo(MINE);
                } else {
                    assertThat(grid.getCell(coordinate))
                            .as(coordinate.toString())
                            .extracting(Grid.Cell::getType)
                            .isEqualTo(EMPTY);
                }
            }
        }
    }

    @Test
    void testEmptyCellsInStartCoordinates() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        when(random.nextInt(100))
                .thenAnswer(invocation -> atomicInteger.getAndIncrement());

        GameSettings settings = new GameSettings(10, 10, GameComplexity.HIGH);

        GridGenerator gridGenerator = new GridGenerator(settings, random);

        Coordinate startCoordinate = new Coordinate(1, 1);

        Grid grid = gridGenerator.generateGrid(startCoordinate);

        Arrays.stream(CellRelationship.values())
                .map(cellRelationship -> cellRelationship.getRelativeCoordinate(settings, startCoordinate))
                .filter(Objects::nonNull)
                .forEach(coordinate ->
                        assertThat(grid.getCell(coordinate))
                                .extracting(Grid.Cell::getType)
                                .isEqualTo(EMPTY));
    }
}