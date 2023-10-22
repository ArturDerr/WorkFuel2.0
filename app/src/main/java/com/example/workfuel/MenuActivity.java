package com.example.workfuel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.simform.custombottomnavigation.Model;
import com.simform.custombottomnavigation.SSCustomBottomNavigation;

import me.ibrahimsn.lib.BottomBarItem;
import me.ibrahimsn.lib.OnItemReselectedListener;
import me.ibrahimsn.lib.OnItemSelectedListener;

public class MenuActivity extends AppCompatActivity {

    private SSCustomBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new Model(1, R.drawable.));
    }
}