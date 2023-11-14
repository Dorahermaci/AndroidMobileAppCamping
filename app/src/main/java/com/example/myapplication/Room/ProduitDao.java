package com.example.myapplication.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProduitDao {
    @Insert
    void insert(Produits produits);

    @Update
    void  update(Produits produits);

    @Query("delete from Produits where id+:id")
    void delete(int id);

    @Query("Select * from Produits")
    List<Produits> getAllProduits();


}
