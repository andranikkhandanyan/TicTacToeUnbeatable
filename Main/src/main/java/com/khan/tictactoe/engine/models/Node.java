package com.khan.tictactoe.engine.models;

import java.util.ArrayList;

public abstract class Node {
    protected static final int WIN = 1;
    protected static final int DRAW = 0;
    protected static final int LOSE = -1;
    protected static final int CONTINUE = 1000;

    protected Value mValue;
    protected ArrayList<Node> children;
    protected Board currentBoard;
    protected int mLevel;
    public final int x;
    public final int y;

    public Node(Board currentBoard, int x, int y, int level) {
        this.currentBoard = currentBoard;
        this.x = x;
        this.y = y;
        children = new ArrayList<Node>();
        mLevel = level;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public abstract Value getValue();

    protected abstract Value checkMove(int x, int y);

    public Value mValue() {
        return mValue;
    }
}
