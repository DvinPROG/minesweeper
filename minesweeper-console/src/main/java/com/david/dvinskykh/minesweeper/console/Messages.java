package com.david.dvinskykh.minesweeper.console;

public interface Messages {
    String GREETING_MESSAGE = """
            ###############################################################
            ######                     MineSweeper               ##########
            ###############################################################
            To put flag just write a engineCommand:
                f 1 1
            To open flag just write a engineCommand:
                o 1 1
            To restart game just write a engineCommand:
                r

            There is three levels of complexity: LOW, MEDIUM, HIGH
            Please, write the width and the height with game complexity:
                <width> <height> <complexity>
            For example:
                5 5 LOW

            Now, you turn:
            """;
    String WRONG_DATA_FOR_GREETING = """
            The input format is wrong. The correct one is:
                <width> <height> <complexity>
            Please, try again:
            """;
    String GAME_OVER = "Game over";
    String OPEN_MINE = """
            ################################
            ########## BOOOOOOOOM ##########
            ################################

            Do you want to continue?
            If yes - write:
                c
            If no - write:
                q
            If you want to restart game - write:
                r
            Please, write your choice:
            """;
    String WRONG_DATA_FOR_OPEN_MINE = """
            Wrong format.
            Do you want to continue?
            If yes - write:
                c
            If no - write:
                q
            If you want to restart game - write:
                r
            Please, write your choice:
            """;
    String COMMAND_IS_WRONG = """
            Command is in wrong format.

            To put flag just write a engineCommand:
                f 1 1
            To open flag just write a engineCommand:
                o 1 1
            To restart game just write a engineCommand:
                r
            """;
    String RESTART_GAME = """
            Do you want to continue?
            If yes - write:
                c
            If no - write:
                q
            If you want to restart game - write:
                r
            Please, write your choice:""";
    String YOU_WIN = "You win! Congratulations!";
    String YOU_LOST = "You lost!";
}
