package com.example.workfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView textViewCreate;
    private Intent intentMain, intentReg;
    private Animation animationScale;
    private SharedPreferences sharedPreferences;
    private EditText email, password;
    private ConstraintLayout constraint;
    private boolean logging;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.loginButton);
        textViewCreate = findViewById(R.id.textViewCreate);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        boolean logging = sharedPreferences.getBoolean("logging", false);

        textViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewCreate.setText(R.string.create_underline);
                intentRegActivity();
                finish();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
                animation();
            }
        });
    }
    private void loginUser() {
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        if (TextUtils.isEmpty(email.getText().toString())) {
            Snackbar.make(constraint, "Вы не ввели почту!", Snackbar.LENGTH_SHORT).show();
            System.exit(0);

        }
        if (password.getText().toString().length() < 7) {
            Snackbar.make(constraint, "Вы не ввели пароль!", Snackbar.LENGTH_SHORT).show();
            System.exit(0);

        }

        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        intentMainActivity();
                        //Snackbar.make(constraint, "Вы успешно вошли в аккаунт!", Snackbar.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Snackbar.make(constraint, "Упс! Что-то пошло не так..." + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });


    }
    private void animation() {
        animationScale = AnimationUtils.loadAnimation(this, R.anim.animation_button_scale);
    }
    private void intentMainActivity() {
        intentMain = new Intent(this, MenuActivity.class);
        startActivity(intentMain);
    }
    private void intentRegActivity() {
        intentReg = new Intent(this, RegistrationActivity.class);
        startActivity(intentReg);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}