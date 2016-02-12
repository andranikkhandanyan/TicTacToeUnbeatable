package com.khan.tictactoe.interfaces;

import com.khan.tictactoe.engine.GameState;
import com.khan.tictactoe.engine.models.Coordinate;

public interface IBustListener {
    void onAIMove(Coordinate coordinate);
    void onGameOver(GameState gameState);
}
