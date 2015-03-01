package com.khan.tictactoe.engine.models;

public class Board {
    public static final int BOARD_WIDTH = 3;
    public static final int BOARD_HEIGHT = 3;
    private Field[][] board;

    public Board() {
        board = new Field[BOARD_WIDTH][BOARD_HEIGHT];
        for(int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                board[i][j] = new Field(i, j);
            }
        }
    }

    public Board(Field[][] fields) {
        board = new Field[BOARD_WIDTH][BOARD_HEIGHT];
        System.arraycopy(fields, 0, board, 0, fields.length);
    }

    public void move(int x, int y, int player) {

    }

    public Field[][] getBoard() {
        return board;
    }
}
