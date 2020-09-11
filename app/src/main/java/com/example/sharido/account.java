package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.fragment.app.Fragment;


public class account extends Fragment {

    TextView name,email,number,aadhar,gender,license,friends;
    Button signout;
    FirebaseAuth firebaseAuth;
    String userID;
    FirebaseFirestore fstore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_account, container, false);

        signout = v.findViewById(R.id.signout);
        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        number = v.findViewById(R.id.number);
        aadhar = v.findViewById(R.id.aadhar);
        gender = v.findViewById(R.id.gender);
        license = v.findViewById(R.id.license);
        friends = v.findViewById(R.id.friends);

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),friends.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        fstore.collection("users").document(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        name.setText(documentSnapshot.getString("fname"));
                        email.setText(documentSnapshot.getString("email"));
                        number.setText(documentSnapshot.getString("mobile"));
                        aadhar.setText(documentSnapshot.getString("aadhar"));
                        license.setText(documentSnapshot.getString("license"));
                        gender.setText(documentSnapshot.getString("gender"));
                    }
                });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),Login.class));
                getActivity().finish();
            }
        });
        return v;

    }
}
