package com.khan.tictactoe.engine.models;

import com.khan.tictactoe.engine.Game;
import com.khan.tictactoe.engine.Seed;

public class Board {
    private static final int BOARD_WIDTH = Game.ROWS;
    private static final int BOARD_HEIGHT = Game.COLUMNS;
    private Field[][] fields;
    public int currentRow;
    public int currentColumn;

    public Board() {
        fields = new Field[BOARD_WIDTH][BOARD_HEIGHT];
        for(int row = 0; row < BOARD_WIDTH; row++) {
            for (int column = 0; column < BOARD_HEIGHT; column++) {
                fields[row][column] = new Field(row, column);
            }
        }
    }

    public Board(Field[][] fields) {
        this.fields = new Field[BOARD_WIDTH][BOARD_HEIGHT];
        System.arraycopy(fields, 0, this.fields, 0, fields.length);
    }

    public boolean isDraw() {
        for (int row = 0; row < BOARD_WIDTH; ++row) {
            for (int col = 0; col < BOARD_HEIGHT; ++col) {
                if (fields[row][col].value == Seed.EMPTY) {
                    return false; // an empty seed found, not a draw, exit
                }
            }
        }
        return true; // no empty cell, it's a draw
    }

    public boolean hasWon(Seed s) {
        return (fields[currentRow][0].value == s         // 3-in-the-row
                && fields[currentRow][1].value == s
                && fields[currentRow][2].value == s
                || fields[0][currentColumn].value == s      // 3-in-the-column
                && fields[1][currentColumn].value == s
                && fields[2][currentColumn].value == s
                || currentRow == currentColumn            // 3-in-the-diagonal
                && fields[0][0].value == s
                && fields[1][1].value == s
                && fields[2][2].value == s
                || currentRow + currentColumn == 2    // 3-in-the-opposite-diagonal
                && fields[0][2].value == s
                && fields[1][1].value == s
                && fields[2][0].value == s);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                stringBuilder.append(fields[y][x]);
                stringBuilder.append("|");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public Field[][] getFields() {
        return fields;
    }
}
