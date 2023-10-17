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
import android.widget.Toast;

import com.example.workfuel.objects.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private Button regButton, backLoginButton;
    private TextView textViewLogin;
    private Intent intentMain, intentReg, intentBack;
    private Animation animationScale;
    private EditText name, email, password, passwordRepeat;
    private ConstraintLayout constraint;
    private SharedPreferences sharedPreferences;
    private boolean register;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        constraint = findViewById(R.id.constraint);
        regButton = findViewById(R.id.regButton);
        textViewLogin = findViewById(R.id.textViewLogin);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        boolean register = sharedPreferences.getBoolean("register", false);

        // авторизируем бд
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrationUser();
                animation();

            }
        });
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewLogin.setText(R.string.login_underline);
                intentBackActivity();
            }
        });
    }
    private void registrationUser() {
        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        passwordRepeat = findViewById(R.id.editTextPasswordRepeat);

        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Вы не ввели имя!", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(RegistrationActivity.this, "Вы не ввели почту!", Toast.LENGTH_SHORT).show();
            return;

        }
        if (password.getText().toString().length() < 7) {
            Toast.makeText(RegistrationActivity.this, "Длина пароля должна быть не менее 7 цифр", Toast.LENGTH_SHORT).show();
            return;

        }
        //if (password.getText().toString().equals(!TextUtils.isEmpty(passwordRepeat.getText().toString())) {
            //Snackbar.make(constraint, "Пароли не совпадают!", Snackbar.LENGTH_SHORT).show();
            //return;

        //}
        // рег
        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Создание объекта
                        User user = new User();
                        // Устанавливаем значения
                        user.setEmail(email.getText().toString());
                        user.setName(name.getText().toString());
                        user.setPassword(password.getText().toString());
                        intentMainActivity();
                        //Идентификация пользователи
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        intentMainActivity();
                                        Toast.makeText(RegistrationActivity.this, "Аккаунт успешно зарегистрирован!", Toast.LENGTH_SHORT).show();
                                        //Snackbar.make(constraint, "Аккаунт успешно зарегистрирован!", Snackbar.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegistrationActivity.this, "Упс! Что-то пошло не так..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        //Snackbar.make(constraint, "Упс! Что-то пошло не так..." + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });
    }
    private void animation() {
        animationScale = AnimationUtils.loadAnimation(this, R.anim.animation_button_scale);
    }
    private void intentMainActivity() {
        intentMain = new Intent(this, LoginActivity.class);
        startActivity(intentMain);
    }
    private void intentBackActivity() {
        intentBack = new Intent(this, LoginActivity.class);
        startActivity(intentBack);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}