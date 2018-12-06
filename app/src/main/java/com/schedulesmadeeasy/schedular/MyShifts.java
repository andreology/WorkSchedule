package com.schedulesmadeeasy.schedular;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyShifts extends AppCompatActivity {
    //declaring variables
    private DrawerLayout mDrawerLayout;
    private List<Shift> shifts;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;
    private final String TAG = "MYSHIFTS";


//On create function for when the page is created what to execute
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shifts);
        rv = findViewById(R.id.shift_recycler_view);
        Log.d(TAG, "STARTING LAYOUT MANAGER");
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initializeData();
        initializeAdapter();
        Log.d(TAG, "ENDING ADAPTER STUFF");


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String reference = "users/" + mUser.getUid() + "/shifts";
        mRef = FirebaseDatabase.getInstance().getReference(reference);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               /** Group newGroup = dataSnapshot.getValue(Group.class);
                System.out.println(newGroup.getTitle());
                shifts.add(0, newGroup);
                rv.getAdapter().notifyItemInserted(0);
                */
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //navigation view, defines what populates on three bar menu
        final NavigationView navigationView = findViewById(R.id.nav_view_my_shifts);
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
        mDrawerLayout = findViewById(R.id.drawer_layout_my_shifts);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_my_shifts);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.title_my_shifts);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);



    }


//initialize arraylist
    private void initializeData() {
        shifts = new ArrayList<>();
    }

    private void initializeAdapter() {
        ShiftRVAdapter adapter = new ShiftRVAdapter(shifts,this);
        rv.setAdapter(adapter);
    }

    //Define what happens when three bar is selected
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
