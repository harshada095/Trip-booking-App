package com.example.tripbookingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

    private List<BookingModel> bookingList;

    public BookingHistoryAdapter(List<BookingModel> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingModel booking = bookingList.get(position);
        holder.placeName.setText(booking.getPlaceName());
        holder.date.setText("Date: " + booking.getDate());
        holder.price.setText("Price: " + booking.getPrice());
        holder.rating.setText("Rating: " + booking.getRating());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName, date, price, rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.bookingPlaceName);
            date = itemView.findViewById(R.id.bookingDate);
            price = itemView.findViewById(R.id.bookingPrice);
            rating = itemView.findViewById(R.id.bookingRating);
        }
    }
}
