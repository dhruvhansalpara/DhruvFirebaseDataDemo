package com.example.firebasedata_demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    EditText etUsername,etPassword;
    TextView tvBtnLogin;
LinearLayout liCreateAcount;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        liCreateAcount= findViewById(R.id.liCreateAcount);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvBtnLogin = findViewById(R.id.tvBtnLogin);


        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();



        liCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



        tvBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (etUsername.getText().toString().trim().isEmpty()) {
                    etUsername.setError("Enter Your Email");
                    etUsername.requestFocus();
                    return;
                }
                if (etPassword.getText().toString().trim().isEmpty()) {
                    etPassword.setError("Enter Your Email");
                    etPassword.requestFocus();
                    return;
                }

                if (password.length() > 6 ){
                    etPassword.setError("Password must be less than 6 characters");
                }

                progressDialog.show();



                // Authanticate  user

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Login Sucessfull",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            progressDialog.dismiss();

                        }else {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext()," try Again!"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }



                    }
                });



            }
        });


    }
}
