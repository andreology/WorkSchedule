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
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that inflates layout listing all the groups the user belongs to.
 * @author Andre Barajas
 */
public class HomePageActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<Group> groups;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;
    private TextView mTitle;
    private final static String TAG = "MYGROUPS";

    /**
     * Initializes user data and inflates layout. Also sets up user authentication.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);//GETTING RECYCLER VIEW LIST
        rv = findViewById(R.id.group_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        //initializeData();
        initializeData();
        initializeAdapter();

        mTitle = findViewById(R.id.titleTextView);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String reference = "users/" + mUser.getUid() + "/groups";
        mRef = FirebaseDatabase.getInstance().getReference(reference);

        if(groups.isEmpty()){
            mTitle.setText(R.string.empty_groups);
            Log.d(TAG, "EMPTY");
        }

        //ADDS LISTENER FOR DATABASE
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group newGroup = dataSnapshot.getValue(Group.class);
                System.out.println(newGroup.getTitle());
                groups.add(0, newGroup);
                rv.getAdapter().notifyItemInserted(0);
                mTitle.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Group modifiedGroup = dataSnapshot.getValue(Group.class);
                int index = -1;
                for (int i = 0; i < groups.size(); i++) {
                    if(modifiedGroup.getId().equals(groups.get(i).getId())){
                        index = i;
                    }
                }
                if(index >= 0){
                    groups.get(index).setMembers(modifiedGroup.getMembers());
                    rv.getAdapter().notifyItemChanged(index);
                }
                
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


        //SETS UP NAVIGATION VIEW
        final NavigationView navigationView = findViewById(R.id.nav_view);
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
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.title_my_groups);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddGroupActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Creates group.
     */
    private void initializeData() {
        groups = new ArrayList<>();
    }

    /**
     * Creates adapter.
     */
    private void initializeAdapter() {
        GroupRVAdapter adapter = new GroupRVAdapter(groups, this);
        rv.setAdapter(adapter);
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page_activity_menu, menu);
        return true;
    }*/

    /**
     * Opens up navigation menu on clicked.
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
