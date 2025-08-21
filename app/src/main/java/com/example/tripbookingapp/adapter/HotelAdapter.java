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
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.models.Hotel;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private Context context;
    private List<Hotel> hotelList;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.name.setText(hotel.getName());
        holder.location.setText(hotel.getLocation());
        holder.price.setText("₹" + hotel.getPrice());

        Glide.with(context)
                .load(hotel.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, price;
        ImageView image;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hotelName);
            location = itemView.findViewById(R.id.hotelLocation);
            price = itemView.findViewById(R.id.hotelPrice);
            image = itemView.findViewById(R.id.hotelImage);
        }
    }
}
