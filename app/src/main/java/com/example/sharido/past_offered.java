package com.example.sharido;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class past_offered extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String userID;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_offered);

        recyclerView = (RecyclerView) findViewById(R.id.offered);

        ArrayList<String> time;
        ArrayList<String> cost;
        ArrayList<String> seats;
        ArrayList<String> model;
        ArrayList<String> vehicle;
        ArrayList<String> source;
        ArrayList<String> destination;
        ArrayList<String> date;

        time = getIntent().getStringArrayListExtra("time");
        cost = getIntent().getStringArrayListExtra("cost");
        model = getIntent().getStringArrayListExtra("model");
        seats = getIntent().getStringArrayListExtra("seats");
        vehicle = getIntent().getStringArrayListExtra("vehicle");
        source = getIntent().getStringArrayListExtra("from");
        destination = getIntent().getStringArrayListExtra("to");
        date = getIntent().getStringArrayListExtra("date");

        adapter1 ada = new adapter1(this,time,cost,seats,model,vehicle,source,destination,date);
        recyclerView.setAdapter(ada);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
