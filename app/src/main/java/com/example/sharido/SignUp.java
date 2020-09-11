package com.example.sharido;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    EditText name,email,mobile,password,aadhar,driving;
    Button regbtn;
    RadioButton radio1,radio2;
    //register reg;
    TextView tv1,tv2;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;
    public static final String TAG="TAG";
    public static String email1;
    String from1,to1,date1,time1;

    public static boolean isValid(String em)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (em == null)
            return false;
        return pat.matcher(em).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editText);
        email = findViewById(R.id.editText2);
        mobile = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        aadhar = findViewById(R.id.editText5);
        driving = findViewById(R.id.editText6);
        radio1 = findViewById(R.id.male);
        radio2 = findViewById(R.id.female);
        regbtn = findViewById(R.id.button3);
        tv1 = findViewById(R.id.textView2);
        tv2 = findViewById(R.id.login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar =findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        fstore = FirebaseFirestore.getInstance();

        //reg = new register();
        double year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final String currDate = day+" "+month+" "+year;
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        final String currTime = hour+""+minute;
        final double tim = Double.parseDouble(currTime);

        if(firebaseAuth.getCurrentUser()!=null)
        {

            String userID= firebaseAuth.getCurrentUser().getUid();
            Query documentReference = fstore.collection("rides").document(userID).collection("booked").whereEqualTo("date",currDate);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot snapshots) {
                    for (DocumentSnapshot documentSnapshot:snapshots){
                    String from = documentSnapshot.getString("from");
                    String to = documentSnapshot.getString("to");
                    String date = documentSnapshot.getString("date");
                    String time = documentSnapshot.getString("time");

                    String time2[] = time.split(" ");
                    time = time2[0]+time2[1];
                    double douTime = Double.parseDouble(time);
                    if(date.equals(currDate)) {
                        Intent intent = new Intent(getApplicationContext(),riding_page.class);
                        intent.putExtra("from",from);
                        intent.putExtra("to",to);
                        intent.putExtra("date",date);
                        intent.putExtra("time",time);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        finish();
                    }
                }}
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                }
            });

            }
            if(firebaseAuth.getCurrentUser()!=null)
            {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
            }

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name1 = name.getText().toString().trim();
                email1= email.getText().toString().trim();
                String pwd1 = password.getText().toString().trim();
                final String mobile1 = mobile.getText().toString().trim();
                final String aadhar1 = aadhar.getText().toString().trim();
                final String driving1 = driving.getText().toString().trim();
                final String gender;

                if(TextUtils.isEmpty(name1))
                {
                    name.setError("Please Enter Name");
                    return;
                }
                if(TextUtils.isEmpty(email1))
                {
                    email.setError("Please Enter Email");
                    return;
                }
                else if(!isValid(email1))
                {
                    email.setError("Please enter valid Email");
                    return;
                }
                if(TextUtils.isEmpty(pwd1))
                {
                    password.setError("Please Enter Password");
                    return;
                }
                else if(pwd1.length()<8)
                {
                    password.setError("Password should be minimum of length 8");
                    return;
                }
                if(TextUtils.isEmpty(mobile1))
                {
                    mobile.setError("Please Enter Mobile Number");
                    return;
                }
                if(TextUtils.isEmpty(aadhar1))
                {
                    aadhar.setError("Please Enter Aadhar Number");
                    return;
                }
                if(TextUtils.isEmpty(driving1))
                {
                    driving.setError("Please Enter Driving License");
                    return;
                }
                if(!radio1.isChecked()&&!radio2.isChecked()){
                    tv1.setText("Please select Gender");
                    return;}
                if(radio1.isChecked())
                    gender = "male";
                else
                    gender="female";

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email1,pwd1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUp.this, "Regitered Successfully", Toast.LENGTH_SHORT).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fname",name1);
                            user.put("email",email1);
                            user.put("mobile",mobile1);
                            user.put("aadhar",aadhar1);
                            user.put("license",driving1);
                            user.put("gender",gender);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile created .."+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure"+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }
                        else
                        {
                            Toast.makeText(SignUp.this, "Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                /*reg.setName(name1);
                reg.setEmail(email1);
                reg.setPwd(pwd1);
                reg.setMobile(mobile1);
                reg.setAadhar(aadhar1);
                reg.setDriving(driving1);
                reg.setGender(gender);
                reff.child(mobile1).setValue(reg);*/
                //Toast.makeText(SignUp.this,"Registered Successfully",Toast.LENGTH_LONG).show();


            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }
}
