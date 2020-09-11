package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class added extends AppCompatActivity {
    TextView source,destination,date1,time,cost,model,seats,vehicle,license;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added);

        source = findViewById(R.id.from);
        destination = findViewById(R.id.to);
        date1 = findViewById(R.id.date);
        time = findViewById(R.id.time);
        cost = findViewById(R.id.cost);
        model = findViewById(R.id.model);
        seats = findViewById(R.id.seats);
        vehicle = findViewById(R.id.vehicle);
        license = findViewById(R.id.license);
        home = findViewById(R.id.home);

        source.setText(getIntent().getStringExtra("from"));
        destination.setText(getIntent().getStringExtra("to"));
        date1.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));
        cost.setText(getIntent().getStringExtra("cost"));
        model.setText(getIntent().getStringExtra("model"));
        seats.setText(getIntent().getStringExtra("seats"));
        vehicle.setText(getIntent().getStringExtra("vehicle"));
        license.setText(getIntent().getStringExtra("license"));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
            }
        });

    }
}
