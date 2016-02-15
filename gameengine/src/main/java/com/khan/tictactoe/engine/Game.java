package com.khan.tictactoe.engine;

import com.khan.tictactoe.engine.models.*;
import com.khan.tictactoe.interfaces.IBustListener;

public class Game {

    public static final int ROWS = 3;
    public static final int COLUMNS = 3;

    private Seed currentPlayer;
    private GameState currentState;
    private Board board;
    private boolean isAI;

    private static Game mInstance;
    private IBustListener mIBustListener;

    private static final String TAG = Game.class.getSimpleName();

    public static Game getInstance() {
        if(mInstance == null) {
            mInstance = new Game();
        }

        return mInstance;
    }

    private Game() {
        board = new Board();
    }

    public void start(Seed which) {
        start(which, true);
    }

    public void start(Seed which, boolean isAI) {
        currentPlayer = which;
        board = new Board();
        currentState = GameState.PLAYING;
        this.isAI = isAI;
    }

    public void playerMove(int row, int column) {
        move(currentPlayer, row, column);
        if (isAI) {
            moveAI();
        }
    }

    private void move(Seed player, int row, int column) {
        if (player != currentPlayer) {
            throw new IllegalArgumentException("Wrong player");
        }
        if (currentState == GameState.PLAYING) {
            if (board.getFields()[row][column].value == Seed.EMPTY) {
                board.getFields()[row][column].value = currentPlayer;
                board.currentRow = row;
                board.currentColumn = column;
                updateGame(currentPlayer);
                togglePlayer();
                if (currentState != GameState.PLAYING) {
                    mIBustListener.onGameOver(currentState);
                }
            }
        }
    }

    private void moveAI() {
        if (currentState == GameState.PLAYING) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AIPlayer aiPlayer = new AIPlayerMinimax(board);
                    aiPlayer.setSeed(currentPlayer);
                    int[] result = aiPlayer.move();
                    int row = result[0];
                    int col = result[1];
                    mIBustListener.onAIMove(row, col, currentPlayer);
                    move(currentPlayer, row, col);
                }
            }).start();
        }
    }

    public void updateGame(Seed player) {
        if (board.hasWon(player)) {
            currentState = (player == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (board.isDraw()) {
            currentState = GameState.DRAW;
        } // continue game, no change
    }

    public void togglePlayer() {
        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    public void setIBustListener(IBustListener IBustListener) {
        mIBustListener = IBustListener;
    }

    public void setIsAI(boolean isAI) {
        this.isAI = isAI;
    }

    public Seed getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
