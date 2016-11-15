package com.example.majinxin1.emptylistview;

import android.app.Application;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by mjx on 2016/11/10.
 */

public class MyApp extends Application {
    public static String TAG = "MyApp";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("config_app", "create_font_size=" +getFontSize());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("config_app", newConfig.toString());
        Log.i("config_app", "font_size=" + newConfig.fontScale);
        super.onConfigurationChanged(newConfig);
    }

    public  float getFontSize() {
        Configuration mCurConfig = new Configuration();
        mCurConfig.updateFrom(this.getResources().getConfiguration());
        return mCurConfig.fontScale;

    }
}
