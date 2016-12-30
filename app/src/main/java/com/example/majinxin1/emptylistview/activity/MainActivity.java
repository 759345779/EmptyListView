package com.example.majinxin1.emptylistview.activity;

import android.app.ActionBar;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
    String[] strNames = {"Main2Activity", "PermissonActivity", "ExcutoersTestActivity","getWidgetManagerTest","PatelleTest"};
    LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getActionBar();
//        actionBar.hide();
        listview = (ListView) findViewById(R.id.listview);
        inflater = LayoutInflater.from(this);
       /* tv_empty = (TextView) findViewById(R.id.tv_empty);
        TextView header = new TextView(this);
        header.setText("这是一个头");
        TextView footer = new TextView(this);
        footer.setText("这是footer");
        listview.setEmptyView(tv_empty);
        listview.addHeaderView(header);
        listview.addFooterView(footer);
        listview.setAdapter(new MyAdapter());
        Log.i("app_widget_test", "onCreate");*/
        listview.setAdapter(new MyAdapter());
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity(position);
            }
        });
        findViewById(R.id.buttn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

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
