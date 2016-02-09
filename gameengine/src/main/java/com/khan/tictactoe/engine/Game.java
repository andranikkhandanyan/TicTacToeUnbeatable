package com.khan.tictactoe.engine;

import com.khan.tictactoe.engine.models.*;
import com.khan.tictactoe.interfaces.IBustListener;

public class Game {

    public static final int PLAYER_O = 0;
    public static final int PLAYER_1 = 1;

    private static Game mInstance;
    private Board mBoard;
    private Node mRoot;
    private Node mCurrent;
    private int mCurrentPlayer;
    private boolean isFirstMove;
    private IBustListener mIBustListener;
    private boolean isStarted;

    private static final String TAG = Game.class.getSimpleName();

    public static Game getInstance() {
        if(mInstance == null) {
            mInstance = new Game();
        }

        return mInstance;
    }

    private Game() {
        mBoard = new Board();
        isFirstMove = true;
    }

    public void start(int which) {
        isStarted = true;
        mCurrentPlayer = which;
        mBoard = new Board();
        isFirstMove = true;
    }

    public void move(final int x, final int y) {
        mBoard.getBoard()[y][x].value = (mCurrentPlayer == PLAYER_O) ? Field.VALUE_O : Field.VALUE_X;
        if(isFirstMove) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mRoot = (mCurrentPlayer == PLAYER_O) ? new XNode(mBoard, x, y, 0) : new ONode(mBoard, x, y, 0);
                    mRoot.getValue();
                    togglePlayer();
                    mCurrent = mRoot;
                    isFirstMove = false;

                    mIBustListener.onMove(getBestMovie());
                }
            }).start();

        } else {
            if(isStarted) {
                togglePlayer();
                makeUserStep(x, y);
            }
            if (isStarted) {
                mIBustListener.onMove(getBestMovie());
            }
        }
    }

    private Coordinate getBestMovie() {
        INode bestNode = mCurrent.getChildren().get(0);
        int min = bestNode.getValue().result;
        int minEval = evaluate(mCurrent.getBoard());
        for(INode node: mCurrent.getChildren()) {
            int current = node.getValue().result;
            int currentEval = evaluate(node.getBoard());
            if(current < min) {
                min = current;
                bestNode = node;
                minEval = currentEval;
            } else if (min == current) {
                if (currentEval < minEval) {
                    min = current;
                    bestNode = node;
                    minEval = currentEval;
                }
//                if(bestNode.getValue().percentage > node.getValue().percentage) {
//                    bestNode = node;
//                }
            }
        }

        if (bestNode instanceof Node) {
            mCurrent = (Node)bestNode;
        } else {
            gameOver(bestNode);
            mCurrent = null;
        }
        return new Coordinate(bestNode.getX(), bestNode.getY());
    }

    public void makeUserStep(int x, int y) {
        for(INode node: mCurrent.getChildren()) {
            if(node.getX() == x && node.getY() == y) {
                if (node instanceof Node) {
                    mCurrent = (Node)node;
                } else {
                    gameOver(node);
                    mCurrent = null;
                }
                break;
            }
        }
    }

    public void togglePlayer() {
        mCurrentPlayer = (mCurrentPlayer == PLAYER_O) ? PLAYER_1 : PLAYER_O;
    }

    public String getCurrentSymbol() {
        return  (mCurrentPlayer == PLAYER_O) ? "0" : "X";
    }

    public void setIBustListener(IBustListener IBustListener) {
        mIBustListener = IBustListener;
    }

    private void gameOver(INode node) {
        int state = (node.getValue().result == 0) ? IBustListener.DRAW : IBustListener.WIN;
        String player = (mCurrentPlayer == PLAYER_1) ? "Player 1 " : "Player 2";
        mIBustListener.onGameOver(state, player);
        isStarted = false;
    }

    /** The heuristic evaluation function for the current board
     @return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer.
     -100, -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent.
     0 otherwise   */
    private int evaluate(Board board) {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateLine(board, 0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(board, 1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(board, 2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(board, 0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(board, 0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(board, 0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(board, 0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(board, 0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    /** The heuristic evaluation function for the given line of 3 cells
     @return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer.
     -100, -10, -1 for 3-, 2-, 1-in-a-line for opponent.
     0 otherwise */
    private int evaluateLine(Board board, int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;
        Field[][] cells = board.getBoard();

        // First cell
        if (cells[col1][row1].value == Field.VALUE_X) {
            score = 1;
        } else if (cells[col1][row1].value == Field.VALUE_O) {
            score = -1;
        }

        // Second cell
        if (cells[col2][row2].value == Field.VALUE_X) {
            if (score == 1) {   // cell1 is mySeed
                score = 10;
            } else if (score == -1) {  // cell1 is oppSeed
                return 0;
            } else {  // cell1 is empty
                score = 1;
            }
        } else if (cells[col2][row2].value == Field.VALUE_O) {
            if (score == -1) { // cell1 is oppSeed
                score = -10;
            } else if (score == 1) { // cell1 is mySeed
                return 0;
            } else {  // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (cells[col3][row3].value == Field.VALUE_X) {
            if (score > 0) {  // cell1 and/or cell2 is mySeed
                score *= 10;
            } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = 1;
            }
        } else if (cells[col3][row3].value == Field.VALUE_O) {
            if (score < 0) {  // cell1 and/or cell2 is oppSeed
                score *= 10;
            } else if (score > 1) {  // cell1 and/or cell2 is mySeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }
}
