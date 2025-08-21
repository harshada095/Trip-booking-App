package com.example.tripbookingapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.adapters.HotelAdapter;
import com.rajendra.vacationtourapp.models.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HotelAdapter adapter;
    List<Hotel> hotelList;
    DatabaseReference hotelRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        recyclerView = findViewById(R.id.recyclerHotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelList = new ArrayList<>();
        adapter = new HotelAdapter(this, hotelList);
        recyclerView.setAdapter(adapter);

        hotelRef = FirebaseDatabase.getInstance().getReference("Hotels");

        hotelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hotelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Hotel hotel = ds.getValue(Hotel.class);
                    hotelList.add(hotel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HotelActivity.this, "Failed to load hotels", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
