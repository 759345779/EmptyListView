package com.example.majinxin1.emptylistview.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.majinxin1.emptylistview.R;
import com.example.majinxin1.emptylistview.activity.BaseActivity;

public class PermissionActivity extends BaseActivity {
    ImageView image_loading;
    RotateAnimation rotateAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        rotateAnim = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF,  0.5f,Animation.RELATIVE_TO_SELF,  0.5f);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.setDuration(1000);

        image_loading = (ImageView) findViewById(R.id.image_loading);
        findViewById(R.id.bt_check_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        findViewById(R.id.bt_request_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        findViewById(R.id.bt_image_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starOrStopLoading();
            }
        });
    }

    private void starOrStopLoading() {
        if (image_loading.getVisibility() == View.VISIBLE) {
            image_loading.setVisibility(View.INVISIBLE);
            image_loading.clearAnimation();
        }else {
            image_loading.setVisibility(View.VISIBLE);
            image_loading.startAnimation(rotateAnim);
        }

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("permission_info", "not");
        }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            Log.i("permission_info", "DENIED");
        }else {
            Log.i("permission_info", "else");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("permission_info", "granted");
            }else {
                Log.i("permission_info", "not granted");
            }
        }
    }
}
