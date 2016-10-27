package com.example.majinxin1.emptylistview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by MAJINXIN1 on 2016/10/27.
 */

public class KinSwitch extends View {
    public KinSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KinSwitch(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("view_cycle", "onMeasure");
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.i("view_cycle", "layout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("view_cycle", "onDraw");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("view_cycle", "onSizeChanged_w=" + w + "  h=" + h);
    }
}
