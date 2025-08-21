package com.example.tripbookingapp;

import androidx.appcompat.app.AppCompatActivity;
import main.java.com.BookingHistoryActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity implements PaymentResultListener {

    TextView placeName, placeDate, placePrice, placeDescription, ratingValue;
    ImageView placeImage, galleryImage1, galleryImage2, galleryImage3;
    Button bookButton;
    ImageView backButton, likeButton;

    String docId;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        placeName = findViewById(R.id.placeName);
        placeDate = findViewById(R.id.placeDate);
        placePrice = findViewById(R.id.placePrice);
        placeDescription = findViewById(R.id.placeDescription);
        ratingValue = findViewById(R.id.ratingValue);

        placeImage = findViewById(R.id.placeImage);
        galleryImage1 = findViewById(R.id.galleryImage1);
        galleryImage2 = findViewById(R.id.galleryImage2);
        galleryImage3 = findViewById(R.id.galleryImage3);

        backButton = findViewById(R.id.backButton);
        likeButton = findViewById(R.id.likeButton);
        bookButton = findViewById(R.id.bookButton);

        db = FirebaseFirestore.getInstance();

        docId = getIntent().getStringExtra("documentId");
        if (docId == null || docId.isEmpty()) {
            Toast.makeText(this, "Invalid document ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadPlaceDetails();

        backButton.setOnClickListener(v -> onBackPressed());

        // 🔥 Start Razorpay payment when clicking book button
        bookButton.setOnClickListener(v -> startPayment());
    }

    private void loadPlaceDetails() {
        db.collection("places").document(docId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    placeName.setText(documentSnapshot.getString("name"));
                    placeDate.setText(documentSnapshot.getString("date"));
                    placePrice.setText(documentSnapshot.getString("price"));
                    placeDescription.setText(documentSnapshot.getString("description"));
                    ratingValue.setText(documentSnapshot.getString("rating"));

                    Glide.with(DetailsActivity.this)
                            .load(documentSnapshot.getString("imageUrl"))
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(placeImage);

                    Glide.with(this).load(documentSnapshot.getString("gallery1")).into(galleryImage1);
                    Glide.with(this).load(documentSnapshot.getString("gallery2")).into(galleryImage2);
                    Glide.with(this).load(documentSnapshot.getString("gallery3")).into(galleryImage3);

                } else {
                    Toast.makeText(this, "Place not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            })
            .addOnFailureListener(e -> {
                Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                finish();
            });
    }

    // 🚀 Razorpay Payment
    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_xxxxxxxx"); // TODO: Replace with your Razorpay Key

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Trip Booking App");
            options.put("description", "Booking Payment");
            options.put("currency", "INR");

            // Convert price (e.g. "500") to paise (500 * 100 = 50000)
            String priceStr = placePrice.getText().toString().replaceAll("[^0-9]", "");
            int amount = Integer.parseInt(priceStr) * 100;
            options.put("amount", amount);

            JSONObject prefill = new JSONObject();
            prefill.put("email", "user@example.com"); // can use logged-in user email
            prefill.put("contact", "9876543210");

            options.put("prefill", prefill);

            checkout.open(DetailsActivity.this, options);

        } catch (Exception e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // ✅ Payment Success
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_LONG).show();
        saveBooking(razorpayPaymentID);
    }

    // ❌ Payment Failure
    @Override
    public void onPaymentError(int code, String description) {
        Toast.makeText(this, "Payment Failed: " + description, Toast.LENGTH_LONG).show();
    }

    // Save booking after payment success
    private void saveBooking(String paymentId) {
        Map<String, Object> booking = new HashMap<>();
        booking.put("placeName", placeName.getText().toString());
        booking.put("date", placeDate.getText().toString());
        booking.put("price", placePrice.getText().toString());
        booking.put("description", placeDescription.getText().toString());
        booking.put("rating", ratingValue.getText().toString());
        booking.put("paymentId", paymentId);
        booking.put("timestamp", System.currentTimeMillis());
        // ✅ Store logged-in user info
        booking.put("userId", userId);
        booking.put("userEmail", userEmail);

        db.collection("bookings")
            .add(booking)
            .addOnSuccessListener(documentReference -> {
                Toast.makeText(this, "Booking Saved", Toast.LENGTH_SHORT).show();
                goToBookingHistory();
            })
            .addOnFailureListener(e -> {
                Toast.makeText(this, "Failed to save booking", Toast.LENGTH_SHORT).show();
            });
    }

    private void goToBookingHistory() {
        Intent intent = new Intent(DetailsActivity.this, BookingHistoryActivity.class);
        startActivity(intent);
        finish();
    }
}
