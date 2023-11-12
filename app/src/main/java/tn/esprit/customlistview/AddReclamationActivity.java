package tn.esprit.customlistview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tn.esprit.customlistview.appdatabase.ReclamationDatabase;
import tn.esprit.customlistview.dao.ReclamationDao;
import tn.esprit.customlistview.entities.Reclamation;

public class AddReclamationActivity extends AppCompatActivity {
    private ReclamationDao reclamationDao;

    private void initializeDatabase() {
        reclamationDao = ReclamationDatabase.getInstance(this).reclamationDao();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reclamation);
        initializeDatabase();
        // Find views
        EditText editObjet = findViewById(R.id.editObjet);
        EditText editContenu = findViewById(R.id.editContenu);
        TextView editDate = findViewById(R.id.editDate);
        Button addButton = findViewById(R.id.addButton);

        // Set current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        editDate.setText("Date: " + currentDate);

        // Handle add button click
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objet = editObjet.getText().toString();
                String contenu = editContenu.getText().toString();
                String date = currentDate;

                // Create a new Reclamation object
                Reclamation newReclamation = new Reclamation(objet, contenu, date);

                // Insert the new Reclamation into your Room database
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        reclamationDao.insertReclamation(newReclamation);
                    }
                }).start();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }
}
