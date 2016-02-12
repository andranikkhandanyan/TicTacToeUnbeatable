package com.khan.tictactoe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khan.tictactoe.R;
import com.khan.tictactoe.engine.Game;
import com.khan.tictactoe.engine.GameState;
import com.khan.tictactoe.engine.Seed;
import com.khan.tictactoe.engine.models.Coordinate;
import com.khan.tictactoe.interfaces.IBustListener;

public class MainActivity extends Activity {

    private View.OnClickListener mOnFieldClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView field = (TextView) view;
            Coordinate coordinate = getCoordinateFromView((String)field.getTag());
            String symbol = "";
            switch (mGame.getCurrentPlayer()) {
                case CROSS:
                    symbol = "X";
                    break;
                case NOUGHT:
                    symbol = "0";
                    break;
            }
            field.setText(symbol);
            mGame.playerMove(coordinate.row, coordinate.column);
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
        public void onAIMove(final Coordinate coordinate) {
            handleAIMove(coordinate);
        }

        @Override
        public void onGameOver(GameState gameState) {
            handleGameOver(gameState);
        }
    };

    private TextView[][] fields = new TextView[3][3];
    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.replay).setOnClickListener(mOnClickListener);
        mGame = Game.getInstance();
        mGame.setIBustListener(mIBustListener);
        replay();
    }

    private void initView() {
        ViewGroup root = (ViewGroup) findViewById(R.id.root);
        for(int y = 0; y < root.getChildCount(); y++) { // column
            ViewGroup line = (ViewGroup)root.getChildAt(y);
            for (int x = 0; x < line.getChildCount(); x++) { // row
                TextView field = (TextView)line.getChildAt(x);
                field.setText("");
                fields[y][x] = field;
                field.setOnClickListener(mOnFieldClickListener);
                field.setTag(y + "_" + x);
            }
        }
    }

    private void replay() {
        initView();
        mGame.start(Seed.CROSS);
    }

    private Coordinate getCoordinateFromView(String s) {
        String[] parts = s.split("_");
        int y = Integer.parseInt(parts[0]);
        int x = Integer.parseInt(parts[1]);
        return new Coordinate(x, y);
    }

    private void handleAIMove(final Coordinate coordinate) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fields[coordinate.column][coordinate.row].setText(getSymbol(mGame.getCurrentPlayer()));
            }
        });
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

    private String getSymbol(Seed seed) {
        String symbol = "";
        switch (seed) {
            case CROSS:
                symbol = "0";
                break;
            case NOUGHT:
                symbol = "X";
                break;
        }

        return symbol;
    }
}
