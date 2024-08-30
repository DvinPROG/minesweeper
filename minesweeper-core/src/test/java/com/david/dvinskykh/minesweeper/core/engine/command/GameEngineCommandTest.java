package com.david.dvinskykh.minesweeper.core.engine.command;

import com.david.dvinskykh.minesweeper.core.engine.GameContext;
import com.david.dvinskykh.minesweeper.core.engine.GameSettings;
import org.junit.jupiter.api.Test;

import static com.david.dvinskykh.minesweeper.core.engine.GameStatus.END;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class GameEngineCommandTest {
    @Test
    void testExecuteNullGrid() {
        GameEngineCommand gameEngineCommand = new GameEngineCommand(END);
        GameContext context = new GameContext(mock(GameSettings.class));

        gameEngineCommand.execute(context);

        assertThat(context.getStatus())
                .isEqualTo(END);
    }
}