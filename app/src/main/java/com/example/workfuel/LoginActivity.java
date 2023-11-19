package com.example.workfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private CheckBox checkBox;
    private Button loginButton;
    private TextView textViewCreate;
    private Intent intentMain, intentReg, intentLog, intentVer;
    private Animation animationScale;
    private SharedPreferences sharedPreferences;
    private EditText email, password;
    private boolean logging;
    public static final String APP_PREFERENCES = "preferences";
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.loginButton);
        textViewCreate = findViewById(R.id.textViewCreate);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        //checkBox = findViewById(R.id.checkBox);

        //boolean checked = checkBox.isChecked();

        //if (checked) {
        //savePreferences();
        //}

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        textViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewCreate.setText(R.string.create_underline);
                intentRegActivity();
                progressBar.setVisibility(View.VISIBLE);


            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loginUser();
                animation();
            }
        });
    }
    private void loginUser() {
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        if (TextUtils.isEmpty(email.getText().toString())) {
            //Toast.makeText(LoginActivity.this, "Вы не ввели почту!", Toast.LENGTH_SHORT).show();
            snackbarMake("Упс! Вы не ввели почту!");
            return;

        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            //Toast.makeText(LoginActivity.this, "Вы не ввели пароль!", Toast.LENGTH_SHORT).show();
            snackbarMake("Упс! Вы не ввели пароль!");
            return;

        }
        if (password.getText().toString().length() < 7) {
            //Toast.makeText(LoginActivity.this, "Данный пароль недействителен, т.к его длина менее 7 символов!", Toast.LENGTH_SHORT).show();
            snackbarMake("Упс! Введенный вами пароль имеет менее 7 символов!");
            return;

        }
        if (TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(password.getText().toString())) {
            //Toast.makeText(LoginActivity.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            snackbarMake("Заполните все поля!");
            return;

        }
        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (auth.getCurrentUser().isEmailVerified()) {
                            intentMainActivity();
                            //Toast.makeText(LoginActivity.this, "Вы успешно вошли в аккаунт!", Toast.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(findViewById(R.id.snackLayout2), "Вам необходимо подтвердить почту!", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(getResources().getColor(R.color.button_color))
                                    .setTextColor(Color.WHITE)
                                    .show();
                            intentVerificationActivity();


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(LoginActivity.this, "Упс! Что-то пошло не так..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                        snackbarMake("Упс! Что-то пошло не так..." + e.getMessage());
                    }
                });


    }
    private void animation() {
        animationScale = AnimationUtils.loadAnimation(this, R.anim.animation_button_scale);
        loginButton.startAnimation(animationScale);
    }
    private void intentMainActivity() {
        Intent intentMain = new Intent(this, MenuActivity.class);
        intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intentMain, 0);
        overridePendingTransition(0,0);

        //intentMain = new Intent(this, MenuActivity.class);
        //startActivity(intentMain);

    }
    private void intentRegActivity() {

        Intent intentReg = new Intent(this, RegistrationActivity.class);
        intentReg.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intentReg, 0);
        overridePendingTransition(0,0);

        //intentReg = new Intent(this, RegistrationActivity.class);
        //startActivity(intentReg);

    }
    private void intentVerificationActivity() {
            intentVer = new Intent(this, EmailVerificationActivity.class);
            startActivity(intentVer);
    }
    private void intentLogActivity() {
        if(intentVer==null) {
            intentLog = new Intent(this, LoginActivity.class);
            startActivity(intentLog);
        }

    }
    private void snackbarMake(String textSnack) {
        Snackbar.make(findViewById(R.id.snackLayout), textSnack, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.button_color))
                .setTextColor(Color.WHITE)
                .show();
    }
    /*private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logging", true);
        editor.apply();
    }

    private void loadPreferences() {
        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        boolean logging = sharedPreferences.getBoolean("logging", true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && auth.getCurrentUser().isEmailVerified()) {
            intentMainActivity();
        } else {
            intentLogActivity();
        }

    } */

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && auth.getCurrentUser().isEmailVerified()) {
            intentMainActivity();
        } else {
            intentLogActivity();
        }


    }
}