package edu.gmu.cs477.fall2020.course_project;


import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static edu.gmu.cs477.fall2020.course_project.MainActivity.BREAKFAST;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.DAYOFMONTH;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.DINNER;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.F_BRAND;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.F_CAL;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.F_NAME;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.F_SERVING;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.LUNCH;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.MEALTYPE;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.MONTH_ID;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.NOTES;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.SNACK;
import static edu.gmu.cs477.fall2020.course_project.MainActivity.YEAR;
import static edu.gmu.cs477.fall2020.course_project.MainActivity._ID;

public class Add_Edit_Activity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private TextView title;
    private Button btnEditDate;
    private Button btnCreate_Save;
    private int requestType;

    int currentFoodID;

    int selectedDay;
    int selectedMonth;
    int selectedYear;
    int selectedMealType;

    EditText inputName;
    EditText inputBrand;
    EditText inputCalories;
    EditText inputServings;
    EditText inputNotes;

    RadioButton rbBreakfast;
    RadioButton rbLunch;
    RadioButton rbDinner;
    RadioButton rbSnack;

    int changeAll = 2; //default to 2 - change a single entry

    String orgfName;
    String orgfBrand;
    String orgfCals;
    String orgfServing;

    int selectedLogEntryId;

    CheckBox checked;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__edit_);

        inputName = (EditText) findViewById(R.id.etInputName);
        inputBrand = (EditText) findViewById(R.id.etInputBrand);
        inputCalories = (EditText) findViewById(R.id.etInputCalories);
        inputServings = (EditText) findViewById(R.id.etInputServing);
        inputNotes = (EditText) findViewById(R.id.etInputNotes);

        mDisplayDate = (TextView) findViewById(R.id.txtDate);
        title = (TextView) findViewById(R.id.txtTitleAdd);
        btnEditDate = findViewById(R.id.btnEditDate);
        btnCreate_Save = findViewById(R.id.btnCreateSave);

        rbBreakfast = (RadioButton) findViewById(R.id.rbBreakfast);
        rbLunch = (RadioButton) findViewById(R.id.rbLunch);
        rbDinner= (RadioButton) findViewById(R.id.rbDinner);
        rbSnack = (RadioButton) findViewById(R.id.rbSnack);

        checked = (CheckBox) findViewById(R.id.checkbox);
        checked.setChecked(false);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();
        requestType = intent.getIntExtra(MainActivity.REQUEST_TYPE, MainActivity.ADD);
        selectedDay = intent.getIntExtra(MainActivity.SELECTED_DAY, day);
        selectedMonth = intent.getIntExtra(MainActivity.SELECTED_MONTH, month);
        selectedYear = intent.getIntExtra(MainActivity.SELECTED_YEAR, year);
        btnCreate_Save.setText("CREATE");
        title.setText("Add New");
        mDisplayDate.setText(selectedMonth + "/" + selectedDay + "/" + selectedYear);
        if (requestType == MainActivity.EDIT){
            btnCreate_Save.setText("SAVE");
            title.setText("Edit Food Item");
            currentFoodID = intent.getExtras().getInt(_ID, -1);
            if(currentFoodID != -1) {
                selectedLogEntryId = intent.getExtras().getInt(LOGGED_ID);
                String fName = intent.getExtras().getString(F_NAME);
                String fBrand = intent.getExtras().getString(F_BRAND);
                String fCal = intent.getExtras().getString(F_CAL);
                String fServings = intent.getExtras().getString(F_SERVING);
                int mealType = intent.getExtras().getInt(MEALTYPE);
                System.out.println("we got this meal type: " + mealType);
                String mNotes = intent.getExtras().getString(NOTES);
                //set the edit texts
                inputName.setText(fName);
                inputBrand.setText(fBrand);
                inputCalories.setText(fCal);
                inputServings.setText(fServings);
                inputNotes.setText(mNotes);

                switch (mealType) {
                    case BREAKFAST:
                        rbBreakfast.setChecked(true);
                        break;

                    case LUNCH:
                        rbLunch.setChecked(true);
                        break;

                    case DINNER:
                        rbDinner.setChecked(true);
                        break;

                    case SNACK:
                        rbSnack.setChecked(true);
                        break;
                }
            }else{
                Toast.makeText(getApplicationContext(), "Error Loading Item", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_CANCELED);
                finish();
            }

        }

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
                    selectedMealType = MainActivity.BREAKFAST;
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

    public void onSave(View v){
            String fName = inputName.getText() != null ? inputName.getText().toString() : "";
            if (fName.equals("")) {
                //show error message
                Toast.makeText(getApplicationContext(), "Name is required", Toast.LENGTH_SHORT).show();
                //return
                return;
            }

            int fCalories = inputCalories.getText().toString().equals("") ? 0 : Integer.parseInt(inputCalories.getText().toString());
            String fBrand = inputBrand.getText().toString();
            String fServing = inputServings.getText().toString();
            String mNotes = inputNotes.getText().toString();

            Intent intent = new Intent();
            intent.putExtra(LOGGED_ID, selectedLogEntryId);
            intent.putExtra(_ID, currentFoodID);
            intent.putExtra(F_NAME, fName);
            intent.putExtra(F_BRAND, fBrand);
            intent.putExtra(F_CAL, fCalories);
            intent.putExtra(F_SERVING, fServing);
            intent.putExtra(MEALTYPE, selectedMealType);
            intent.putExtra(NOTES, mNotes);
            intent.putExtra(DAYOFMONTH, selectedDay);
            intent.putExtra(MONTH_ID, selectedMonth);
            intent.putExtra(YEAR, selectedYear);

            if (requestType == MainActivity.ADD) {

                //call addExercise
                intent.putExtra("requestType", MainActivity.ADD);
                setResult(Activity.RESULT_OK, intent);
                finish();
                //add check here that no duplicate names and that
            } else {

                setChangeAll(MainActivity.CHANGE_SINGE_ENTRY);
                //update entry
                if (!fName.equals(orgfName) || !fBrand.equals(orgfBrand) || !(fCalories == Integer.parseInt(orgfCals)) || !fServing.equals(orgfServing)) {
                    //prompt
                    if (checked.isChecked()){
                        setChangeAll(MainActivity.CHANGE_ALL_ENTRIES);
                    }else{
                        setChangeAll(MainActivity.CHANGE_SINGE_ENTRY);
                    }



                }
                intent.putExtra("updateSettings", changeAll);
                intent.putExtra("requestType", MainActivity.EDIT);
                setResult(Activity.RESULT_OK, intent);
                finish();




            }




            }

            public void setChangeAll(int ans) {
                changeAll = ans;

            }



    }

