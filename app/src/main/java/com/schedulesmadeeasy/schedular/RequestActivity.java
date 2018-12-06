package com.schedulesmadeeasy.schedular;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;

public class RequestActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private DrawerLayout mDrawerLayout;

    /**
     * Buttons needed in function
     */
    Button End_Date_Button;
    Button Start_Date_Button;
    TextView Start_Date_View;
    TextView End_Date_View;
    String startDate, endDate;
    int choice = 0;

    int day, month, year, hour, minute;
    String dayName;
    int dayOfWeek;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;


    /**
     * What happens when the function is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Start_Date_Button = (Button) findViewById(R.id.Start_Date_Button);
        End_Date_Button = (Button) findViewById(R.id.End_Date_Button);
        Start_Date_View = (TextView) findViewById(R.id.Start_Date_View);
        End_Date_View = (TextView) findViewById(R.id.End_Date_View);
        Start_Date_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                choice = 1;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestActivity.this, RequestActivity.this, year, month, day);
                datePickerDialog.show();
            }

        });
        End_Date_Button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                choice = 2;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestActivity.this, RequestActivity.this, year, month, day);
                datePickerDialog.show();
            }

        });


/**
 * Populate three bar menu
 */
        final NavigationView navigationView = findViewById(R.id.nav_view_request);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (item.getItemId()) {
                            case R.id.my_shifts:
                                Intent myShiftsPage = new Intent(getApplicationContext(), MyShifts.class);
                                startActivity(myShiftsPage);
                                break;

                            case R.id.action_settings:

                                Intent settingsPage = new Intent(getApplicationContext(), MySettingsActivity.class);
                                startActivity(settingsPage);
                                break;


                            case R.id.my_groups:
                                Intent groupPage = new Intent(getApplicationContext(), HomePageActivity.class);
                                startActivity(groupPage);
                                break;


                            case R.id.my_availability:
                                Intent availabilityPage = new Intent(getApplicationContext(), myAvailability.class);
                                startActivity(availabilityPage);
                                break;

                            case R.id.requests:
                                Intent requestsPage = new Intent(getApplicationContext(), RequestActivity.class);
                                startActivity(requestsPage);
                                break;


                        }
                        return true;
                    }
                }
        );


        //GETTING TOOLBAR
        mDrawerLayout = findViewById(R.id.drawer_layout_request);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_request);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.title_requests);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);



    }

    /**
     * Set date
     * @param datePicker
     * @param i
     * @param i1
     * @param i2
     */
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
        {
            yearFinal = i;
            monthFinal = i1 + 1;
            dayFinal = i2;

            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(RequestActivity.this, RequestActivity.this, hour, minute, DateFormat.is24HourFormat(this));
            timePickerDialog.show();
        }

    /**
     * Set time
     * @param timePicker
     * @param i
     * @param i1
     */
        public void onTimeSet(TimePicker timePicker, int i, int i1)
        {
            Calendar c = Calendar.getInstance();
            hourFinal = i;
            minuteFinal = i1;
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            switch(dayOfWeek)
            {
                case 1:
                    dayName = "Monday";
                    break;
                case 2:
                    dayName = "Tuesday";
                    break;
                case 3:
                    dayName = "Wednesday";
                    break;
                case 4:
                    dayName = "Thursday";
                    break;
                case 5:
                    dayName = "Friday";
                    break;
                case 6:
                    dayName = "Saturday";
                    break;
                case 7:
                    dayName = "Sunday";
                    break;
                default:
                    break;

            }
            if (choice == 1) {
                Start_Date_View.setText(dayName + " , " + dayFinal + " " + yearFinal + "\n" + hourFinal + ":" + minuteFinal);

                startDate = "" + dayFinal + hourFinal + minuteFinal;

                Toast.makeText(RequestActivity.this, startDate, Toast.LENGTH_SHORT).show();
            }
            else if (choice == 2)
            {
                End_Date_View.setText(dayName + " , " + dayFinal + " " + yearFinal + "\n" + hourFinal + ":" + minuteFinal);

                endDate = "" + dayFinal + hourFinal + minuteFinal;

                Toast.makeText(RequestActivity.this, endDate, Toast.LENGTH_SHORT).show();
            }


        }


    /**
     * Pull up three bar menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //USER CHOSE SETTINGS ITEM, CHANGE TO APP SETTINGS SCREEN
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                //USER'S ACTION WAS NOT RECOGNIZED.
                //INVOKE THE SUPERCLASS TO HANDLE IT.
                return super.onOptionsItemSelected(item);
        }
    }

    }

