package com.khan.tictactoe.engine;

import com.khan.tictactoe.engine.models.Board;
import com.khan.tictactoe.engine.models.Field;

public abstract class AIPlayer {
    protected int ROWS = Game.ROWS;
    protected int COLS = Game.COLUMNS;

    protected Field[][] fields;
    protected Seed mySeed;    // computer's seed
    protected Seed oppSeed;   // opponent's seed

    /** Constructor with reference to game board */
    public AIPlayer(Board board) {
        fields = board.getFields();
    }

    /** Set/change the seed used by computer and opponent */
    public void setSeed(Seed seed) {
        this.mySeed = seed;
        oppSeed = (mySeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    /** Get next move. Return int[2] of {row, col} */
    abstract int[] move();
}
