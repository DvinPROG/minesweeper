package com.david.dvinskykh.minesweeper.core;

import lombok.Getter;

@Getter
public enum GameComplexity {
    LOW(0.1),
    MEDIUM(0.25),
    HIGH(0.5);

    private final double coefficient;

    GameComplexity(double coefficient) {
        this.coefficient = coefficient;
    }
}
