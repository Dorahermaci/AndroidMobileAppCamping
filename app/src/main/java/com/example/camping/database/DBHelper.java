package com.example.camping.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.camping.model.Constants;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "evenement.db";
    private static final int DATABASE_VERSION = 9;

    private static final String SQL_CREATE_EVENEMENT_TABLE =
            "CREATE TABLE " + Constants.EVENEMENT_TABLE_NAME + " (" +
                    Constants.EVENEMENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Constants.EVENEMENT_COLUMN_NOM + " TEXT NOT NULL," +
                    Constants.EVENEMENT_COLUMN_DEBUT + " DATE NOT NULL," +
                    Constants.EVENEMENT_COLUMN_FIN + " DATE NOT NULL," +
                    Constants.EVENEMENT_COLUMN_IMAGE + " TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EVENEMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.EVENEMENT_TABLE_NAME);
        onCreate(db);
    }
}
