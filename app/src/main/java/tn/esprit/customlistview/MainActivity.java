package tn.esprit.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import tn.esprit.customlistview.appdatabase.ReclamationDatabase;
import tn.esprit.customlistview.dao.ReclamationDao;
import tn.esprit.customlistview.databinding.ActivityMainBinding;
import tn.esprit.customlistview.entities.Reclamation;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ReclamationListAdapter listAdapter;
    private ReclamationDao reclamationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeDatabase();

        // Fetch Reclamations and update UI
        fetchReclamations();

        // ... rest of your code

        binding.fabAdd.setOnClickListener(view -> navigateToAddInterface());

    }

    private void initializeDatabase() {
        ReclamationDatabase reclamationDatabase = ReclamationDatabase.getInstance(this);
        reclamationDao = reclamationDatabase.reclamationDao();
    }

    private void fetchReclamations() {
        new Thread(() -> {
            ArrayList<Reclamation> reclamations = new ArrayList<>(reclamationDao.getAllReclamations());

            runOnUiThread(() -> {
                updateUI(reclamations);
            });
        }).start();
    }

    private void updateUI(ArrayList<Reclamation> reclamations) {
        listAdapter = new ReclamationListAdapter(this, reclamations);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);

        binding.listview.setOnItemClickListener((adapterView, view, i, l) -> {
            Reclamation selectedReclamation = reclamations.get(i);

            Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
            intent.putExtra("name", selectedReclamation.getObjet());
            intent.putExtra("desc", selectedReclamation.getContenu());
            intent.putExtra("time", selectedReclamation.getDate());
            intent.putExtra("id", selectedReclamation.getId());

            startActivity(intent);
        });


    }

    private void navigateToAddInterface() {
        // Replace AddInterfaceActivity.class with the actual class for your add interface
        Intent intent = new Intent(MainActivity.this, AddReclamationActivity.class);
        startActivity(intent);
        finish();
    }
}
