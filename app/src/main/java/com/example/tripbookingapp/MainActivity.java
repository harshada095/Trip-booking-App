package com.example.tripbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbookingapp.adapter.RecentsAdapter;
import com.example.tripbookingapp.adapter.TopPlacesAdapter;
import com.example.tripbookingapp.model.RecentsData;
import com.example.tripbookingapp.model.TopPlacesData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recentRecycler, topPlacesRecycler;
    RecentsAdapter recentsAdapter;
    TopPlacesAdapter topPlacesAdapter;
    List<RecentsData> recentsDataList = new ArrayList<>();
    List<TopPlacesData> topPlacesDataList = new ArrayList<>();

    DatabaseReference databaseReference;
    EditText editTextSearch;  // Search box

    // 🔹 Bottom navigation
    ImageView navHome, navProfile, navFlights, navHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recentRecycler = findViewById(R.id.recent_recycler);
        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        editTextSearch = findViewById(R.id.editText);

        // Firebase Reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Load Data
        loadRecentsData();
        loadTopPlacesData();

        // Search Listener
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // 🔹 Setup Bottom Navigation
        navHome = findViewById(R.id.nav_home);
        navProfile = findViewById(R.id.nav_profile);
        navFlights = findViewById(R.id.nav_flights);
        navHotel = findViewById(R.id.nav_hotel);

        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

        navFlights.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookingHistoryActivity.class);
            startActivity(intent);
            finish();
        });

        navHotel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HotelActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadRecentsData() {
        databaseReference.child("recents").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recentsDataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RecentsData data = dataSnapshot.getValue(RecentsData.class);
                    recentsDataList.add(data);
                }
                setRecentRecycler(recentsDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadTopPlacesData() {
        databaseReference.child("top_places").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topPlacesDataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TopPlacesData data = dataSnapshot.getValue(TopPlacesData.class);
                    topPlacesDataList.add(data);
                }
                setTopPlacesRecycler(topPlacesDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void setRecentRecycler(List<RecentsData> recentsDataList) {
        recentRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recentsAdapter = new RecentsAdapter(this, recentsDataList);
        recentRecycler.setAdapter(recentsAdapter);
    }

    private void setTopPlacesRecycler(List<TopPlacesData> topPlacesDataList) {
        topPlacesRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        topPlacesAdapter = new TopPlacesAdapter(this, topPlacesDataList);
        topPlacesRecycler.setAdapter(topPlacesAdapter);
    }

    // 🔹 Filter Function for Search
    private void filterData(String text) {
        List<RecentsData> filteredRecents = new ArrayList<>();
        List<TopPlacesData> filteredTopPlaces = new ArrayList<>();

        for (RecentsData item : recentsDataList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                item.getLocation().toLowerCase().contains(text.toLowerCase())) {
                filteredRecents.add(item);
            }
        }

        for (TopPlacesData item : topPlacesDataList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                item.getLocation().toLowerCase().contains(text.toLowerCase())) {
                filteredTopPlaces.add(item);
            }
        }

        // Update adapters
        recentsAdapter.updateList(filteredRecents);
        topPlacesAdapter.updateList(filteredTopPlaces);
    }
}
