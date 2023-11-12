package tn.esprit.customlistview.appdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tn.esprit.customlistview.dao.ReclamationDao;
import tn.esprit.customlistview.entities.Reclamation;

@Database(entities = {Reclamation.class}, version = 1)
public abstract class ReclamationDatabase extends RoomDatabase {
    private static ReclamationDatabase instance;

    public abstract ReclamationDao reclamationDao();

    public static synchronized ReclamationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ReclamationDatabase.class, "reclamation_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
