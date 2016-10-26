/*
 * Copyright (c) 2014. Kingsoft Office Software. All rights reserved.
 */

package com.example.majinxin1.emptylistview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huzhi on 14-12-11.
 */
public class UISwitch extends View {

    private static float scaleHeigh =2.0f;
    private static float scaleWidth =2.5f;
    protected Bitmap bitmap_bg_on, bitmap_bg_off;

    protected boolean mChecked;

    protected OnChangedListener mChgLsn;

    public UISwitch(Context context) {
        super(context);
    }

    public UISwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UISwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public interface OnChangedListener {
        abstract void onChanged(boolean CheckState);
    }

    /**仅仅引发UI上的变化，不会触发事件*/
    public void setSelected(boolean isSelected){
        if(mChecked!=isSelected){
            mChecked = isSelected;
            invalidate();
        }
    }

    /**引发UI上的变化，触发事件*/
    public void setChecked(boolean isChecked) {
        if (mChecked != isChecked) {
            mChecked = isChecked;
            invalidate();
            if (mChgLsn != null)
                mChgLsn.onChanged(mChecked);
        }
    }

    /**设置开关切换事件*/
    public void setOnChangedListener(OnChangedListener l) {
        mChgLsn = l;
    }

    public boolean isChecked(){
        return mChecked;
    }

    public void setSwitchOff(int resourceId){
        bitmap_bg_off =  big(BitmapFactory.decodeResource(getResources(), resourceId));
    }

    public void setSwitchOn(int resourceId){

        bitmap_bg_on = big(BitmapFactory.decodeResource(getResources(), resourceId));

    }

    public static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeigh);
        Bitmap resizeBmp=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
    public static Bitmap bigCircle(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleHeigh, scaleHeigh);
        Bitmap resizeBmp=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
}
