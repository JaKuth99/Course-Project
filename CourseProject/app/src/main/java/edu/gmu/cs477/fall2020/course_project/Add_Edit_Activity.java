package edu.gmu.cs477.courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.Calendar;

public class Add_Edit_Activity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private Button btnEditDate;

    private static int BREAKFAST = 0;
    private static int LUNCH = 1;
    private static int DINNER = 2;
    private static int SNACK = 3;


    int selectedDay;
    int selectedMonth;
    int selectedYear;
    int selectedMealType;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__edit_);

        mDisplayDate = (TextView) findViewById(R.id.txtDate);
        btnEditDate = findViewById(R.id.btnEditDate);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();
        selectedDay = intent.getIntExtra(MainActivity.SELECTED_DAY, day);
        selectedMonth = intent.getIntExtra(MainActivity.SELECTED_MONTH, month);
        selectedYear = intent.getIntExtra(MainActivity.SELECTED_YEAR, year);

        mDisplayDate.setText(selectedMonth + "/" + selectedDay + "/" + selectedYear);


        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month  + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }

    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.rbBreakfast:
                if (checked)
                    selectedMealType = BREAKFAST;
                break;
            case R.id.rbLunch:
                if (checked)
                    selectedMealType = LUNCH;

                break;
            case R.id.rbDinner:
                if (checked)
                    selectedMealType = DINNER;

                break;
            case R.id.rbSnack:
                if (checked)
                    selectedMealType = SNACK;

                break;
        }
    }

    public void changeDate(View view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                Add_Edit_Activity.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog,
                mDateSetListener,
                selectedYear, selectedMonth - 1, selectedDay);
        dialog.show();
    }

    public void onCancel(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}