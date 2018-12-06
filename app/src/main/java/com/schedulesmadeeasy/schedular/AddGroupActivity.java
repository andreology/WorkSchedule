package com.schedulesmadeeasy.schedular;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 *  Activity for creating a group. Adds a group with given name to a user's ownership.
 *  @author Andre Barajas
 *  @version .9
 */
public class AddGroupActivity extends AppCompatActivity {
    private Button mCreateGroup;
    private EditText mTitleEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRefUserGroups;
    private DatabaseReference mRefGroupInfo;

    /**
     * Inflates layout and instantiates user and references to database.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String reference = "users/" + mUser.getUid() + "/groups";
        mRefUserGroups = FirebaseDatabase.getInstance().getReference(reference);
        mRefGroupInfo = FirebaseDatabase.getInstance().getReference("groups");

        //MAKE BUTTON AND LISTENER
        mTitleEditText = findViewById(R.id.titleAddGroupEditText);
        mCreateGroup = findViewById(R.id.addGroupButton);
        mCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    addGroupInfoForUser();
                    //TODO POST "GROUP_ID":{ "events": "username": "time", "requests"..., "members"

                }
            }
        });
    }

    /**
     * Creates new group in the database with the given name.
     * Group belongs to the user that created it.
     */
    private void addGroupInfoForUser(){
        String title = mTitleEditText.getText().toString();
        DatabaseReference groupRef = mRefUserGroups.push();
        groupRef.setValue(new Group(title, "1", "Manager", groupRef.getKey()));
        //TODO ADD ON SUCCESS LISTENER
        mRefGroupInfo.child(groupRef.getKey()+"/members/"+mUser.getUid()).setValue("Manager",
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError != null){
                            System.out.println("DATA COULD NOT BE SAVED: " + databaseError.getMessage());
                        }else{
                            System.out.println("DATA SAVED SUCCESSFULLY");
                        }
                    }
                });
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(intent);
    }

    /**
     * Makes sure that each inputted text box is valid and has an input.
     * @return true if the form is correct, false if there is an error in validation
     */
    private boolean validateForm(){
        boolean valid = true;
        String title = mTitleEditText.getText().toString();
        if(TextUtils.isEmpty(title)){
            mTitleEditText.setError("Required.");
            valid = false;
        }else{
            mTitleEditText.setError(null);
        }
        return valid;
    }
}
