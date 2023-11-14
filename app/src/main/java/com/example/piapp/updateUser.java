package com.example.piapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.piapp.entities.UserDao;
import com.example.piapp.entities.UserDatabase;
import com.example.piapp.entities.UserEntity;
import com.example.piapp.profile;

public class updateUser extends AppCompatActivity {
    EditText fullNameEditText, emailEditText, phoneNumberEditText, passwordEditText;
    AppCompatButton updateButton , cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);

        fullNameEditText = findViewById(R.id.fullName);
        emailEditText = findViewById(R.id.email);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        passwordEditText = findViewById(R.id.password);
        updateButton = findViewById(R.id.updateBtn);
        cancelButton = findViewById(R.id.cancelBtn);



        String email = getIntent().getStringExtra("email");
        String fullName = getIntent().getStringExtra("fullName");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");

        emailEditText.setText(email);
        fullNameEditText.setText(fullName);
        phoneNumberEditText.setText(phoneNumber);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFullName = fullNameEditText.getText().toString();
                String newEmail = emailEditText.getText().toString();
                String newPhoneNumber = phoneNumberEditText.getText().toString();

                UserEntity updatedUser = new UserEntity();
                updatedUser.setEmail(newEmail);
                updatedUser.setFullName(newFullName);
                updatedUser.setPhoneNumber(newPhoneNumber);

                // Mettez à jour les données de l'utilisateur dans la base de données en utilisant un thread d'arrière-plan
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Debug", "Before update: Email: " + updatedUser.getEmail() + ", Full Name: " + updatedUser.getFullName() + ", Phone Number: " + updatedUser.getPhoneNumber());

                        UserDao userDao = UserDatabase.getUserDatabase(getApplicationContext()).userDao();
                        userDao.updateUser(updatedUser);

                        Log.d("Debug", "After update: Email: " + updatedUser.getEmail() + ", Full Name: " + updatedUser.getFullName() + ", Phone Number: " + updatedUser.getPhoneNumber());

                        /// Enregistrez les nouvelles informations dans SharedPreferences
                        SharedPreferences.Editor preferencesEditor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                        preferencesEditor.putString("email", newEmail);
                        preferencesEditor.putString("fullName", newFullName);
                        preferencesEditor.putString("phoneNumber", newPhoneNumber);
                        preferencesEditor.apply();

                        Intent intent = new Intent(updateUser.this, profile.class);
                        intent.putExtra("email", newEmail);
                        intent.putExtra("fullName", newFullName);
                        intent.putExtra("phoneNumber", newPhoneNumber);
                        startActivity(intent);
                    }
                }).start();
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }}