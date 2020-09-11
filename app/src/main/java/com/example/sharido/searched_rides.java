package com.example.sharido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class searched_rides extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String userID;
    RecyclerView recyclerView;
    String from1,to1,date;
    TextView from,to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_rides);

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);

        recyclerView = (RecyclerView)findViewById(R.id.rides);
        userID = firebaseAuth.getCurrentUser().getUid();

        List<String> time;
        List<String> cost;
        List<String> seats;
        List<String> model;
        List<String> vehicle;

        time = getIntent().getStringArrayListExtra("time");
        cost = getIntent().getStringArrayListExtra("cost");
        model = getIntent().getStringArrayListExtra("model");
        seats = getIntent().getStringArrayListExtra("seats");
        vehicle = getIntent().getStringArrayListExtra("vehicle");

        from1 = getIntent().getStringExtra("from");
        to1 = getIntent().getStringExtra("to");
        date = getIntent().getStringExtra("date");

        from.setText(from1);
        to.setText(to1);

        adapter ada = new adapter(this,time,cost,seats,model,vehicle,from1,to1,date);
        recyclerView.setAdapter(ada);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    /*
    public void send(String f,String t)
    {
        from.setText(f);
        to.setText(t);
    }*/
}
