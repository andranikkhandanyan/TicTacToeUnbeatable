package com.khan.tictactoe.interfaces;

import com.khan.tictactoe.engine.models.Coordinate;

public interface IBustListener {

    int WIN = 0;
    int DRAW = 1;

    void onMove(Coordinate coordinate);
    void onGameOver(int state, String player);
}
