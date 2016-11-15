package com.example.majinxin1.emptylistview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    public static final String CLICK_EXAMLPE_ACTION = "com.example.majinxin1.emptylistview_TWO";
    public static final String LISTVIEW_ACTION = "com.example.majinxin1.emptylistview_LISTVIEW";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

        Intent two_click = new Intent().setAction(CLICK_EXAMLPE_ACTION);
        final int num=appWidgetId;
        two_click.putExtra("widget_id", num);
        PendingIntent two_pending = PendingIntent.getBroadcast(context, 0, two_click, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text2, two_pending);

        Intent mmintent = new Intent(context, WidgetService.class);
        mmintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.appwidget_text3, mmintent);
        // Instruct the widget manager to update the widget
        
        Log.i("putExtra appWidgetId", "appWidgetId=" + appWidgetId);
        Intent gridIntent = new Intent();
        gridIntent.setAction(LISTVIEW_ACTION);
        gridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntentvv = PendingIntent.getBroadcast(context, 0, gridIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置intent模板
        views.setPendingIntentTemplate(R.id.appwidget_text3, pendingIntentvv);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Log.i("appWidgetId", "appWidgetId=" + appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();
        if (action.equals(LISTVIEW_ACTION)) {
            // 接受“gridview”的点击事件的广播
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(WidgetService.EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + viewIndex+"  app_widget_id="+appWidgetId, Toast.LENGTH_SHORT).show();
        }
        if (action.equals(CLICK_EXAMLPE_ACTION)) {
            int id=intent.getIntExtra("widget_id", 0);
            Toast.makeText(context, "Click Button widget_id=" + id, Toast.LENGTH_SHORT).show();
        }
    }
}

