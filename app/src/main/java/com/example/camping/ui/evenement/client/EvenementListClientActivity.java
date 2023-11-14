package com.example.camping.ui.evenement.client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camping.R;
import com.example.camping.adapters.EvenementClientAdapter;
import com.example.camping.database.EvenementDBHelper;

public class EvenementListClientActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenement_list_client);

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

        recyclerView.setAdapter(new EvenementClientAdapter(evenementDBHelper.getAll(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
