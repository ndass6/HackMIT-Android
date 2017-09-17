package com.tejunareddy.hackmit2017.bucketlist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tejunareddy.hackmit2017.bucketlist.R;

public class SplashActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginButton = (Button) findViewById(R.id.splash_button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClick(v);
            }
        });

        registerButton = (Button) findViewById(R.id.splash_button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClick(v);
            }
        });
    }

    private void onLoginButtonClick(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void onRegisterButtonClick(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
