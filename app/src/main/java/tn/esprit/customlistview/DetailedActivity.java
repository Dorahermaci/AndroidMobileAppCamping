package tn.esprit.customlistview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tn.esprit.customlistview.appdatabase.ReclamationDatabase;
import tn.esprit.customlistview.dao.ReclamationDao;
import tn.esprit.customlistview.databinding.ActivityDetailedBinding;

public class DetailedActivity extends AppCompatActivity {
    private ReclamationDao reclamationDao;

    ActivityDetailedBinding binding;
    private Button deleteButton;

    private void initializeDatabase() {
        reclamationDao = ReclamationDatabase.getInstance(this).reclamationDao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeDatabase();
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        if (intent != null) {
            // Jebhom mel intent basa saghrouna declarineha fel main
            String name = intent.getStringExtra("name");
            String time = intent.getStringExtra("time");
            String desc = intent.getStringExtra("desc");
            int id = intent.getIntExtra("id", -1);

            binding.detailName.setText(name);
            binding.detailTime.setText(time);
            binding.detailIngredients.setText(desc);
            binding.detailId.setText(String.valueOf(id));
        }
        deleteButton = findViewById(R.id.deleteButton);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click here
                showDeleteConfirmationDialog();
            }
        });

        // Set click listener for the update button
        binding.updateButton.setOnClickListener(view -> {


            // Navigate to the UpdateReclamationActivity
            Intent updateIntent = new Intent(DetailedActivity.this, UpdateReclamationActivity.class);
            // Pass the necessary data to the UpdateReclamationActivity if needed
            updateIntent.putExtra("name", binding.detailName.getText().toString());
            updateIntent.putExtra("time", binding.detailTime.getText().toString());
            updateIntent.putExtra("desc", binding.detailIngredients.getText().toString());
            updateIntent.putExtra("id", binding.detailId.getText().toString());

            startActivity(updateIntent);
            finish();
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Reclamation");
        builder.setMessage("Are you sure you want to delete this reclamation?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the delete operation
                deleteReclamation();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void deleteReclamation() {
        // Retrieve the id from the TextView
        String idString = binding.detailId.getText().toString();

        // Check if the id is not empty
        if (!idString.isEmpty()) {
            // Parse the id to an integer
            int reclamationId = Integer.parseInt(idString);

            // Use Kotlin coroutines to perform the database operation in the background
            new Thread(() -> {
                // Perform the deleteReclamationById method with the reclamationId
                reclamationDao.deleteReclamationById(reclamationId);

                // Update UI on the main thread if necessary
                runOnUiThread(() -> {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }).start();
        } else {
            // Handle the case where the id is empty or invalid
            // You may show an error message or handle it based on your application logic
        }
    }


}
