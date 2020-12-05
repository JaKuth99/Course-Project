package edu.gmu.cs477.fall2020.course_project;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        String calorie_amount="";
        DatabaseOpenHelper dbHelper= new DatabaseOpenHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        long millis=System.currentTimeMillis();
        java.util.Date date=new java.util.Date(millis);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String query="select sum(F_CAL) from tblFood NATURAL JOIN tblLoggedFood where YEAR='"+year+"' AND MONTH_ID='"+month+"' AND DAYOFMONTH='"+day+"'";
        Cursor cursor = db.rawQuery(query , null) ;
        if (cursor.moveToFirst()) {
            calorie_amount = cursor.getString(0);
        }
        cursor.close();
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.calorie_number, calorie_amount);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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
}

