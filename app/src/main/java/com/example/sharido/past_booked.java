package com.example.sharido;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class past_booked extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String userID;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_booked);

        recyclerView = (RecyclerView) findViewById(R.id.booked);
        //userID = firebaseAuth.getCurrentUser().getUid();

        List<String> time;
        List<String> cost;
        List<String> seats;
        List<String> model;
        List<String> vehicle;
        List<String> source;
        List<String> destination;
        List<String> date;

        time = getIntent().getStringArrayListExtra("time");
        cost = getIntent().getStringArrayListExtra("cost");
        model = getIntent().getStringArrayListExtra("model");
        seats = getIntent().getStringArrayListExtra("seats");
        vehicle = getIntent().getStringArrayListExtra("vehicle");
        source = getIntent().getStringArrayListExtra("from");
        destination = getIntent().getStringArrayListExtra("to");
        date = getIntent().getStringArrayListExtra("date");

        adapter2 ada = new adapter2(this,time,cost,seats,model,vehicle,source,destination,date);
        recyclerView.setAdapter(ada);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}