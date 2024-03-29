package com.example.workfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class EmailVerificationActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button transitionButton;
    private TextView textViewLogin;
    private Intent intentLogin, intentMain, emailIntent;
    private Animation animationScale;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        auth = FirebaseAuth.getInstance();

        transitionButton = findViewById(R.id.transitionButton);
        textViewLogin = findViewById(R.id.textViewLogin);

        transitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animationTransitionButton();
                emailIntent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(emailIntent, ""));
                /*try {
                    animationTransitionButton();
                    emailIntent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                    emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(emailIntent, ""));
                } catch (Exception error1) {
                    snackMake("Упс! Что-то пошло не так..." + error1.getMessage());
                } */

            }
        });
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewLogin.setText(R.string.login_underline);
                intentLogActivity();
                finish();
            }
        });

        new Thread(new Runnable() {
            public void run() {
                if (auth.getCurrentUser().isEmailVerified()) {
                    progressBar.setVisibility(View.GONE);
                    intentMainActivity();
                    //Toast.makeText(RegistrationActivity.this, "Аккаунт успешно зарегистрирован!", Toast.LENGTH_SHORT).show();
                        /*Snackbar.make(findViewById(R.id.frame), "Аккаунт успешно зарегистрирован!", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(getResources().getColor(R.color.button_color))
                                .setTextColor(Color.WHITE)
                                .show(); */
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    //snackMake("Вам необходимо подтвердить почту!");
                }
            }
        }).start();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                while (true) {
                    if (auth.getCurrentUser().isEmailVerified()) {
                        progressBar.setVisibility(View.GONE);
                        intentMainActivity();
                        //Toast.makeText(RegistrationActivity.this, "Аккаунт успешно зарегистрирован!", Toast.LENGTH_SHORT).show();
                        /*Snackbar.make(findViewById(R.id.frame), "Аккаунт успешно зарегистрирован!", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(getResources().getColor(R.color.button_color))
                                .setTextColor(Color.WHITE)
                                .show(); */
                        break;
                    }
                    else {
                        progressBar.setVisibility(View.VISIBLE);
                        snackMake("Вам необходимо подтвердить почту!");
                    }
                }

            }
        });

    }
    private void intentLogActivity() {
        //intentLogin = new Intent(this, LoginActivity.class);
        overridePendingTransition(0, 0);
        startActivity(intentLogin);
        finish();
    }
    private void intentMainActivity() {
        intentMain = new Intent(this, MenuActivity.class);
        overridePendingTransition(0, 0);
        startActivity(intentMain);
    }
    private void snackMake(String textSnack) {
        Snackbar.make(findViewById(R.id.snackLayout3), textSnack, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.button_color))
                .setTextColor(Color.WHITE)
                .show();
    }
    private void animationTransitionButton() {
        animationScale = AnimationUtils.loadAnimation(this, R.anim.animation_button_scale);
        transitionButton.startAnimation(animationScale);
    }
    @Override
    protected void onResume() {
        super.onResume();
        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            intentMainActivity();
        } else {
            intentLogActivity();
        } */
    }
}