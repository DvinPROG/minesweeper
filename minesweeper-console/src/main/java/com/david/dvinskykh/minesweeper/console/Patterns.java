package com.david.dvinskykh.minesweeper.console;

import java.util.regex.Pattern;

public interface Patterns {
    String ENGINE_COMMAND_GROUP = "engineCommand";
    Pattern ENGINE_COMMAND_PATTERN = Pattern.compile("(?<engineCommand>[frcoq])((?<=([fo]))\\s+?(?<x>\\d+?)\\s+?(?<y>\\d+))?");
    Pattern GAME_ENGINE_COMMAND_PATTERN = Pattern.compile("[rcq]");
    Pattern GAME_SETTINGS_PATTERN = Pattern.compile("(?<width>\\d+)\\s+(?<height>\\d+)\\s+(?<complexity>LOW|MEDIUM|HIGH)");
}
