package com.khan.tictactoe.engine.models;

public class Value {
    public final int result;
    public final double percentage;

    public Value(int result, double percentage) {
        this.result = result;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "r: " + result + ", p: " + percentage;
    }
}
