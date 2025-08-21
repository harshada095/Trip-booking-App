package com.example.tripbookingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tripbookingapp.DetailsActivity;
import com.example.tripbookingapp.R;
import com.example.tripbookingapp.model.RecentsData;

import java.util.List;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder> {

    Context context;
    List<RecentsData> recentsDataList;

    public RecentsAdapter(Context context, List<RecentsData> recentsDataList) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recents_row_item, parent, false);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {

        RecentsData data = recentsDataList.get(position);

        holder.countryName.setText(data.getCountryName());
        holder.placeName.setText(data.getPlaceName());
        holder.price.setText(data.getPrice());

        // Load image from URL using Glide
        Glide.with(context)
                .load(data.getImageUrl())
                .placeholder(R.drawable.placeholder_image) // optional
                .error(R.drawable.error_image) // optional
                .into(holder.placeImage);

        // Click to open DetailsActivity (optional: pass data)
        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(context, DetailsActivity.class);
            i.putExtra("placeName", data.getPlaceName());
            i.putExtra("countryName", data.getCountryName());
            i.putExtra("price", data.getPrice());
            i.putExtra("imageUrl", data.getImageUrl());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return recentsDataList.size();
    }

    public static final class RecentsViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
