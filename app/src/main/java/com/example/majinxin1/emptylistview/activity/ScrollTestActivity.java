package com.example.majinxin1.emptylistview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.majinxin1.emptylistview.R;
import com.example.majinxin1.emptylistview.adapter.BaseAdapterIml;

import java.util.ArrayList;

public class ScrollTestActivity extends BaseActivity {
    ListView listView;
    ArrayList<Integer> datalist = new ArrayList<>();
    LayoutInflater inflater = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater= LayoutInflater.from(this);
        setContentView(R.layout.activity_scroll_test);
        listView = (ListView) findViewById(R.id.listview);
        createData();
        listView.setAdapter(new BaseAdapterIml() {
            @Override
            public int getDataCount() {
                return datalist.size();
            }

            @Override
            public View createView(int position, View converView, ViewGroup parent) {
                if (converView == null) {
                    converView = inflater.inflate(R.layout.scrolltest_item, null);
                }
                ((TextView) converView.findViewById(R.id.tv_content)).setText(datalist.get(position)+"");
                return converView;
            }
        });
    }

    private void createData() {
        for (int i=0;i<20;i++){
            datalist.add (i);
        }
    }


}
