package com.example.workfuel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workfuel.adapters.PopAdapters;
import com.example.workfuel.objects.PopularDishes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.List;

public class MenuFragment extends Fragment {

    List <PopularDishes> popularDishesList;
    PopAdapters popAdapters;
    FirebaseUser auth;
    TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        String name = auth.getDisplayName();
        userName.setText(name);


        return root;
    }
}