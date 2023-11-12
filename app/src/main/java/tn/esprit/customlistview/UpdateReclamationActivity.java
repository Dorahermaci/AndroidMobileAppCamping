package tn.esprit.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tn.esprit.customlistview.appdatabase.ReclamationDatabase;
import tn.esprit.customlistview.dao.ReclamationDao;
import tn.esprit.customlistview.databinding.ActivityUpdateReclamationBinding;
import tn.esprit.customlistview.entities.Reclamation;

public class UpdateReclamationActivity extends AppCompatActivity {

    private ReclamationDao reclamationDao;
    private Reclamation reclamation;

    private EditText editObjet;
    private EditText editContenu;
    private EditText editId;
    private TextView editDate;
    private Button updateButton;
    ActivityUpdateReclamationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateReclamationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize your database and DAO
        reclamationDao = ReclamationDatabase.getInstance(this).reclamationDao();

        // Find views using binding
        editObjet = binding.editObjet;
        editContenu = binding.editContenu;
        editDate = binding.editDate;
        editId = binding.editId;
        updateButton = binding.updateButton;

        Intent intent = getIntent();
        String objet = intent.getStringExtra("name");
        String contenu = intent.getStringExtra("desc");
        String date = intent.getStringExtra("time");
        String id = intent.getStringExtra("id");
        binding.editObjet.setText(objet);
        binding.editContenu.setText(contenu);
        binding.editDate.setText(date);
        binding.editId.setText(id);

        // Set click listener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReclamation();
            }
        });
    }

    private void updateReclamation() {
        // Get updated data from UI elements
        String updatedObjet = editObjet.getText().toString();
        String updatedContenu = editContenu.getText().toString();
        String updatedDate = editDate.getText().toString();
        String id = editId.getText().toString();

        // Perform the update using the DAO on a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Retrieve the existing Reclamation object from the database based on the provided data
                reclamation = reclamationDao.getReclamationById(Integer.parseInt(id));

                // Check if reclamation is still null (not found in the database)
                if (reclamation == null) {
                    finish();
                    return;
                }

                // Update the existing Reclamation object
                reclamation.setObjet(updatedObjet);
                reclamation.setContenu(updatedContenu);
                reclamation.setDate(updatedDate);

                // Update the Reclamation object in the database
                reclamationDao.updateReclamation(reclamation);
            }
        }).start();

        // Provide feedback to the user
        Toast.makeText(this, "Reclamation updated successfully", Toast.LENGTH_SHORT).show();

        // Finish the activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        finish();

    }


}
