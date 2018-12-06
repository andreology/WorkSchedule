package com.schedulesmadeeasy.schedular;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Initial activity that asks if user wants to sign in or sign up.
 * @author Andre Barajas
 * */
public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        final Button sign_in = findViewById(R.id.sign_in_main_button);
        final Button sign_up = findViewById(R.id.sign_up_main_button);

        sign_in.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mAuth.signOut();
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}
