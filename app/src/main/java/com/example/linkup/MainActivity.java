package com.example.linkup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnRegister,btnLogin;
    EditText etEmail,etPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPass);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent goToChat=new Intent(MainActivity.this,ChatPage.class);
            goToChat.putExtra("email",currentUser.getEmail());
            goToChat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            goToChat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            goToChat.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(goToChat);
        }
    }

    public void onLogin(View view){
        if(etEmail.getText().toString().equals("") || etPass.getText().toString().equals("")){
            etEmail.setError("Enter all fields");
            etPass.setError("Enter all fields");
            return;
        }

        String email=etEmail.getText().toString();
        String password=etPass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            Intent goToChat=new Intent(MainActivity.this,ChatPage.class);
                            goToChat.putExtra("email",currentUser.getEmail());
                            goToChat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            goToChat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            goToChat.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(goToChat);
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onRegister(View view){
        if(etEmail.getText().toString().equals("") || etPass.getText().toString().equals("")){
            etEmail.setError("Enter all fields");
            etPass.setError("Enter all fields");
            return;
        }

        String email=etEmail.getText().toString();
        String password=etPass.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            Intent goToChat=new Intent(MainActivity.this,ChatPage.class);
                            goToChat.putExtra("email",currentUser.getEmail());
                            goToChat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            goToChat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            goToChat.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(goToChat);
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
