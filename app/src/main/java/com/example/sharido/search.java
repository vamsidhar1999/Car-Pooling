package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class search extends Fragment {

    EditText from,to;
    DatePicker datePicker;
    Button search;
    String data="";
    TextView data1;
    double year;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    ProgressBar progressBar;
    //String destination,cost,date1,time,vehicle,seats,model;

    //public List<String> hour1 = new ArrayList<String>();
    public List<String> time1 = new ArrayList<String>();
    private static List<String> cost1 = new ArrayList<String>();
    private static List<String> seats1 = new ArrayList<String>();
    private static List<String> model1 = new ArrayList<String>();
    private static List<String> vehicle1 = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);

        from = v.findViewById(R.id.editText1);
        to = v.findViewById(R.id.editText2);
        datePicker = v.findViewById(R.id.date);
        search = v.findViewById(R.id.button3);

        datePicker.setMinDate(System.currentTimeMillis());

        //final search s = new search();

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        progressBar = v.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);

                final String from1 = from.getText().toString();
                final String to1 = to.getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                year = datePicker.getYear();
                final String date = day+" "+ month+" "+year;

                Map<String,Object> user = new HashMap<>();
                user.get("from");
                user.get("to");
                user.get("date");
                user.get("time");
                user.get("vehicle number");
                user.get("number of seats");
                user.get("Cost per person");
                user.get("car model");

                String userID = firebaseAuth.getCurrentUser().getUid();

                /*final DocumentReference documentReference = fstore.collection("rides").document(userID).collection("ride").document(
                        "SQsnLGbb96EAC3z700ax");
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        data1.setText(documentSnapshot.getString("time"))
                    }
                });*/


                fstore.collectionGroup("ride").whereEqualTo("date",date).whereEqualTo("from",from1).whereEqualTo("to",to1)

                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot:snapshots)
                        {
                            //data1.setText(from1);
                            String source = queryDocumentSnapshot.getString("from");
                            String destination = queryDocumentSnapshot.getString("to");
                            String date1 = queryDocumentSnapshot.getString("date");
                            String time = queryDocumentSnapshot.getString("time");
                            //String minute = queryDocumentSnapshot.getString("minute");
                            String vehicle = queryDocumentSnapshot.getString("vehicle number");
                            String cost  = queryDocumentSnapshot.getString("Cost per person");
                            String seats = queryDocumentSnapshot.getString("number of seats");
                            String model = queryDocumentSnapshot.getString("car model");

                            //data += source+" "+destination+" "+date1+" "+time+" "+vehicle+" "+cost+" "+seats+" "+model+"\n";
                            time1.add(time);
                            model1.add(model);
                            cost1.add(cost);
                            seats1.add(seats);
                            vehicle1.add(vehicle);
                        }
                        //String temp = time1.get(0);
                        //search s = new search();
                        ///List<String> temp = s.sendTime();
                        //String temp1 = temp.get(0);
                        //data1.setText(data);
                        Intent intent;
                        intent = new Intent(getActivity(),searched_rides.class);
                        intent.putStringArrayListExtra("time",(ArrayList<String>)time1);
                        intent.putStringArrayListExtra("cost",(ArrayList<String>)cost1);
                        intent.putStringArrayListExtra("model",(ArrayList<String>)model1);
                        intent.putStringArrayListExtra("seats",(ArrayList<String>)seats1);
                        intent.putStringArrayListExtra("vehicle",(ArrayList<String>)vehicle1);
                        intent.putExtra("from",from1);
                        intent.putExtra("to",to1);
                        intent.putExtra("date",date);

                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        data1.setText("no data");
                    }
                });
                /*data1.setText("button");
                fstore.collection("landmarks").whereEqualTo("type", "museum").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                // [START_EXCLUDE]
                                data1.setText("so");
                                for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                                    //Log.d(TAG, snap.getId() + " => " + snap.getData());
                                    //data1.setText("yes");
                                    data1.setText(snap.getString("name"));
                                }
                                // [END_EXCLUDE]
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        data1.setText("no data");
                    }
                });*/
            }
        });

        return v;
    }
}
