package com.example.tripbookingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tripbookingapp.R;
import com.example.tripbookingapp.model.TopPlacesData;

import java.util.List;

public class TopPlacesAdapter extends RecyclerView.Adapter<TopPlacesAdapter.TopPlacesViewHolder> {

    private Context context;
    private List<TopPlacesData> topPlacesDataList;

    public TopPlacesAdapter(Context context, List<TopPlacesData> topPlacesDataList) {
        this.context = context;
        this.topPlacesDataList = topPlacesDataList;
    }

    @NonNull
    @Override
    public TopPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_places_row_item, parent, false);
        return new TopPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlacesViewHolder holder, int position) {
        TopPlacesData data = topPlacesDataList.get(position);

        holder.countryName.setText(data.getCountryName());
        holder.placeName.setText(data.getPlaceName());
        holder.price.setText(data.getPrice());

        // Load image from URL using Glide
        Glide.with(context)
                .load(data.getImageUrl()) // Must be a valid URL string from Firebase
                .placeholder(R.drawable.placeholder_image) // Optional: default image
                .error(R.drawable.error_image)             // Optional: fallback
                .into(holder.placeImage);
    }

    @Override
    public int getItemCount() {
        return topPlacesDataList.size();
    }

    // 🔹 Method to update list dynamically (for search & Firebase updates)
    public void updateList(List<TopPlacesData> newList) {
        this.topPlacesDataList = newList;
        notifyDataSetChanged();
    }

    public static final class TopPlacesViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImage;
        TextView placeName, countryName, price;

        public TopPlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
