package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class friends extends AppCompatActivity {

    EditText name1,name2,name3,number1,number2,number3;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    String userID;
    Button submit;
    String strname1,strname2,strname3,strnumber1,strnumber2,strnumber3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        submit = findViewById(R.id.submit);

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strname1 = name1.getText().toString();
                strname2 = name2.getText().toString();
                strname3 = name3.getText().toString();
                strnumber1 = number1.getText().toString();
                strnumber2 = number2.getText().toString();
                strnumber3 = number3.getText().toString();

                userID = firebaseAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("friends").document(userID);

                Map<String,String> user = new HashMap<>();
                user.put("name1",strname1);
                user.put("name2",strname2);
                user.put("name3",strname3);
                user.put("number1",strnumber1);
                user.put("number2",strnumber2);
                user.put("number3",strnumber3);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(friends.this,"Friends Contacts saved successfully",Toast.LENGTH_SHORT);
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
                }
            });
            }
        });

    }
}
