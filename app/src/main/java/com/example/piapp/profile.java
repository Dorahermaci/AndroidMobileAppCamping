package com.example.piapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.piapp.entities.UserEntity;
public class profile extends AppCompatActivity {

    TextView tEmail, tFullName, tPhoneNumber;
    AppCompatButton buttonUpdate;
    AppCompatButton logout;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tEmail = findViewById(R.id.email);
        tFullName = findViewById(R.id.fullName);
        tPhoneNumber = findViewById(R.id.phoneNumber);
        buttonUpdate = findViewById(R.id.buttonUpdate);
       logout = findViewById(R.id.logoutBtn);

        mPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);


        String email = getIntent().getStringExtra("email");
        String fullName = getIntent().getStringExtra("fullName");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");

        tEmail.setText(email);
        tFullName.setText(fullName);
        tPhoneNumber.setText(phoneNumber);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, updateUser.class);
                intent.putExtra("email", email);
                intent.putExtra("fullName", fullName);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Réinitialisez les valeurs appropriées dans SharedPreferences
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.remove("email");
                preferencesEditor.remove("password");
                preferencesEditor.remove("remember");
                preferencesEditor.apply();

                Intent intent = new Intent(profile.this, login.class);
                startActivity(intent);
                finish();
            }
        });




    }
    }
