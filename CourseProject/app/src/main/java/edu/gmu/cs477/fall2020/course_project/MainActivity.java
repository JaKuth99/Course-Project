package edu.gmu.cs477.fall2020.course_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.util.Calendar;
import android.app.AlertDialog;


public class MainActivity extends AppCompatActivity {
    TextView txtDisplayDate;
    CalendarView calendar;
    int curDay;
    int curMonth;
    int curYear;

    public final static String NAME_LIST = "edu.gmu.cs477.mainActivity.NAME_LIST";
    public final static String REQUEST_TYPE = "edu.gmu.cs477.mainActivity.REQUEST_TYPE";
    public final static int ACTIVITY_RESULT = 1;

    public final static int ADD = 0;
    public final static int EDIT = 1;

    public final static String SELECTED_DAY = "edu.gmu.cs477.mainActivity.SELECTED_DAY";
    public final static String SELECTED_MONTH = "edu.gmu.cs477.mainActivity.SELECTED_MONTH";
    public final static String SELECTED_YEAR = "edu.gmu.cs477.mainActivity.SELECTED_YEAR";

    private ListView foodList;
    SimpleCursorAdapter adapter;

    protected SQLiteDatabase db = null;
    DatabaseOpenHelper dbHelper = null;
    Cursor c;



    final static String TABLE_FOOD = "tblFoods";
    final static String F_NAME=  "name";
    final static String _ID = "_id";
    final static String F_BRAND = "brand";
    final static String F_CAL = "calories";
    final static String F_SERVING = "serving";
    //put the photo here
    final static String[] tblFoodColumns = {_ID, F_NAME, F_BRAND, F_CAL, F_SERVING};

    final static String TABLE_LOGGED = "tblLoggedFood";
    final static String LOGGED_ID = "_loggedID";
    final static String F_ID =  "FoodID";
    final static String M_ID= "_id";
    final static String YEAR = "brand";
    final static String DAYOFMONTH= "calories";
    final static String NOTES = "fat_calories";
    final static String FOODTYPE = "serving";
    //put the photo here
    final static String[] tblLoggedColumns = {LOGGED_ID, F_ID, M_ID, YEAR, DAYOFMONTH, NOTES, FOODTYPE};

    final static String TABLE_MONTH= "tblLoggedFood";
    final static String MONTH_ID = "_monthID";
    final static String MONTH_NAME =  "name";
    //put the photo here
    final static String[] tblMonthColumns = {MONTH_ID, MONTH_NAME};


    final static String TABLE_MEALTYPE ="tblMealType";
    final static String TYPE_ID = "_typeID";
    final static String TYPE_NAME =  "name";
    //put the photo here
    final static String[] tblTypeColumns = {TYPE_ID, TYPE_NAME};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtDisplayDate = (TextView)(findViewById(R.id.txtDisplayDate));

        //get the current date to display
        Calendar rightNow = Calendar.getInstance();
        curDay = rightNow.get(Calendar.DAY_OF_MONTH);
        curMonth = rightNow.get(Calendar.MONTH) + 1;
        curYear = rightNow.get(Calendar.YEAR);
        String currentDate = curMonth + "/" + curDay + "/" + curYear;

        txtDisplayDate.setText("Food Logged for " + currentDate);

        calendar = (CalendarView)(findViewById(R.id.calendarView));
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                curYear = year;
                curMonth = month + 1;
                curDay = dayOfMonth;
                txtDisplayDate.setText("Food Logged for " + curMonth + "/" + curDay + "/" + curYear);
            }
        });

        dbHelper = new DatabaseOpenHelper(this);

        foodList = (ListView) findViewById(R.id.allFoodList);

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get database
                db = dbHelper.getWritableDatabase();

                //get cursor for position
                Cursor q = (Cursor) parent.getAdapter().getItem(position);
                /*
                //extract data from cursor
                String exe_name = q.getString(1);
                int exe_reps = q.getInt(2);
                int exe_sets = q.getInt(3);
                String exe_weights = q.getString(4);
                String exe_notes = q.getString(5);

                //build intent with data to display in edit screen
                Intent intent = new Intent(getApplicationContext(), AddEditWorkouts.class);
                intent.putExtra(EXE_NAME, exe_name);
                intent.putExtra(EXE_REPS, exe_reps);
                intent.putExtra(EXE_SETS, exe_sets);
                intent.putExtra(EXE_WEIGHTS, exe_weights);
                intent.putExtra(EXE_NOTES, exe_notes);
                intent.putExtra(REQUEST_TYPE, AddEditWorkouts.EDIT);
                startActivityForResult(intent, ACTIVITY_RESULT);

                 */


            };
        });

    }

    public void onAddNew(View v){//go to new food page
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.prompt_on_add, null);
        AlertDialog.Builder alertdialog =new AlertDialog.Builder(MainActivity.this);
        alertdialog.setView(promptView);
        alertdialog.setCancelable(true)
                .setNegativeButton("Create New Entry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Add_Edit_Activity.class);
                        intent.putExtra(REQUEST_TYPE, ADD);
                        intent.putExtra(SELECTED_DAY, curDay);
                        intent.putExtra(SELECTED_YEAR, curYear);
                        intent.putExtra(SELECTED_MONTH, curMonth);
                        startActivity(intent);
                    }
                })
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertdialog.create();
        alertdialog.show();




    }

    public void onEditFood(View v) {
        Intent intent = new Intent(this, Add_Edit_Activity.class);
        intent.putExtra(REQUEST_TYPE, EDIT);
        //send data associated with the entry that should be edited
            //go to FoodLogged -> get meal type and date and notes and food ID
            //go to Food table with ID, get name, brand, serving size, calories
        startActivity(intent);
    }
}
