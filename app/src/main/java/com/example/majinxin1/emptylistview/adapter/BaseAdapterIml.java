package com.example.majinxin1.emptylistview.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by MAJINXIN1 on 2017/1/4.
 */
public abstract class BaseAdapterIml extends BaseAdapter {

    @Override
    public int getCount() {
        return getDataCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position,convertView,parent);
    }

    public abstract int getDataCount();
    public abstract  View createView(int position, View converView, ViewGroup parent) ;

}
