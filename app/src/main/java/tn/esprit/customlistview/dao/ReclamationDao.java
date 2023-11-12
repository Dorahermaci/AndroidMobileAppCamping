package tn.esprit.customlistview.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tn.esprit.customlistview.entities.Reclamation;

@Dao
public interface ReclamationDao {
    @Insert
    void insertReclamation(Reclamation reclamation);

    @Query("SELECT * FROM Reclamation")
    List<Reclamation> getAllReclamations();

    @Update
    void updateReclamation(Reclamation reclamation);

    @Query("SELECT * FROM Reclamation WHERE id = :reclamationId")
    Reclamation getReclamationById(int reclamationId);


    @Query("DELETE FROM reclamation WHERE id = :reclamationId")
    void deleteReclamationById(int reclamationId);


}

