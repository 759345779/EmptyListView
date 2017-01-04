package com.example.majinxin1.emptylistview.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.majinxin1.emptylistview.R;
import com.example.majinxin1.emptylistview.WidgetService;

public class MainActivity extends BaseActivity {
    ListView listview;
    TextView tv_empty;
    String[] strNames = {"Main2Activity", "PermissonActivity", "ExcutoersTestActivity","getWidgetManagerTest","PatelleTest","actionBarActivity",
            "ScrellTest"};
    LayoutInflater inflater;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.window_icon);
        toolbar.setTitle("主标题");
        toolbar.setSubtitle("副标题");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        listview = (ListView) findViewById(R.id.listview);
        inflater = LayoutInflater.from(this);
        listview.setAdapter(new MyAdapter());
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity(position);
            }
        });
    }

    private void openActivity(int position) {
        switch (position) {
            case 0:
                openActivity(Main2Activity.class);
                break;
            case 1:
                openActivity(PermissionActivity.class);
                break;
            case 2:
                openActivity(ExcutoersTestActivity.class);
                break;
            case 3:
                getWidgetManaget();
                break;
            case 4:
                openActivity(PetelleTestActivity.class);
                break;
            case 5:
                openActivity(ActionBarDemoActivity.class);
                break;
            case 6:
                openActivity(ScrollTestActivity.class);
                break;
        }
    }

    private void getWidgetManaget() {
        WidgetService.mCount++;
        WidgetService.itemStr.add("添加=" + WidgetService.mCount);
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(intent);
    }

    private void openActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);

    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return strNames.length;
        }

        @Override
        public Object getItem(int position) {
            return strNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item, null);
            }
            ((TextView)(convertView.findViewById(R.id.tv_item))).setText(strNames[position]);

            return convertView;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("app_widget_test", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("app_widget_test", "onResume");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            int mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            Log.i("app_widget_test", "app_widget_id="+ mAppWidgetId);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("config", newConfig.toString());
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("app_widget_test", "onStart");
    }
}
