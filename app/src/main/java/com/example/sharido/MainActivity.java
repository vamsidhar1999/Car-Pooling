package com.example.sharido;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_signup(View view){
        startActivity(new Intent(getApplicationContext(),SignUp.class));
    }
    public void btn_login(View view){
        startActivity(new Intent(getApplicationContext(),Login.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //shared sh = new shared(getApplicationContext());
        //sh.firstuser();
    }
}