package com.example.majinxin1.emptylistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by MAJINXIN1 on 2017/1/4.
 */
public class ScrollTestListView extends ListView implements AbsListView.OnScrollListener {
    public ScrollTestListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_FLING:
                Log.i("scroll_test", "SCROLL_STATE_FLING");
                break;
            case SCROLL_STATE_IDLE:
                Log.i("scroll_test", "SCROLL_STATE_IDLE");
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("scroll_test", "SCROLL_STATE_TOUCH_SCROLL");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i("scroll_test", "fistVisible=" + firstVisibleItem + "  visibleItem=" + visibleItemCount + "  totalItem=" + totalItemCount);
    }
}
