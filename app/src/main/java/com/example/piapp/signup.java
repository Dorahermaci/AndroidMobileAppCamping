package com.example.piapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.piapp.entities.UserDao;
import com.example.piapp.entities.UserDatabase;
import com.example.piapp.entities.UserEntity;

public class signup   extends AppCompatActivity {
EditText fullName, email  , password , phoneNumber;
    AppCompatButton buttonSignup , buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonSignup = findViewById(R.id.buttonSignup);
        buttonLogin=findViewById(R.id.buttonLogin);
        phoneNumber=findViewById(R.id.phoneNumber);


        buttonSignup.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        UserEntity userEntity = new UserEntity();


                        //creating user entity
                        userEntity.setFullName(fullName.getText().toString());
                        userEntity.setEmail(email.getText().toString());
                        userEntity.setPassword(password.getText().toString());
                        userEntity.setPhoneNumber(phoneNumber.getText().toString());
                        if (validateInput(userEntity)) {

                            //insert operations

                            UserDatabase userDatabase= UserDatabase.getUserDatabase(getApplicationContext());
                            UserDao userDao=userDatabase.userDao();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    //register user
                                    userDao.registerUser(userEntity);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            Toast.makeText(getApplicationContext() , "User Registred"  , Toast.LENGTH_SHORT).show();


                                        }
                                    });
                                }
                            }).start();


                        } else {
                            Toast.makeText(getApplicationContext(), "Fill all fields !", Toast.LENGTH_SHORT).show();


                        }
                    }
                });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this , login.class));
            }
        });
    }


        private Boolean validateInput(UserEntity userEntity) {

            if (userEntity.getFullName().isEmpty() ||
                    userEntity.getPassword().isEmpty() ||
                    userEntity.getEmail().isEmpty() ||
            userEntity.getPhoneNumber().isEmpty()) {
   return false;
        }

            return true;

    }  }

