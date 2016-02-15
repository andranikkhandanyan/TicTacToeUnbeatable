package com.khan.tictactoe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.khan.tictactoe.R;
import com.khan.tictactoe.engine.Game;
import com.khan.tictactoe.engine.GameState;
import com.khan.tictactoe.engine.Seed;
import com.khan.tictactoe.interfaces.IBustListener;
import com.khan.tictactoe.shared.widget.FieldView;
import com.khan.tictactoe.shared.widget.FieldsContainerView;

public class MainActivity extends Activity {

    private FieldsContainerView.OnFieldClickListener mOnFieldClickListener = new FieldsContainerView.OnFieldClickListener() {
        @Override
        public void onClicked(int row, int col, FieldView fieldView) {
            if (mGame.getCurrentState() == GameState.PLAYING && fieldView.isAvailable()) {
                FieldView.Value symbol = getSymbol(mGame.getCurrentPlayer());
                fieldView.setValue(symbol);
                mGame.playerMove(row, col);
            }
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.replay:
                    replay();
                    break;
            }
        }
    };

    private IBustListener mIBustListener = new IBustListener() {
        @Override
        public void onAIMove(int row, int col, Seed seed) {
            handleAIMove(row, col, seed);
        }

        @Override
        public void onGameOver(GameState gameState) {
            handleGameOver(gameState);
        }
    };

    private FieldsContainerView mFieldsContainerView;
    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mGame = Game.getInstance();
        mGame.setIBustListener(mIBustListener);
        replay();
    }

    private void initView() {
        mFieldsContainerView = (FieldsContainerView) findViewById(R.id.fields_container);
        mFieldsContainerView.setOnFieldClickListener(mOnFieldClickListener);
        findViewById(R.id.replay).setOnClickListener(mOnClickListener);
    }

    private void replay() {
        mFieldsContainerView.reset();
        mGame.start(Seed.CROSS);
    }

    private void handleAIMove(final int row, final int col, final Seed seed) {
        mFieldsContainerView.getField(row, col).setValue(getSymbol(seed));
    }

    private void handleGameOver(final GameState gameState) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = "";
                switch (gameState) {
                    case DRAW:
                        msg = "Draw";
                        break;
                    case CROSS_WON:
                        msg = "Cross" + " Won";
                        break;
                    case NOUGHT_WON:
                        msg = "NOUGHT" + " Won";
                        break;
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private FieldView.Value getSymbol(Seed seed) {
        FieldView.Value symbol = FieldView.Value.EMPTY;
        switch (seed) {
            case CROSS:
                symbol = FieldView.Value.CROSS;
                break;
            case NOUGHT:
                symbol = FieldView.Value.NOUGHT;
                break;
        }

        return symbol;
    }
}
