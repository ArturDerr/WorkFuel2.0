package com.example.workfuel.adapters;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workfuel.R;
import com.example.workfuel.objects.PopularDishes;

import java.util.List;

public class PopAdapters extends RecyclerView.Adapter<PopAdapters.ViewHolder> {

    private Context context;
    private List<PopularDishes> popularDishesList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_eat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(popularDishesList.get(position).getImg()).into(holder.img);
        holder.name.setText(popularDishesList.get(position).getName());
        holder.rating.setText(popularDishesList.get(position).getRating());
        holder.description.setText(popularDishesList.get(position).getDescription());


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, description, rating;
        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.popular_img);
            name = itemView.findViewById(R.id.nameDish);
            description = itemView.findViewById(R.id.descriptionDish);
            rating = itemView.findViewById(R.id.rating);

        }
    }
}
