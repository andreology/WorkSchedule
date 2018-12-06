package com.schedulesmadeeasy.schedular;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity for setting up initial information relating to your user.
 * @author Andre Barajas
 * */
public class SetUpActivity extends AppCompatActivity {
    private static final String TAG = "SETUP";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Button mButton;
    private EditText mUserName;
    private EditText mFirstName;
    private EditText mLastName;
    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        mButton = findViewById(R.id.setup_button);
        mUserName = findViewById(R.id.editTextUserNameSetup);
        mFirstName = findViewById(R.id.editTextFirstNameSetup);
        mLastName = findViewById(R.id.editTextLastNameSetup);

        /*
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Boats").build();
        mUser.updateProfile(profileUpdates);
        */

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateForm()){
                   return;
                }
                addUserInfo();
            }
        });
    }

    /**
     * Adds user info to user information in database if it's valid.
     */
    private void addUserInfo(){
        final String user_name = mUserName.getText().toString().trim();
        String first_name = mFirstName.getText().toString().trim();
        String last_name = mLastName.getText().toString().trim();
        //Updating Name for User

        String id = mUser.getUid();
        User user = new User(user_name, mUser.getEmail(), first_name, last_name, id);
        //ADDING USER TO DATABASE
        databaseUsers.child(id).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null){
                    Log.d(TAG, "UNABLE TO POST: " + databaseError);
                    Toast.makeText(SetUpActivity.this, "Username is taken.",
                            Toast.LENGTH_LONG).show();
                    mUserName.setText("");
                }else{
                    Toast.makeText(SetUpActivity.this, "WRITTEN WOOOO", Toast.LENGTH_SHORT).show();
                    //EDITING PROFILE DISPLAY NAME TO USER NAME
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(user_name).build();
                    mUser.updateProfile(profileUpdates);
                    //WRITING USERNAME TO LIST OF TAKEN USERNAMES
                    writeUsername();

                    Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    /**
     * Adds username to available databases.
     */
    private void writeUsername(){
        String user_name = mUserName.getText().toString().trim();
        DatabaseReference databaseUsernames = FirebaseDatabase.getInstance().getReference("usernames");

        databaseUsernames.child(user_name).setValue(mUser.getUid(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null){
                    Log.d(TAG,"Some shit happened: " + databaseError.getMessage());
                }else{
                    Log.d(TAG, "WRITING TO USERNAME DATABASE");
                }
            }
        });

    }

    /**
     * Validates input to see if it's error free or non-empty.
     * @return
     */
    private boolean validateForm(){
        boolean valid = true;
        String user_name = mUserName.getText().toString().trim();
        if(TextUtils.isEmpty(user_name)){
            mUserName.setError("Required.");
            valid = false;
        }else{
            mUserName.setError(null);
        }

        String first_name = mFirstName.getText().toString().trim();
        if(TextUtils.isEmpty(first_name)){
            mFirstName.setError("Required.");
            valid = false;
        }else{
            mFirstName.setError(null);
        }

        String last_name = mLastName.getText().toString().trim();
        if(TextUtils.isEmpty(last_name)){
            mLastName.setError("Required.");
            valid = false;
        }else{
            mLastName.setError(null);
        }

        return valid;
    }
}
