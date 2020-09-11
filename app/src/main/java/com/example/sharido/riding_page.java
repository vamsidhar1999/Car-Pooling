package com.example.sharido;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class riding_page extends AppCompatActivity {
    TextView source, destination, date, time;
    Button emergency;
    int clickcounter=0;
    Button home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riding_page);
        //clickcounter = 0;

        source = findViewById(R.id.from);
        destination = findViewById(R.id.to);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        home = findViewById(R.id.home);

        String source1 = getIntent().getStringExtra("from");
        String destination1 = getIntent().getStringExtra("to");
        String date1 = getIntent().getStringExtra("date");
        String time1 = getIntent().getStringExtra("time");

        source.setText(source1);
        destination.setText(destination1);
        date.setText(date1);
        time.setText(time1);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
            }
        });


        emergency = findViewById(R.id.emergency);

        emergency.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(riding_page.this);
                builder.setMessage("Are you sure you are in emergency or danger").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getApplicationContext(),emergency.class));
                        finish();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                            }).show();
                return false;
            }

        });

    }

}

