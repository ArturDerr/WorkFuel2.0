package com.example.workfuel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton, registerButton;
    private Intent intentMain, intentReg;
    private Animation animationScale;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        boolean login = sharedPreferences.getBoolean("login", false);

        // Короче это в конце регистрации

        // if (!isRegistered) {
        //    // Показать окно регистрации
        //    // Запустить активность регистрации или показать диалоговое окно
        //} else {
        // То открываем MainActivity
        // }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation();
                intentMainActivity();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentRegActivity();
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
    private void intentRegActivity() {
        intentReg = new Intent(this, RegistrationActivity.class);
        startActivity(intentReg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Либо в конце регистрации (что более вероятно)
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isRegistered", true);
        editor.apply();
    }
}