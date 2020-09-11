package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class booking extends AppCompatActivity {
    TextView from,to,cost,model,time,date,seats;
    Button book;
    String userID;
    String from1,to1,cost1,model1,seats1,date1,time1,vehicle,license;
    String email,subject,message,bookername,offeredname,bookermobile,offeredmobile;
    String mail;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        cost = findViewById(R.id.cost);
        time = findViewById(R.id.time);
        model = findViewById(R.id.model);
        seats = findViewById(R.id.seats);
        date = findViewById(R.id.date);
        book = findViewById(R.id.book);



        from1 = getIntent().getStringExtra("from");
        to1 = getIntent().getStringExtra("to");
        cost1 = getIntent().getStringExtra("cost");
        model1 = getIntent().getStringExtra("model");
        time1 = getIntent().getStringExtra("time");
        date1 = getIntent().getStringExtra("date");
        seats1 = getIntent().getStringExtra("seats");
        mail = "vamsidhar1999@gmail.com";
        vehicle = getIntent().getStringExtra("vehicle");

        from.setText(from1);
        cost.setText(cost1);
        model.setText(model1);
        time.setText(time1);
        date.setText(date1);
        to.setText(to1);
        seats.setText(seats1);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                license = offer.l;

                CollectionReference collectionReference = firebaseFirestore.collection("users");

                Query query = collectionReference.whereEqualTo("license",license);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult()))
                        {
                            mail = documentSnapshot.getString("email");
                            offeredname = documentSnapshot.getString("fname");
                            offeredmobile = documentSnapshot.getString("mobile");
                        }
                    }
                });


                final Intent intent = new Intent(getApplicationContext(),booked_ride.class);
                intent.putExtra("from",from1);
                intent.putExtra("to",to1);
                intent.putExtra("cost",cost1);
                intent.putExtra("time",time1);
                intent.putExtra("date",date1);
                intent.putExtra("model",model1);
                intent.putExtra("seats",seats1);
                intent.putExtra("vehicle",vehicle);

                userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

                userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                DocumentReference collectionReference1 = firebaseFirestore.collection("users").document(userID);
                collectionReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        email = documentSnapshot.getString("email");
                        bookername = documentSnapshot.getString("fname");
                        bookermobile = documentSnapshot.getString("mobile");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(booking.this,"no data",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                final DocumentReference documentReference = firebaseFirestore.collection("rides").document(userID).collection("booked").document();

                Map<String,Object> user = new HashMap<>();
                user.put("from",from1);
                user.put("to",to1);
                user.put("date",date1);
                user.put("time",time1);
                user.put("number of seats",seats1);
                user.put("Cost per person",cost1);
                user.put("car model",model1);
                user.put("vehicle number",vehicle);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(booking.this,"booked successfully",Toast.LENGTH_SHORT).show();

                        String subject1,message1;
                        subject = "Ride Booking Conformation";
                        message = "Dear "+bookername+"\nYour Ride has been booked from "+from1+" to "+to1+" on "+date1+" "+time1+" for "+seats1+" seats."+" It costs "+cost1+" for this ride.\nName: "+offeredname+"\nMobile: "+offeredmobile;

                        subject1 = "Seat has been booked";
                        message1 = "Dear "+offeredname+"\nYour ride on "+date1+" has a new co-passenger named "+bookername+" mobile number: "+bookermobile;
                        JavaMailAPI javaMailAPI = new JavaMailAPI(booking.this,email,subject,message);
                        javaMailAPI.execute();
                        JavaMailAPI javaMailAPI1 = new JavaMailAPI(booking.this,mail,subject1,message1);
                        javaMailAPI1.execute();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(booking.this,"! Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}
