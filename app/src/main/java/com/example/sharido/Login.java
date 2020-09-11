package com.example.sharido;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText email,pwd;
    Button loginbtn;
    TextView tv,forgot,login;
    static String mobile1;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    public static String email1;

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
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.editText2);
        tv = findViewById(R.id.textView);
        login = findViewById(R.id.login);
        forgot = findViewById(R.id.forgot);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


        loginbtn = findViewById(R.id.button3);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                email1 = email.getText().toString().trim();
                final String pwd1 = pwd.getText().toString().trim();
                if(TextUtils.isEmpty(email1))
                {
                    email.setError("Please enter your Email");
                    return;
                }
                else if(!isValid(email1))
                {
                    email.setError("Please enter valid Email");
                    return;
                }
                if(TextUtils.isEmpty(pwd1))
                {
                    pwd.setError("Please enter Password");
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(email1,pwd1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this,"Loggedin Successfuully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                            finish();
                        }
                        else {
                            Toast.makeText(Login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT);
                            tv.setText("Please Enter Valid Credentils");
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

                /*reff = FirebaseDatabase.getInstance().getReference(mobile1);
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String mobile2 = (String) dataSnapshot.child("mobile").getValue();
                            String pwd2 = (String) dataSnapshot.child("pwd").getValue();

                            if ((mobile1.equals(mobile2)) && (pwd1.equals(pwd2))) {
                                startActivity(new Intent(getApplicationContext(),Home.class));
                                Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                                finish();
                                shared sh=new shared(getApplicationContext());
                                sh.seconduser();
                            } else {
                                tv.setText("Please Enter Valid Details");
                            }
                    }
                });*/
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetmail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Email for Reset Link");
                passwordResetDialog.setView(resetmail);

                passwordResetDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetmail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Email sent Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Email Not Sent !"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }
    /*public String send(){
        return mobile1;
    }*/
}
