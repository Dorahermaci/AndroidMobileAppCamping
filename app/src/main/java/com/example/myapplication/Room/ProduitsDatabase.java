package com.example.myapplication.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Produits.class},version = 3)
public abstract class ProduitsDatabase extends RoomDatabase {

    public abstract ProduitDao getDao();

    public static ProduitsDatabase INSTANCE;

    public static ProduitsDatabase getInstance(Context context){
        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context,ProduitsDatabase.class,"ProduitsDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
