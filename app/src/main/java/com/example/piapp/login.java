package com.example.piapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.piapp.entities.UserDao;
import com.example.piapp.entities.UserDatabase;
import com.example.piapp.entities.UserEntity;

public class login extends AppCompatActivity {

    EditText email, password;
    AppCompatButton buttonLogin, buttonSignup;
    CheckBox mCheckBoxRemember;

    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);
        mCheckBoxRemember = findViewById(R.id.rememberMe);
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

       boolean rememberMe = mPrefs.getBoolean("remember", false);
        mCheckBoxRemember.setChecked(rememberMe);
        // Si "Se souvenir de moi" est coché, restaurer l'e-mail et le mot de passe précédemment enregistrés
        if (rememberMe) {
            String savedEmail = mPrefs.getString("email", "");
            String savedPassword = mPrefs.getString("password", "");

            email.setText(savedEmail);
            password.setText(savedPassword);
        }

        mCheckBoxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putBoolean("remember", isChecked);
                editor.apply();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(emailText, passwordText);

                            if (userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // Enregistrez les données de l'utilisateur dans les préférences partagées lors de la connexion
                                SharedPreferences.Editor editor = mPrefs.edit();
                                editor.putString("email", emailText);
                                editor.putString("fullName", userEntity.getFullName());
                                editor.putString("phoneNumber", userEntity.getPhoneNumber());
                                editor.putString("password", passwordText); // Ajout du mot de passe
                                editor.apply();

                                startActivity(new Intent(
                                                login.this, profile.class
                                        ).putExtra("email", emailText)
                                                .putExtra("fullName", userEntity.getFullName())
                                                .putExtra("phoneNumber", userEntity.getPhoneNumber())
                                );
                            }
                        }
                    }).start();
                }
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, signup.class));
            }
        });
    }
}
