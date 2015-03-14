package com.khan.tictactoe.engine.models;

public class State implements INode {
    private int mX;
    private int mY;
    protected Value mValue;

    State (int x, int y, Value value) {
        mX = x;
        mY = y;
        mValue = value;
    }

    @Override
    public Value getValue() {
        return mValue;
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
    }
}
