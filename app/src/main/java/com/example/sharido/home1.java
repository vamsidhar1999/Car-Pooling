package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class home1 extends Fragment {

    Button ride,offer;
    Button offered,booked;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;

    public List<String> time1 = new ArrayList<String>();
    private static List<String> cost1 = new ArrayList<String>();
    private static List<String> seats1 = new ArrayList<String>();
    private static List<String> model1 = new ArrayList<String>();
    private static List<String> vehicle1 = new ArrayList<String>();
    private static List<String> destination1 = new ArrayList<String>();
    private static List<String> source1 = new ArrayList<String>();
    private static List<String> date1 = new ArrayList<String>();

    public List<String> time2 = new ArrayList<String>();
    private static List<String> cost2 = new ArrayList<String>();
    private static List<String> seats2 = new ArrayList<String>();
    private static List<String> model2 = new ArrayList<String>();
    private static List<String> vehicle2 = new ArrayList<String>();
    private static List<String> destination2 = new ArrayList<String>();
    private static List<String> source2 = new ArrayList<String>();
    private static List<String> date2 = new ArrayList<String>();

    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home1, container, false);
        ride = v.findViewById(R.id.btnride);
        offer = v.findViewById(R.id.btnoffer);
        offered = v.findViewById(R.id.offeredrides);
        booked = v.findViewById(R.id.bookedrides);

        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.fragment_container,new search());
                fragmentTransaction1.commit();
            }
        });
        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container, new offer());
                fragmentTransaction2.commit();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();

        booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fstore.collection("rides").document(userID).collection("booked")

                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : snapshots) {
                            //data1.setText(from1);
                            String source = queryDocumentSnapshot.getString("from");
                            String destination = queryDocumentSnapshot.getString("to");
                            String date = queryDocumentSnapshot.getString("date");
                            String time = queryDocumentSnapshot.getString("time");
                            String vehicle = queryDocumentSnapshot.getString("vehicle number");
                            String cost = queryDocumentSnapshot.getString("Cost per person");
                            String seats = queryDocumentSnapshot.getString("number of seats");
                            String model = queryDocumentSnapshot.getString("car model");

                            source1.add(source);
                            destination1.add(destination);
                            date1.add(date);
                            time1.add(time);
                            model1.add(model);
                            cost1.add(cost);
                            seats1.add(seats);
                            vehicle1.add(vehicle);
                        }
                        Intent intent = new Intent(getActivity(),past_booked.class);
                        intent.putStringArrayListExtra("time",(ArrayList<String>)time1);
                        intent.putStringArrayListExtra("cost",(ArrayList<String>)cost1);
                        intent.putStringArrayListExtra("model",(ArrayList<String>)model1);
                        intent.putStringArrayListExtra("seats",(ArrayList<String>)seats1);
                        intent.putStringArrayListExtra("vehicle",(ArrayList<String>)vehicle1);
                        intent.putStringArrayListExtra("from",(ArrayList<String>)source1);
                        intent.putStringArrayListExtra("to",(ArrayList<String>)destination1);
                        intent.putStringArrayListExtra("date",(ArrayList<String>)date1);
                        startActivity(intent);
                    }

                });
            }
        });
        offered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fstore.collection("rides").document(userID).collection("ride")

                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : snapshots) {
                            //data1.setText(from1);
                            String source = queryDocumentSnapshot.getString("from");
                            String destination = queryDocumentSnapshot.getString("to");
                            String date = queryDocumentSnapshot.getString("date");
                            String time = queryDocumentSnapshot.getString("time");
                            String vehicle = queryDocumentSnapshot.getString("vehicle number");
                            String cost = queryDocumentSnapshot.getString("Cost per person");
                            String seats = queryDocumentSnapshot.getString("number of seats");
                            String model = queryDocumentSnapshot.getString("car model");

                            source2.add(source);
                            destination2.add(destination);
                            date2.add(date);
                            time2.add(time);
                            model2.add(model);
                            cost2.add(cost);
                            seats2.add(seats);
                            vehicle2.add(vehicle);
                        }
                        Intent intent = new Intent(getActivity(),past_offered.class);
                        intent.putStringArrayListExtra("time",(ArrayList<String>)time2);
                        intent.putStringArrayListExtra("cost",(ArrayList<String>)cost2);
                        intent.putStringArrayListExtra("model",(ArrayList<String>)model2);
                        intent.putStringArrayListExtra("seats",(ArrayList<String>)seats2);
                        intent.putStringArrayListExtra("vehicle",(ArrayList<String>)vehicle2);
                        intent.putStringArrayListExtra("from",(ArrayList<String>)source2);
                        intent.putStringArrayListExtra("to",(ArrayList<String>)destination2);
                        intent.putStringArrayListExtra("date",(ArrayList<String>)date2);
                        startActivity(intent);
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startActivity(new Intent(getActivity(),Home.class));
                    }
                });

            }
        });
        return  v;
    }

}
