package com.khan.tictactoe.engine.models;

import com.khan.tictactoe.engine.Seed;

public class Field {
    public final int row;
    public final int column;

    public Seed value;

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
        clear();
    }

    public void clear() {
        value = Seed.EMPTY;
    }

    @Override
    public String toString() {
        String rv = "";
        switch (value) {
            case CROSS:  rv = " X "; break;
            case NOUGHT: rv = " X "; break;
            case EMPTY:  rv = " X "; break;
        }

        return rv;
    }
}
