package com.example.firebasedata_demo.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasedata_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etfirstname ,etlastname,etEmail,etPassword;
    TextView tvBtnSignup;

    ProgressDialog progressDialog;
    private FirebaseFirestore db;
    FirebaseAuth firebaseAuth ;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tvBtnSignup = findViewById(R.id.tvBtnSignup);
        etfirstname = findViewById(R.id.etfirstname);
        etlastname = findViewById(R.id.etlastname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);


        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        tvBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                String firstname = etfirstname.getText().toString();
                String lastname  = etlastname.getText().toString();
                String email  = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                HashMap<String, Object> data = new HashMap<>();

                data.put("Firstname",firstname);
                data.put("Lastname",lastname);
                data.put("Email",email);
                data.put("Password",password);

                if (etfirstname.getText().toString().trim().isEmpty()) {
                    etfirstname.setError("Enter Your Firstname");
                    etfirstname.requestFocus();
                    return;
                }
                if (etlastname.getText().toString().trim().isEmpty()) {
                    etlastname.setError("Enter Your lastname");
                    etlastname.requestFocus();
                    return;
                }
                if (etEmail.getText().toString().trim().isEmpty()) {
                    etEmail.setError("Enter Your Email");
                    etEmail.requestFocus();
                    return;
                }
                if (etPassword.getText().toString().trim().isEmpty()) {
                    etPassword.setError("Enter Your password");
                    etPassword.requestFocus();
                    return;
                }


                if (password.length() > 6 ){
                    etPassword.setError("Password must be less than 6 characters");
                    etPassword.setError("Enter Your password");
                    etPassword.requestFocus();
                }



                db.collection("first collection").add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Data is save",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Data is not save",Toast.LENGTH_SHORT).show();
                        Log.e("test","--"+e.getMessage().toString());

                    }
                });


                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User Register Sucessfull",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            progressDialog.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext()," try Again!"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });



            }
        });





    }
}
