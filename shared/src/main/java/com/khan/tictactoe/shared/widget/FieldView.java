package com.khan.tictactoe.shared.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FieldView extends View {

    private static final String sCrossValue = "X";
    private static final String sNoughtValue = "0";

    private Value mValue;
    private int mRow;
    private int mColumn;
    private boolean isAvailable = true;
    private final Paint mTextPaint = new Paint();

    public FieldView(Context context) {
        this(context, null);
    }

    public FieldView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initView(context);
    }

    private void init() {
        mValue = Value.EMPTY;
        mTextPaint.setColor(Color.DKGRAY);
        mTextPaint.setTextSize(200);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.STROKE);
    }

    private void initView(Context context) {
        setBackgroundColor(Color.WHITE);
    }

    public void setPosition(int row, int column) {
        mRow = row;
        mColumn = column;
    }

    public void setValue(Value value) {
        mValue = value;
        isAvailable = false;
        postInvalidate();
    }

    public void clear() {
        mValue = Value.EMPTY;
        isAvailable = true;
        postInvalidate();
    }

    public int getRow() {
        return mRow;
    }

    public int getColumn() {
        return mColumn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
//        canvas.drawARGB(80, 225, 225, 255);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2)) ;
        switch (mValue) {
            case CROSS:
                canvas.drawText(sCrossValue, xPos, yPos, mTextPaint);
                break;
            case NOUGHT:
                canvas.drawText(sNoughtValue, xPos, yPos, mTextPaint);
                break;
            case EMPTY:
                canvas.drawText("", xPos, yPos, mTextPaint);
                break;
        }
    }

    public enum Value {
        CROSS, NOUGHT, EMPTY;
    }
}
