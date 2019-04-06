package com.example.majinxin1.emptylistview.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.majinxin1.emptylistview.R;

import java.util.ArrayList;
import java.util.List;


public class PetelleTestActivity extends Activity {
    Bitmap bitmap;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petelle_test);
        listView = (ListView) findViewById(R.id.list_view);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.window_icon);
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch colorVibSwatch = palette.getVibrantSwatch();
        Palette.Swatch colorVibDarSwatch = palette.getDarkVibrantSwatch();
        Palette.Swatch colorVibLigSwatch = palette.getLightVibrantSwatch();
        Palette.Swatch colorMuSwatch = palette.getMutedSwatch();
        Palette.Swatch colorMuDarSwatch = palette.getDarkMutedSwatch();
        Palette.Swatch colorMulLigSwatch = palette.getLightMutedSwatch();
        /*int colorVibrant = colorVibSwatch.getRgb();
        int colorVibrantDark = colorVibDarSwatch.getRgb();
        int colorVibrantLight = colorVibLigSwatch.getRgb();
        int colorMuted = colorMuSwatch.getRgb();
        int colorMutedDark = colorMuDarSwatch.getRgb();
        int colorMuteLight = colorMulLigSwatch.getRgb();*/
        ArrayList< Palette.Swatch> colorList = new ArrayList<>();
        colorList.add(colorVibSwatch);
        colorList.add(colorVibDarSwatch);
        colorList.add(colorVibLigSwatch);
        colorList.add(colorMuSwatch);
        colorList.add(colorMuDarSwatch);
        colorList.add(colorMulLigSwatch);
        listView.setAdapter(new MyAdapter(colorList));

    }

    class MyAdapter extends BaseAdapter {
        List<Palette.Swatch> list;

        public MyAdapter(List<Palette.Swatch> list) {
            this.list = list;
        }

        @Override

        public int getCount() {
            return list.size();
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
            if (convertView == null) {
                convertView = LayoutInflater.from(PetelleTestActivity.this).inflate(R.layout.item01, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.tv_content);
            if (position < list.size() && list.get(position)!=null) {
                textView.setBackgroundColor(list.get(position).getRgb());
                textView.setTextColor(list.get(position).getBodyTextColor());
            }else {
                textView.setText("default");
            }
            textView.setText(getContent(position));
            return convertView;
        }
    }

    public String getContent(int position) {
        String textStr = null;
        switch (position) {
            case 0:
                textStr = "colorVibrant";
                break;
            case 1:
                textStr = "colorVibrantDark";
                break;
            case 2:
                textStr = "colorVibrantLight";
                break;
            case 3:
                textStr = "colorMuted";
                break;
            case 4:
                textStr = "colorMutedDark";
                break;
            case 5:
                textStr = "colorMuteLight";
                break;
            default:
                textStr = "default";
                break;

        }
        return textStr;
    }
}
