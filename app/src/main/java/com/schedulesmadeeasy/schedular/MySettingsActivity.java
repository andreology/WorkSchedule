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
import android.view.WindowManager;

public class MySettingsActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    /**
     * What happens when function is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

/**
 * Populate three bar menu
 */
        final NavigationView navigationView = findViewById(R.id.nav_view_my_settings);
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
        mDrawerLayout = findViewById(R.id.drawer_layout_my_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_my_settings);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.title_setttings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


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
