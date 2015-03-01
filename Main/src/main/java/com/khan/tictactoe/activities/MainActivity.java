package com.khan.tictactoe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.khan.tictactoe.R;
import com.khan.tictactoe.engine.Game;
import com.khan.tictactoe.engine.models.Coordinate;
import com.khan.tictactoe.interfaces.IBustListener;

public class MainActivity extends Activity {

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView field = (TextView) view;
            Coordinate coordinate = getCoordinateFromView((String)field.getTag());
            field.setText(Game.getInstance().getCurrentSymbol());
            Game.getInstance().move(coordinate.x, coordinate.y);
        }
    };

    private IBustListener mIBustListener = new IBustListener() {
        @Override
        public void onMove(Coordinate coordinate) {
            fields[coordinate.y][coordinate.x].setText(Game.getInstance().getCurrentSymbol());
        }
    };

    private TextView[][] fields = new TextView[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Game.getInstance().start(Game.PLAYER_1);
        Game.getInstance().setIBustListener(mIBustListener);
    }

    private void initView() {
        ViewGroup root = (ViewGroup) findViewById(R.id.root);
        for(int i = 0; i < root.getChildCount(); i++) {
            ViewGroup line = (ViewGroup)root.getChildAt(i);
            for (int j = 0; j < line.getChildCount(); j++) {
                TextView field = (TextView)line.getChildAt(j);
                fields[i][j] = field;
                field.setOnClickListener(mOnClickListener);
                field.setTag(i + "_" + j);
            }
        }
    }

    private Coordinate getCoordinateFromView(String s) {
        String[] parts = s.split("_");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[0]);
        return new Coordinate(x, y);
    }
}
