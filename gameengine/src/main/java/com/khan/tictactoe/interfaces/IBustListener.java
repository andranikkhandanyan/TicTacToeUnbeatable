package com.khan.tictactoe.interfaces;

import com.khan.tictactoe.engine.GameState;
import com.khan.tictactoe.engine.Seed;

public interface IBustListener {
    void onAIMove(int row, int col, Seed seed);
    void onGameOver(GameState gameState);
}
