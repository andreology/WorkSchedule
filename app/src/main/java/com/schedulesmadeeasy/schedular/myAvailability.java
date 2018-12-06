package com.schedulesmadeeasy.schedular;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myAvailability extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private String mID;
    private String mTitle;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    String[] time = {"N/A", "12:00 A.M.", "12:30 A.M.", "1:00 A.M.", "1:30 A.M.", "2:00 A.M.", "2:30 A.M.", "3:00 A.M.", "3:30 A.M.", "4:00 A.M.",
            "4:30 A.M.", "5:00 A.M.", "5:30 A.M.", "6:00 A.M.", "6:30 A.M.", "7:00 A.M.", "7:30 A.M.", "8:00 A.M.", "8:30 A.M.", "9:00 A.M.",
            "9:30 A.M.", "10:00 A.M.", "10:30 A.M.", "11:00 A.M.", "11:30 A.M.",
            "12:00 P.M.", "12:30 P.M.", "1:00 P.M.", "1:30 P.M.", "2:00 P.M.", "2:30 P.M.", "3:00 P.M.", "3:30 P.M.", "4:00 P.M.",
            "4:30 P.M.", "5:00 P.M.", "5:30 P.M.", "6:00 P.M.", "6:30 P.M.", "7:00 P.M.", "7:30 P.M.", "8:00 P.M.", "8:30 P.M.", "9:00 P.M.",
            "9:30 P.M.", "10:00 P.M.", "10:30 P.M.", "11:00 P.M.", "11:30 P.M."
    };
    Button confirmButton;

    /**
     * What happens when functino created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_availability);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mID = extras.getString("ID");
            mTitle = extras.getString("TITLE");
        }
        // Where to write the data to
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String reference = "groups/" + mID + "/availability/" + mUser.getUid();
        mRef = FirebaseDatabase.getInstance().getReference(reference);


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        // Spinner 1
        final Spinner spin = (Spinner) findViewById(R.id.timeSpinner);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        // spinner 2
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin2 = (Spinner) findViewById(R.id.timeSpinner2);
        spin2.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(aa2);

        // spinner 3
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin3 = (Spinner) findViewById(R.id.timeSpinner3);
        spin3.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin3.setAdapter(aa3);

        // spinner 4
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin4 = (Spinner) findViewById(R.id.timeSpinner4);
        spin4.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin4.setAdapter(aa4);

        // spinner 5
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin5 = (Spinner) findViewById(R.id.timeSpinner5);
        spin5.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin5.setAdapter(aa5);

        // spinner 6
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin6 = (Spinner) findViewById(R.id.timeSpinner6);
        spin6.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa6 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin6.setAdapter(aa6);

        // spinner 7
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin7 = (Spinner) findViewById(R.id.timeSpinner7);
        spin7.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa7 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin7.setAdapter(aa7);

        // spinner 8
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin8 = (Spinner) findViewById(R.id.timeSpinner8);
        spin8.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa8 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin8.setAdapter(aa8);

        // spinner 9
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin9 = (Spinner) findViewById(R.id.timeSpinner9);
        spin9.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa9 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin9.setAdapter(aa9);

        // spinner 9
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin10 = (Spinner) findViewById(R.id.timeSpinner10);
        spin10.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa10 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        aa10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin10.setAdapter(aa10);

        //Confirm Button Creation
        confirmButton = findViewById(R.id.confirmButtonMyAvailability);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String mondayStart = spin.getSelectedItem().toString();
                String mondayEnd = spin10.getSelectedItem().toString();
                //Toast.makeText(myAvailability.this, "Monday: " + mondayStart + " - " + mondayEnd,
                //        Toast.LENGTH_SHORT).show();
                mRef.child("Monday").setValue(mondayStart + "-" + mondayEnd);

                String tuesdayStart = spin3.getSelectedItem().toString();
                String tuesdayEnd = spin2.getSelectedItem().toString();
                //Toast.makeText(myAvailability.this, "Tuesday: " + tuesdayStart + " - " + tuesdayEnd,
                //        Toast.LENGTH_SHORT).show();
                mRef.child("Tuesday").setValue(tuesdayStart + "-" + tuesdayEnd);

                String wednesdayStart = spin5.getSelectedItem().toString();
                String wednesdayEnd = spin6.getSelectedItem().toString();
                //Toast.makeText(myAvailability.this, "Wednesday: " + wednesdayStart + " - " + wednesdayEnd,
                //        Toast.LENGTH_SHORT).show();
                mRef.child("Wednesday").setValue(wednesdayStart + "-" + wednesdayEnd);

                String thursdayStart = spin4.getSelectedItem().toString();
                String thursdayEnd = spin7.getSelectedItem().toString();
                //Toast.makeText(myAvailability.this, "Thursday: " + thursdayStart + " - " + thursdayEnd,
                //        Toast.LENGTH_SHORT).show();
                mRef.child("Thursday").setValue(thursdayStart + "-" + thursdayEnd);

                String fridayStart = spin9.getSelectedItem().toString();
                String fridayEnd = spin8.getSelectedItem().toString();
                //Toast.makeText(myAvailability.this, "Friday: " + fridayStart + " - " + fridayEnd,
                //        Toast.LENGTH_SHORT).show();
                mRef.child("Friday").setValue(fridayStart + "-" + fridayEnd);
                Intent confirm = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(confirm);
            }

        });

/**
 * Navigation view that populate three bar
 */
        final NavigationView navigationView = findViewById(R.id.nav_view_my_availability);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()

                {
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
        mDrawerLayout = findViewById(R.id.drawer_layout_my_availability);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_my_availability);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.title_my_availability);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


    }

    /**
     *
     Performing action onItemSelected and onNothing selected
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(), time[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * Three bar menu
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
