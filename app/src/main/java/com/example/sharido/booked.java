package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class booked extends AppCompatActivity {
    TextView source,destination,time,cost,date,model,seats,vehicle;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);

        source = findViewById(R.id.from);
        destination = findViewById(R.id.to);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        cost = findViewById(R.id.cost);
        model = findViewById(R.id.model);
        seats = findViewById(R.id.seats);
        home = findViewById(R.id.home);
        vehicle = findViewById(R.id.vehicle);


        source.setText(getIntent().getStringExtra("from"));
        destination.setText(getIntent().getStringExtra("to"));
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));
        cost.setText(getIntent().getStringExtra("cost"));
        model.setText(getIntent().getStringExtra("model"));
        seats.setText(getIntent().getStringExtra("seats"));
        vehicle.setText(getIntent().getStringExtra("vehicle"));
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
            }
        });

    }
}
