package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class offer extends Fragment{

    public offer(){}

    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText from,to,vehicle,cost,model;
    private Spinner seat;
    private add off;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String userID;
    String data="";
    TextView data1;
    public static String l;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        datePicker = view.findViewById(R.id.date1);

        datePicker.setMinDate(System.currentTimeMillis());

        from = view.findViewById(R.id.from);
        to = view.findViewById(R.id.to);
        timePicker=view.findViewById(R.id.time);
        vehicle = view.findViewById(R.id.vehicle);
        seat = view.findViewById(R.id.seat);
        cost = view.findViewById(R.id.cost);
        model = view.findViewById(R.id.model);
        Button addbtn = view.findViewById(R.id.btn);


        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                double year = datePicker.getYear();
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                String from1 = from.getText().toString().trim();
                String to1 = to.getText().toString().trim();
                String date = day+" "+ month+" "+year;
                String time = hour+" "+minute;
                String vehicle1 = vehicle.getText().toString().trim();
                String seat1 = seat.getSelectedItem().toString().trim();
                String cost1 = cost.getText().toString();
                String model1 = model.getText().toString();

                if(TextUtils.isEmpty(from1))
                {
                    from.setError("Please Enter the Starting Location");
                    return;
                }
                if(TextUtils.isEmpty(to1))
                {
                    to.setError("Please Enter Destination");
                    return;
                }
                if(TextUtils.isEmpty(vehicle1))
                {
                    vehicle.setError("Please Enter Vehicle Number");
                    return;
                }
                if(TextUtils.isEmpty(cost1))
                {
                    cost.setError("Please Enter Cost");
                    return;
                }
                if(TextUtils.isEmpty(model1))
                {
                    from.setError("Please Enter the Model Name of the car");
                    return;
                }
                userID = firebaseAuth.getCurrentUser().getUid();

                DocumentReference documentReference = fstore.collection("users").document(userID);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String license = documentSnapshot.getString("license");
                        l=license;
                    }
                });

                DocumentReference documentReference1 = fstore.collection("rides").document(userID).collection("ride").document();

                Map<String,Object> user = new HashMap<>();
                user.put("from",from1);
                user.put("to",to1);
                user.put("date",date);
                user.put("time",time);
                //user.put("minute",minute);
                user.put("vehicle number",vehicle1);
                user.put("number of seats",seat1);
                user.put("Cost per person",cost1);
                user.put("car model",model1);
                user.put("license",l);

                final Intent intent = new Intent(getActivity(),booked_ride.class);
                intent.putExtra("from",from1);
                intent.putExtra("to",to1);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("vehicle",vehicle1);
                intent.putExtra("seats",seat1);
                intent.putExtra("cost",cost1);
                intent.putExtra("model",model1);
                intent.putExtra("license",l);

                documentReference1.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

            }
        });

        return view;
    }
}