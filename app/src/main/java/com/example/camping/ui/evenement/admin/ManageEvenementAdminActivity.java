package com.example.camping.ui.evenement.admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.camping.R;
import com.example.camping.database.EvenementDBHelper;
import com.example.camping.model.Evenement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManageEvenementAdminActivity extends AppCompatActivity {

    private TextView topTextView;
    private TextView debutTextView;
    private TextView finTextView;
    private EditText editTextNom;
    private ImageView imageViewEvenement;
    private Uri selectedImageUri;
    private long evenementId;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_evenement);

        initializeViews();

        evenementId = getIntent().getLongExtra("evenement_id", -1);

        if (evenementId != -1) {
            loadExistingEvenement();
        }

        Button buttonManageEvenement = findViewById(R.id.buttonManageEvenement);
        buttonManageEvenement.setOnClickListener(view -> {
            if (evenementId != -1) {
                updateEvenement();
            } else {
                addEvenement();
            }
        });

        imageViewEvenement.setOnClickListener(view -> openImagePicker());

        Button buttonPickDebutDate = findViewById(R.id.buttonPickDebutDate);
        Button buttonPickFinDate = findViewById(R.id.buttonPickFinDate);

        buttonPickDebutDate.setOnClickListener(v -> showDatePickerDialog(debutTextView));
        buttonPickFinDate.setOnClickListener(v -> showDatePickerDialog(finTextView));
    }

    private void initializeViews() {
        topTextView = findViewById(R.id.topTextView);
        debutTextView = findViewById(R.id.textViewDebut);
        finTextView = findViewById(R.id.textViewFin);
        editTextNom = findViewById(R.id.editTextNom);
        imageViewEvenement = findViewById(R.id.imageViewEvenement);
    }

    private void loadExistingEvenement() {
        EvenementDBHelper evenementDBHelper = new EvenementDBHelper(this);
        evenementDBHelper.open();

        Evenement evenement = evenementDBHelper.getById(evenementId);

        evenementDBHelper.close();

        if (evenement != null) {
            topTextView.setText("Manage evenement");
            editTextNom.setText(evenement.getNom());
            debutTextView.setText(formatDate(evenement.getDebut()));
            finTextView.setText(formatDate(evenement.getFin()));

            Glide.with(this)
                    .load(evenement.getImagePath())
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageViewEvenement);
        }
    }

    private String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        }
        return "";
    }

    private void updateEvenement() {
        EvenementDBHelper evenementDBHelper = new EvenementDBHelper(this);
        evenementDBHelper.open();

        Evenement existingEvenement = evenementDBHelper.getById(evenementId);
        evenementDBHelper.close();

        Evenement updatedEvenement = createEvenementFromInput(existingEvenement);
        updatedEvenement.setId(evenementId);

        int updatedRows = updateEvenementInDatabase(updatedEvenement);

        handleUpdateResult(updatedRows);
    }

    private void addEvenement() {
        Evenement newEvenement = createEvenementFromInput(null);

        long insertedId = insertEvenementIntoDatabase(newEvenement);

        handleInsertResult(insertedId);
    }

    private Evenement createEvenementFromInput(@Nullable Evenement oldEvenement) {
        String nom = editTextNom.getText().toString();
        Date debut = parseDate(debutTextView.getText().toString());
        Date fin = parseDate(finTextView.getText().toString());

        Evenement evenement = new Evenement(nom, debut, fin, selectedImageUri != null ? selectedImageUri.getPath() : "");

        if (oldEvenement != null) {
            evenement.setImagePath(oldEvenement.getImagePath());
        }

        return evenement;
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleUpdateResult(int updatedRows) {
        if (updatedRows != -1) {
            showToast("Evenement updated successfully");
            finish();
        } else {
            showToast("Failed to update evenement");
        }
    }

    private void handleInsertResult(long insertedId) {
        if (insertedId != -1) {
            showToast("Evenement added successfully");
            finish();
        } else {
            showToast("Failed to add evenement");
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private int updateEvenementInDatabase(Evenement updatedEvenement) {
        EvenementDBHelper evenementDBHelper = new EvenementDBHelper(this);
        evenementDBHelper.open();

        int updatedRows = evenementDBHelper.update(updatedEvenement);

        evenementDBHelper.close();
        return updatedRows;
    }

    private long insertEvenementIntoDatabase(Evenement newEvenement) {
        EvenementDBHelper evenementDBHelper = new EvenementDBHelper(this);
        evenementDBHelper.open();

        long insertedId = evenementDBHelper.add(newEvenement);

        evenementDBHelper.close();
        return insertedId;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            loadImageIntoImageView(data.getData());
            selectedImageUri = saveImageToInternalStorage(data.getData());
        }
    }

    private void loadImageIntoImageView(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .into(imageViewEvenement);
    }

    private Uri saveImageToInternalStorage(Uri selectedImageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

            if (inputStream != null) {
                File internalStorageDir = getFilesDir();
                String fileName = "image_" + System.currentTimeMillis() + ".jpg";
                File file = new File(internalStorageDir, fileName);

                OutputStream outputStream = Files.newOutputStream(file.toPath());

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                inputStream.close();
                outputStream.close();

                return Uri.fromFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void showDatePickerDialog(TextView textView) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextViewDate(textView, calendar);
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateTextViewDate(TextView textView, Calendar calendar) {
        if (textView != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(calendar.getTime());
            textView.setText(formattedDate);
        }
    }
}
