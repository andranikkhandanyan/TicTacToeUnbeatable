package com.khan.tictactoe.engine.models;

public class Field {

    public static final int VALUE_UNDEFINED = -1;
    public static final int VALUE_O = 0;
    public static final int VALUE_X = 1;

    public final int x;
    public final int y;

    public int value;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        value = VALUE_UNDEFINED;
    }
}
