package com.khan.tictactoe.engine;

import android.util.Log;

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
                    Log.v(TAG, "thread started");
                    mRoot = (mCurrentPlayer == PLAYER_O) ? new XNode(mBoard, x, y, 0) : new ONode(mBoard, x, y, 0);
                    mRoot.getValue();
                    togglePlayer();
                    mCurrent = mRoot;
                    isFirstMove = false;

                    mIBustListener.onMove(getBestMovie());

                    Log.v(TAG, "thread completed");
                }
            }).start();

        } else {
            togglePlayer();
            mIBustListener.onMove(getBestMovie());
        }


    }

    private Coordinate getBestMovie() {
         Node bestNode = mCurrent.getChildren().get(0);
        int max = bestNode.getValue().result;
        for(Node node: mCurrent.getChildren()) {
            int current = node.getValue().result;
            if(current < max) {
                max = current;
                bestNode = node;
            } else if (max == current) {
                if(bestNode.getValue().percentage < node.getValue().percentage) {
                    bestNode = node;
                }
            }
        }

        mCurrent = bestNode;
        return new Coordinate(mCurrent.mX, mCurrent.mY);
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
}
