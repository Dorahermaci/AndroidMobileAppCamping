package com.example.myapplication;

import com.example.myapplication.Room.Produits;

public interface AdapterListener {

    void OnUpdate(Produits produits);
    void OnDelete(int id, int pos);
}
