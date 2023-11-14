package com.example.camping.ui.evenement.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camping.R;
import com.example.camping.adapters.EvenementAdminAdapter;
import com.example.camping.database.EvenementDBHelper;

public class EvenementListAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenement_list_admin);

        // Add Evenement Button
        Button buttonAddEvenement = findViewById(R.id.buttonAddEvenement);
        buttonAddEvenement.setOnClickListener(
                v -> startActivity(new Intent(this, ManageEvenementAdminActivity.class))
        );

        recyclerView = findViewById(R.id.recyclerViewEvenementList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        EvenementDBHelper evenementDBHelper = new EvenementDBHelper(this);
        evenementDBHelper.open();

        recyclerView.setAdapter(new EvenementAdminAdapter(evenementDBHelper.getAll(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
