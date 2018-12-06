package com.schedulesmadeeasy.schedular;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

/**
 * Activity for signing up to app. This makes a user out of information given.
 * @author Andre Barajas
 * */
public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SIGN UP";
    private FirebaseAuth mAuth;
    private Button mSignUpButton;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mPassword2;
    private static int sUserNameCount;
    private static int sPasswordCount;

    /**
     * Inflates view and sets up listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sUserNameCount = 0;
        sPasswordCount = 0;
        mUserName = findViewById(R.id.user_name_edit_text_sign_up);
        mPassword = findViewById(R.id.password_edit_text_sign_up);
        mPassword2 = findViewById(R.id.password2EditText);
        mSignUpButton = findViewById(R.id.sign_up_button);

        //[START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        //[END initialize_auth]

        //LISTENERS//
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

        mPassword2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    String userName = mUserName.getText().toString();
                    String passWord = mPassword.getText().toString();
                    if(passWord.equals(mPassword2.getText().toString())){
                        signUp(userName, passWord);
                    }else {
                        Toast.makeText(SignUpActivity.this, "Passwords do not match.",
                                Toast.LENGTH_LONG).show();
                        mPassword.setText("");
                        mPassword2.setText("");
                        mUserName.clearFocus();
                        mPassword.clearFocus();
                        mPassword2.clearFocus();
                    }
                    handled = true;
                }
                return handled;
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String userName = mUserName.getText().toString();
                String passWord = mPassword.getText().toString();
                if(passWord.equals(mPassword2.getText().toString())){
                    signUp(userName, passWord);
                }else{
                    Toast.makeText(SignUpActivity.this, "Passwords do not match.",
                            Toast.LENGTH_LONG).show();
                    mPassword.setText("");
                    mPassword2.setText("");
                    mUserName.clearFocus();
                    mPassword.clearFocus();
                    mPassword2.clearFocus();
                }
            }
        });


    }

    /**
     * Starts user on start.
     */
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * Signs up user if validated.
     * @param email E-mail to sign up with.
     * @param password Password to sign up with.
     */
    private void signUp(String email, String password){
        Log.d(TAG, "createAccount: " + email);
        if(!validateForm()){
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendEmailVerification();
                            //TODO FIGURE OUT HOW TO UPDATE UI
                            //updateUI(user);
                        }else{
                            //If sign in fails display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    /**
     * Sends email verification to email that is inputted.
     */
    private void sendEmailVerification(){
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // RE-enable button

                        if (task.isSuccessful()){
                            Log.d(TAG, "sendEmailVerification:success");
                            Toast.makeText(SignUpActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this, R.style.MyDialogTheme);
                            builder.setMessage("Please verify email sent to " + user.getEmail() + " before signing in.");
                            builder.setPositiveButton("OK!",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    updateUI(user);
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(SignUpActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Validates the form to check for non-empty inputs.
     * @return
     */
    private boolean validateForm(){
        //TODO REGEX for email address
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
            valid = false;
        }else{
            mPassword.setError(null);
        }

        String password2 = mPassword2.getText().toString();
        if (TextUtils.isEmpty(password2)){
            mPassword.setError("Required.");
            valid = false;
        }else{
            mPassword.setError(null);
        }


        return valid;

    }

    /**
     * Depending if user is validated it goes to login activity.
     * @param user
     */
    private void updateUI(FirebaseUser user){
        if(user != null){
            if(user.isEmailVerified()){
                Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(i);
            }else{
                /*Toast.makeText(SignUpActivity.this,
                        "Please verify email sent to " + user.getEmail(),
                        Toast.LENGTH_LONG).show();
                        */

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("Email", user.getEmail());
                startActivity(i);
            }
        }else{
            mUserName.setText("");
            mPassword.setText("");
            sPasswordCount = 0;
            sUserNameCount = 0;
        }
    }
}
