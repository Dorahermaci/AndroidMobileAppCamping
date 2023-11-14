package com.example.camping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.camping.R;
import com.example.camping.ui.evenement.admin.EvenementListAdminActivity;
import com.example.camping.ui.evenement.client.EvenementListClientActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClient = findViewById(R.id.btnClient);
        Button btnAdmin = findViewById(R.id.btnAdmin);

        btnClient.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EvenementListClientActivity.class));
        });

        btnAdmin.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EvenementListAdminActivity.class));
        });
    }
}
