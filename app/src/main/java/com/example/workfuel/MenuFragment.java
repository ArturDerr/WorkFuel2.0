package com.example.workfuel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workfuel.adapters.PopAdapters;
import com.example.workfuel.objects.PopularDishes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    RecyclerView recyclerMenu;
    FirebaseFirestore bd;
    List <PopularDishes> popularDishesList;
    PopAdapters popAdapters;
    TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        userName = view.findViewById(R.id.userName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            userName.append(name + "!");
        }


        bd = FirebaseFirestore.getInstance();

        recyclerMenu = view.findViewById(R.id.recyclerMenu);
        recyclerMenu.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        // устанавливаем адаптер для RecyclerView
        popularDishesList = new ArrayList<>();
        popAdapters = new PopAdapters(getActivity(), popularDishesList);
        recyclerMenu.setAdapter(popAdapters);

         bd.collection("PopAdapters")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularDishes popularDishes = document.toObject(PopularDishes.class);
                                popularDishesList.add(popularDishes);
                                popAdapters.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Упс! Что-то пошло не так..."+ task.getException(), Toast.LENGTH_SHORT);

                        }
                    }
                });
        return view;
    }
}