package com.example.sharido;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class emergency extends AppCompatActivity {

    String userID;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    String message1,message2,message3;
    String n1,n2,n3,num1,num2,num3;

    ArrayList<String> vehicle1 = new ArrayList<String>();
    ArrayList<String> source1 = new ArrayList<String>();
    ArrayList<String> destination1 = new ArrayList<String>();
    ArrayList<String> date1 = new ArrayList<String>();
    ArrayList<String> time1 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();


        int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

        PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, getIntent(),0);

        if (ContextCompat.checkSelfPermission(emergency.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(emergency.this, new String [] {Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }

        double year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final String currDate = day+" "+month+" "+year;

        fstore.collection("rides").document(userID).collection("booked").whereEqualTo("date",currDate).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        for (DocumentSnapshot snapshot:snapshots)
                        {
                            String from = snapshot.getString("from");
                            String to = snapshot.getString("to");
                            String date = snapshot.getString("date");
                            String time = snapshot.getString("time");
                            String vehicle = snapshot.getString("vehicle number");

                            source1.add(from);
                            destination1.add(to);
                            date1.add(date);
                            time1.add(time);
                            vehicle1.add(vehicle);
                        }
                        message1 = "Urgent \nIam in DANGER. Iam travelling from "+source1.get(0)+" to "+destination1.get(0)+" started on "+date1.get(0)+" time "+time1.get(0)+". And the vehicle number is "+vehicle1.get(0)+" PLEASE HELP ME!!!";
                        message1 = "Urgent \nIam in DANGER. Iam travelling from "+source1.get(0)+" to "+destination1.get(0)+" started on "+date1.get(0)+" time "+time1.get(0)+". And the vehicle number is "+vehicle1.get(0)+" PLEASE HELP ME!!!";
                        message1 = "Urgent \nIam in DANGER. Iam travelling from "+source1.get(0)+" to "+destination1.get(0)+" started on "+date1.get(0)+" time "+time1.get(0)+". And the vehicle number is "+vehicle1.get(0)+" PLEASE HELP ME!!!";

                    }
                });



        fstore.collection("friends").document(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name1 = documentSnapshot.getString("name1");
                        String name2 = documentSnapshot.getString("name2");
                        String name3 = documentSnapshot.getString("name3");
                        String number1 = documentSnapshot.getString("number1");
                        String number2 = documentSnapshot.getString("number2");
                        String number3 = documentSnapshot.getString("number3");

                        n1=name1;
                        n2=name2;
                        n3=name3;
                        num1=number1;
                        num2=number2;
                        num3=number3;

                        TextView num = findViewById(R.id.num);
                        num.setText(num1);

                        SmsManager sms = SmsManager.getDefault();
                        SmsManager sms1 = SmsManager.getDefault();
                        SmsManager sms2 = SmsManager.getDefault();
                        sms.sendTextMessage(num1,null,message1,null,null);
                        sms1.sendTextMessage(num2,null,message2,null,null);
                        sms2.sendTextMessage(num3,null,message3,null,null);
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                    }
                });

    }
}