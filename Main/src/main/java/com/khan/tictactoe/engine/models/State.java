package com.khan.tictactoe.engine.models;

public class State implements INode {
    private int mX;
    private int mY;
    protected Value mValue;
    protected Board mBoard;

    State (int x, int y, Value value, Board board) {
        mX = x;
        mY = y;
        mValue = value;
        mBoard = board;
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

    @Override
    public Board getBoard() {
        return mBoard;
    }
}
