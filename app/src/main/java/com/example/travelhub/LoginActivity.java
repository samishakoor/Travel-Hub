package com.example.travelhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private String email;
    private String password;
    FirebaseAuth auth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //link to signup activity
        TextView create_account = (TextView) findViewById(R.id.create_account_btn);
        create_account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent signup_intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(signup_intent);
            }
        });

        //login process
        Button login_button = (Button) findViewById(R.id.login_btn);
        EditText user_email=(EditText) findViewById(R.id.login_email);
        EditText user_password=(EditText) findViewById(R.id.login_password);
        login_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                email=user_email.getText().toString().trim();
                password=user_password.getText().toString().trim();

                if(!email.isEmpty())
                {
                    if(!password.isEmpty()) {
                        progressDialog.setMessage("Logging in...");
                        progressDialog.show();
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this,"Login Successfull", Toast.LENGTH_SHORT).show();
                                        progressDialog.cancel();

                                        Intent login_success_intent=new Intent (LoginActivity.this, CustomerProfile.class);
                                        startActivity(login_success_intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.cancel();
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please Enter Your Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //resetting password
        TextView reset_password_btn = (TextView) findViewById(R.id.recover_password);
        reset_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = user_email.getText().toString();
                if (!email.isEmpty()) {
                    progressDialog.setMessage("Sending Mail");
                    progressDialog.show();

                    auth.sendPasswordResetEmail(email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                }
        }
        });

    }
}