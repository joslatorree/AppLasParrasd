package com.solmov.appmovillasparras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

        EditText edtxtemail, edtxtpassword;

        String email, password;

        Button mButtonLogin;


        private FirebaseAuth mAuth;
        @Override
        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mAuth=FirebaseAuth.getInstance();
            setContentView(R.layout.activity_login);
            edtxtemail = findViewById(R.id.et_txtmail);
            edtxtpassword = findViewById(R.id.et_password);
            mButtonLogin = findViewById(R.id.button_signin);


            mButtonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email = edtxtemail.getText().toString();
                    password = edtxtpassword.getText().toString();
                    if(!email.isEmpty()&&!password.isEmpty())
                    {
                        loginUser();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        private void loginUser(){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this,"No se inicio sesion",Toast.LENGTH_SHORT);

                    }
                    else{
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                }

            });

        }

}