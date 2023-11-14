package com.example.camping.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.camping.model.Constants;
import com.example.camping.model.Evenement;
import java.util.ArrayList;
import java.util.List;

public class EvenementDBHelper {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public EvenementDBHelper(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Get all evenements
    public List<Evenement> getAll() {
        List<Evenement> evenements = new ArrayList<>();

        Cursor cursor = database.query(
                Constants.EVENEMENT_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Evenement evenement = cursorToEvenement(cursor);
                evenements.add(evenement);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return evenements;
    }

    // Get a evenement by ID
    public Evenement getById(long evenementId) {
        Cursor cursor = database.query(
                Constants.EVENEMENT_TABLE_NAME,
                null,
                Constants.EVENEMENT_COLUMN_ID + " = ?",
                new String[]{String.valueOf(evenementId)},
                null,
                null,
                null
        );

        Evenement evenement = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                evenement = cursorToEvenement(cursor);
            }
            cursor.close();
        }

        return evenement;
    }

    // Insert a new evenement
    public long add(Evenement evenement) {
        ContentValues values = new ContentValues();
        values.put(Constants.EVENEMENT_COLUMN_NOM, evenement.getNom());
        values.put(Constants.EVENEMENT_COLUMN_DEBUT, evenement.getDebut().toString()); // assuming Date is converted to String
        values.put(Constants.EVENEMENT_COLUMN_FIN, evenement.getFin().toString()); // assuming Date is converted to String
        values.put(Constants.EVENEMENT_COLUMN_IMAGE, evenement.getImagePath());

        return database.insert(Constants.EVENEMENT_TABLE_NAME, null, values);
    }

    // Update a evenement
    public int update(Evenement evenement) {
        ContentValues values = new ContentValues();
        values.put(Constants.EVENEMENT_COLUMN_NOM, evenement.getNom());
        values.put(Constants.EVENEMENT_COLUMN_DEBUT, evenement.getDebut().toString()); // assuming Date is converted to String
        values.put(Constants.EVENEMENT_COLUMN_FIN, evenement.getFin().toString()); // assuming Date is converted to String
        values.put(Constants.EVENEMENT_COLUMN_IMAGE, evenement.getImagePath());

        return database.update(
                Constants.EVENEMENT_TABLE_NAME,
                values,
                Constants.EVENEMENT_COLUMN_ID + " = ?",
                new String[]{String.valueOf(evenement.getId())}
        );
    }

    // Delete a evenement
    public int delete(long evenementId) {
        return database.delete(
                Constants.EVENEMENT_TABLE_NAME,
                Constants.EVENEMENT_COLUMN_ID + " = ?",
                new String[]{String.valueOf(evenementId)}
        );
    }

    // Helper method to convert a cursor to a Evenement object
    private Evenement cursorToEvenement(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(Constants.EVENEMENT_COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Constants.EVENEMENT_COLUMN_NOM));
        String debutString = cursor.getString(cursor.getColumnIndexOrThrow(Constants.EVENEMENT_COLUMN_DEBUT));
        String finString = cursor.getString(cursor.getColumnIndexOrThrow(Constants.EVENEMENT_COLUMN_FIN));
        String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(Constants.EVENEMENT_COLUMN_IMAGE));

        // Convert String to Date, you need to implement a proper conversion method
        // For simplicity, let's assume you have a method parseDate(String dateString) in Evenement class
        Evenement evenement = new Evenement(id, name, Evenement.parseDate(debutString), Evenement.parseDate(finString), imagePath);

        return evenement;
    }
}
