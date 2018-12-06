package com.schedulesmadeeasy.schedular;

import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ManagerAssignShift extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;
    private String mID;
    private String mTitle;
    private ArrayList<String> names;
    private final static String TAG = "ASSIGN SHIFTS";

    /**
     * Spinners for availability
     */
    private Spinner mSpinner1;
    private Spinner mSpinner2;
    private Spinner mSpinner3;
    private Spinner mSpinner4;
    private Spinner mSpinner5;
    private Spinner mSpinner6;
    private Spinner mSpinner7;
    private Spinner mSpinner8;
    private Spinner mSpinner9;
    private Spinner mSpinner10;
    private ArrayAdapter<String> mAdapter1;
    private ArrayAdapter<String> mAdapter2;
    private ArrayAdapter<String> mAdapter3;
    private ArrayAdapter<String> mAdapter4;
    private ArrayAdapter<String> mAdapter5;
    private ArrayAdapter<String> mAdapter6;
    private ArrayAdapter<String> mAdapter7;
    private ArrayAdapter<String> mAdapter8;
    private ArrayAdapter<String> mAdapter9;
    private ArrayAdapter<String> mAdapter10;

    Map<String,String> mIDtoName;
    Map<String,String> mIDtoAvailability;
    Handler mHandler;
    TextView mAvailability;


    /**
     * What happens when the function is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_assign_shift);

        names = new ArrayList<>();
        names.add("None");
        mIDtoName = new TreeMap<>();
        mIDtoAvailability = new TreeMap<>();
        mAvailability = findViewById(R.id.allAvailabilityTextView);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mID = extras.getString("ID");
            mTitle = extras.getString("TITLE");
        }
        String reference = "groups/" + mID + "/members";
        mRef = FirebaseDatabase.getInstance().getReference(reference);


        mSpinner1 = findViewById(R.id.nameSpinner1);
        mAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner1, mAdapter1);

        mSpinner2 = findViewById(R.id.nameSpinner2);
        mAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner2, mAdapter2);

        mSpinner3 = findViewById(R.id.nameSpinner3);
        mAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner3, mAdapter3);

        mSpinner4 = findViewById(R.id.nameSpinner4);
        mAdapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner4, mAdapter4);

        mSpinner5 = findViewById(R.id.nameSpinner5);
        mAdapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner5, mAdapter5);

        mSpinner6 = findViewById(R.id.nameSpinner6);
        mAdapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner6, mAdapter6);

        mSpinner7 = findViewById(R.id.nameSpinner7);
        mAdapter7 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner7, mAdapter7);

        mSpinner8 = findViewById(R.id.nameSpinner8);
        mAdapter8 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner8, mAdapter8);

        mSpinner9 = findViewById(R.id.nameSpinner9);
        mAdapter9 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner9, mAdapter9);

        mSpinner10 = findViewById(R.id.nameSpinner10);
        mAdapter10 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        initializeSpinner(mSpinner10, mAdapter10);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "ADDING: " + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                String nameKey = "users/" + key;
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(nameKey);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG, "KEY:" + dataSnapshot.getKey());
                        String fName = dataSnapshot.child("firstName").getValue(String.class);
                        String lName = dataSnapshot.child("lastName").getValue(String.class);
                        String id = dataSnapshot.child("userId").getValue(String.class);
                        String name = String.format("%s %s", fName, lName);
                        Log.d(TAG, name);
                        mIDtoName.put(id, name);

                        names.add(name);
                        //mAdapter1.add(name);
                        mAdapter1.notifyDataSetChanged();
                        mAdapter2.notifyDataSetChanged();
                        mAdapter3.notifyDataSetChanged();
                        mAdapter4.notifyDataSetChanged();
                        mAdapter5.notifyDataSetChanged();
                        mAdapter6.notifyDataSetChanged();
                        mAdapter7.notifyDataSetChanged();
                        mAdapter8.notifyDataSetChanged();
                        mAdapter9.notifyDataSetChanged();
                        mAdapter10.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

        String refAvailability = "groups/" + mID + "/availability";
        DatabaseReference availRef = FirebaseDatabase.getInstance().getReference(refAvailability);
        availRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                String value = "";
                value += "Monday: " + dataSnapshot.child("Monday").getValue(String.class) + "\n";
                value += "Tuesday: " + dataSnapshot.child("Tuesday").getValue(String.class) + "\n";
                value += "Wednesday: " + dataSnapshot.child("Wednesday").getValue(String.class) + "\n";
                value += "Thursday: " + dataSnapshot.child("Thursday").getValue(String.class) + "\n";
                value += "Friday: " + dataSnapshot.child("Friday").getValue(String.class) + "\n";

                Log.d(TAG, "Value:\n" + value);
                mIDtoAvailability.put(key, value);
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

        mHandler = new Handler();
        updateTime();
    }

    /**
     * Update the time of availability
     */
    Runnable updater;
    void updateTime(){
        updater = new Runnable() {
            @Override
            public void run() {
                String availability = "";
                for (Map.Entry<String, String> entry :
                        mIDtoName.entrySet()){
                    if(mIDtoAvailability.containsKey(entry.getKey())){
                        availability += String.format("%s\n%s", entry.getValue(), mIDtoAvailability.get(entry.getKey()));
                    }
                }
                if(!availability.isEmpty()){
                    mAvailability.setText(availability);
                }
                mHandler.postDelayed(updater, 1000);
            }
        };
        mHandler.post(updater);
    }

    /**
     * Initialization of the spinner
     * @param spin
     * @param adapter
     */
    private void initializeSpinner(Spinner spin, ArrayAdapter adapter){
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    /**
     * Three bar
     * @param arg0
     * @param arg1
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(), time[position], Toast.LENGTH_LONG).show();
    }

    /**
     * Default view
     * @param arg0
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * When the function is destroyed
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacks(updater);
    }



}
