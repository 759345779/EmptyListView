package com.example.majinxin1.emptylistview;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    TextView tv_empty;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*listview = (ListView) findViewById(R.id.listview);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        TextView header = new TextView(this);
        header.setText("这是一个头");
        TextView footer = new TextView(this);
        footer.setText("这是footer");
        listview.setEmptyView(tv_empty);
        listview.addHeaderView(header);
        listview.addFooterView(footer);
        listview.setAdapter(new MyAdapter());
        Log.i("app_widget_test", "onCreate");*/
        findViewById(R.id.buttn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

            }
        });

    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            textView.setText("这是内容");

            return textView;
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
    protected void onStart() {
        super.onStart();
        Log.i("app_widget_test", "onStart");
    }
}
