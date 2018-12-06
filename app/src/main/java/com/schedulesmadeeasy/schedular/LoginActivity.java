package com.schedulesmadeeasy.schedular;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.schedulesmadeeasy.schedular.R;

/**
 * Activity that inflates login layout. Provides login authentication for user.
 * @author Andre Barajas
 * */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";
    private FirebaseAuth mAuth;
    private EditText mUserName;
    private EditText mPassword;
    private static int sUserNameCount;
    private static int sPasswordCount;

    /**
     * Inflates views and sets up listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sUserNameCount = 0;
        sPasswordCount = 0;
        mUserName = findViewById(R.id.user_name_edit_text);
        mPassword = findViewById(R.id.password_edit_text);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mUserName.setText(extras.getString("Email"));
        }

        //[START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        //[END initialize_auth]

        //LISTENERS//
        final Button button = findViewById(R.id.sign_in_button_login);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //TODO VALIDATE LOGIN CREDENTIALS
                String userName = mUserName.getText().toString();
                String passWord = mPassword.getText().toString();
                signIn(userName, passWord);

                //Intent intent = new Intent(v.getContext(), HomePageActivity.class);
                //startActivity(intent);
            }
        });

        mUserName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(sUserNameCount == 0){
                    mUserName.setText("");
                    sUserNameCount++;
                }
            }
        });
        mPassword.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(sPasswordCount == 0){
                    mPassword.setText("");
                    sPasswordCount++;
                }
            }
        });

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    String userName = mUserName.getText().toString();
                    String passWord = mPassword.getText().toString();
                    signIn(userName, passWord);
                    handled = true;
                }
                return handled;
            }
        });
    }

    /**
     * Updates current user on start.
     */
    @Override
    public void onStart(){
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    /**
     * On back pressed goes to signup activity.
     */
    @Override
    public void onBackPressed(){
        Log.d(TAG, "onBackPressed:LoginActivity");
        mAuth.signOut();
        Intent setIntent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(setIntent);
    }

    /**
     * Validates email and password authentication.
     * @param email The email.
     * @param password The password for the user.
     */
    private void signIn(String email, String password){
        Log.d(TAG, "signIn: " + email);
        if(!validateForm()){
            return;
        }
        //showProgressDialog();
        //[START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //hideProgressDialog();
                    }
                });
        // [END sign_in_with_email]
    }

    /**
     * Validates the form to make sure it's non-empty or free of errors..
     * @return true if validated, false if contains error
     */
    private boolean validateForm() {
        boolean valid = true;

        String email = mUserName.getText().toString();
        if (TextUtils.isEmpty(email)){
            mUserName.setError("Required.");
            valid = false;
        }else{
            mUserName.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)){
            mPassword.setError("Required.");
        }else{
            mPassword.setError(null);
        }
        return valid;
    }

    /*
    private void signOut(){
        mAuth.signOut();
        updateUI(null);
    }*/

    /**
     * Updates teh UI base on the user given.
     * If user has not set up account then goes to set up.
     * If he has then goes to main page.
     * @param user
     */
    private void updateUI(FirebaseUser user){
        if(user != null){
            if(user.isEmailVerified()){
                //WORKAROUND FOR GETDISPLAYNAME BUG
                if(user.getDisplayName()==null){
                    Log.d(TAG, "DISPLAY NAME NOT SET.");
                    Intent i = new Intent(getApplicationContext(), SetUpActivity.class);
                    startActivity(i);
                }else{
                    Log.d(TAG, "DISPLAY NAME SET: " + user.getDisplayName());
                    if(user.getDisplayName().equals("")){
                        Log.d(TAG, "DISPLAY NAME NOT SET.");
                        Intent i = new Intent(getApplicationContext(), SetUpActivity.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                        startActivity(i);
                    }
                }
            }else{
                Toast.makeText(LoginActivity.this,
                        "Please verify email.",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            //mUserName.setText(R.string.placeholder_user_name);
            mPassword.setText("");
            sPasswordCount = 0;
            sUserNameCount = 0;
        }
    }


}
