package com.example.workfuel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class RegistrationActivity extends AppCompatActivity {

    private Button regButton, backLoginButton;
    private Intent intentMain;
    private Animation animationScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        regButton = findViewById(R.id.regButton);
        backLoginButton = findViewById(R.id.backLoginButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation();
                intentMainActivity();
            }
        });
        backLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentBackActivity();
            }
        });
    }
    private void animation() {
        animationScale = AnimationUtils.loadAnimation(this, R.anim.animation_button_scale);
    }
    private void intentMainActivity() {
        intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }
    private void intentBackActivity() {
        finish();
    }

}