package com.example.workfuel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.simform.custombottomnavigation.SSCustomBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MenuActivity extends AppCompatActivity {

    private SSCustomBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new SSCustomBottomNavigation.Model(1, R.drawable.ic_home, "Home"));
        bottomNavigation.add(new SSCustomBottomNavigation.Model(2, R.drawable.ic_basket, "Basket"));
        bottomNavigation.add(new SSCustomBottomNavigation.Model(3, R.drawable.ic_settings, "Settings"));

        //bottomNavigation.setCount(2, "1"); Это кол-во еды в корзине сверху над иконкой
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new Function1<SSCustomBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(SSCustomBottomNavigation.Model model) {

                switch (model.getId()) {
                    case 1:
                        transaction(new MenuFragment());
                        break;

                    case 2:
                        transaction(new BasketFragment());
                        break;

                    case 3:
                        transaction(new SettingsFragment());
                        break;
                }
                return null;
            }
        });

    }
    private void transaction(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}