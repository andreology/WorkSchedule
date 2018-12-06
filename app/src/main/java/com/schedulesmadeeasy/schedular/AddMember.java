package com.schedulesmadeeasy.schedular;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Activity for adding a member to the current group.
 * Current group is passed from previous activity.
 * Assumes group ID and Title is passed to this activity.
 * @author Andre Barajas
 * @version 0.9
 */
public class AddMember extends AppCompatActivity {
    private EditText mUsernameEditText;
    private String mID;
    private String mTitle;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;
    private Group mGroup;
    private Button mAddUsernameButton;

    private static final String TAG = "ADDMEMBER";

    /**
     * Initializes values and inflates layout.
     * Sets up listeners from layout.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mID = extras.getString("ID");
            mTitle = extras.getString("TITLE");
        }

        mUsernameEditText = findViewById(R.id.addMemberEditText);
        //mAddUsernameButton = findViewById(R.id.addMemberButton);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String reference = "users/" + mUser.getUid() + "/groups/" + mID;
        mRef = FirebaseDatabase.getInstance().getReference(reference);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mGroup = dataSnapshot.getValue(Group.class);
                if(mGroup == null){
                    Log.d(TAG, "could not find group");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAddUsernameButton = findViewById(R.id.addMemberButton);
        mAddUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    addUserToGroup();
                }
            }
        });


    }

    /**
     * Adds a validated user to the current group.
     */
    private void addUserToGroup(){
        final String username = mUsernameEditText.getText().toString();
        String reference = "usernames/" + username;
        DatabaseReference usernamesRef = FirebaseDatabase.getInstance().getReference(reference);
        usernamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key;
                key = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "KEY: " + key);
                if(key == null){
                    Toast.makeText(getApplicationContext(), "USERNAME DOESN'T EXIST",
                            Toast.LENGTH_SHORT).show();
                }else{
                    //IF KEY EXISTS ADD USER TO GROUP
                    //TODO MAKE SURE USERNAME IS NOT SELF
                    String pushReference = "users/" + key + "/groups/" + mGroup.id;
                    // Log.d(TAG, "REFERENCE: " + pushReference);
                    //Log.d(TAG, "GROUP\n" + mGroup.toString());
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(pushReference);
                    int groupMembers = Integer.parseInt(mGroup.getMembers()) + 1;
                    Group memberGroup = new Group(mGroup.title, groupMembers + "", "Member", mGroup.id);
                    userRef.setValue(memberGroup);

                    //ADD ONE TO YOUR OWN GROUP
                    pushReference = "users/" + mUser.getUid() + "/groups/" + mGroup.id;
                    userRef = FirebaseDatabase.getInstance().getReference(pushReference);
                    Group managerGroup = new Group(mGroup.title, groupMembers + "", "Manager", mGroup.id);
                    userRef.setValue(managerGroup);
                    //Toast.makeText(getApplicationContext(), "USERNAME ADDED",
                    //        Toast.LENGTH_SHORT).show();
                    //POSTING USERNAME TO GROUPS LIST OF USER NAMES
                    String userNameRef = "groups/" + mID + "/members/" + key;
                    userRef = FirebaseDatabase.getInstance().getReference(userNameRef);
                    userRef.setValue("Member");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Makes sure the form is valid and non-empty.
     * @return true if form is valid, false if there was an error or a textbox was left empty
     */
    private boolean validateForm(){
        boolean valid = true;
        String username = mUsernameEditText.getText().toString();
        if(TextUtils.isEmpty(username)){
            mUsernameEditText.setError("Required");
            valid = false;
        }else{
            mUsernameEditText.setError(null);
        }

        return valid;
    }


}
