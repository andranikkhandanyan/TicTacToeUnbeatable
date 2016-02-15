package com.khan.tictactoe.shared.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.khan.tictactoe.shared.R;

import java.util.ArrayList;
import java.util.List;

public class FieldsContainerView extends FrameLayout {
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof FieldView) {
                if (mOnFieldClickListener != null) {
                    FieldView fieldView = ((FieldView) v);
                    int row = fieldView.getRow();
                    int col = fieldView.getColumn();
                    mOnFieldClickListener.onClicked(row, col, fieldView);
                }
            } else {
                throw new IllegalStateException("View should be extended from com.khan.tictactoe.shared.widget.FieldView");
            }
        }
    };

    private int mRows;
    private int mColumns;
    private List<FieldView> mFields;
    private final Rect mTmpChildRect = new Rect();
    private OnFieldClickListener mOnFieldClickListener;

    public FieldsContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FieldsContainerView(Context context) {
        this(context, null);
    }

    public FieldsContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        initChildren(context);
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.FieldsContainerView,
                0,
                0);
        try {
            mRows = a.getInt(R.styleable.FieldsContainerView_rows, 0);
            mColumns = a.getInt(R.styleable.FieldsContainerView_columns, 0);
        } finally {
            a.recycle();
        }
    }

    private void initChildren(Context context) {
        FieldView fieldView;
        mFields = new ArrayList<>();
        LayoutParams params;
        for (int row = 0; row < mRows; row++) {
            for (int col = 0; col < mColumns; col++) {
                fieldView = new FieldView(context);
                fieldView.setPosition(row, col);
                fieldView.setOnClickListener(mOnClickListener);
                addView(fieldView);
                params = (LayoutParams)fieldView.getLayoutParams();
                params.setMargins(5, 5, 5, 5);
                mFields.add(fieldView);
            }
        }
    }

    public int getRows() {
        return mRows;
    }

    public void setRows(int rows) {
        mRows = rows;
    }

    public int getColumns() {
        return mColumns;
    }

    public void setColumns(int columns) {
        mColumns = columns;
    }

    public FieldView getField(int row, int col) {
        return mFields.get(row * mColumns + col);
    }

    public void reset() {
        for (FieldView fieldView : mFields) {
            fieldView.clear();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childMeasure;
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width <= height) {
            childMeasure= width / mRows;
            height = childMeasure * mColumns;
        } else {
            childMeasure = height / mColumns;
            width = childMeasure * mRows;
        }
        for (FieldView fieldView : mFields) {
            final LayoutParams lp = (LayoutParams) fieldView.getLayoutParams();
            lp.width = childMeasure;
            lp.height = childMeasure;
            measureChildWithMargins(fieldView, childMeasure, 0, childMeasure, 0);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // These are the far left and right edges in which we are performing layout.
        int leftPos = getPaddingLeft();
        int rightPos = right - left - getPaddingRight();
        // These are the top and bottom edges in which we are performing layout.
        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();
        if (changed) {
            FieldView fieldView;
            for (int i = 0; i < mFields.size(); i++) {
                fieldView = mFields.get(i);
                final LayoutParams lp = (LayoutParams) fieldView.getLayoutParams();
                final int width = fieldView.getMeasuredWidth();
                final int height = fieldView.getMeasuredHeight();
                int row = i / mRows;
                int col = i - row * mColumns;
                mTmpChildRect.setEmpty();
                mTmpChildRect.left = leftPos + col * width + lp.leftMargin;
                mTmpChildRect.right = mTmpChildRect.left + width - lp.rightMargin;
                mTmpChildRect.top = parentTop + height * row + lp.topMargin;
                mTmpChildRect.bottom = mTmpChildRect.top + height - lp.bottomMargin;
                fieldView.layout(mTmpChildRect.left, mTmpChildRect.top,
                        mTmpChildRect.right, mTmpChildRect.bottom);
            }
        }
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void setOnFieldClickListener(OnFieldClickListener onFieldClickListener) {
        mOnFieldClickListener = onFieldClickListener;
    }

    public interface OnFieldClickListener {
        void onClicked(int row, int col, FieldView fieldView);
    }
}
