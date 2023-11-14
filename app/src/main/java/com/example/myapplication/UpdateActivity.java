package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Room.ProduitDao;
import com.example.myapplication.Room.Produits;
import com.example.myapplication.Room.ProduitsDatabase;

public class UpdateActivity extends AppCompatActivity {

    private EditText nomEd,descriptionEd,prixEd;
    private Button update;
    private Produits produits;

    private ProduitsDatabase produitsDatabase;
    private ProduitDao produitDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        produitsDatabase=ProduitsDatabase.getInstance(this);
        produitDao=produitsDatabase.getDao();


        nomEd=findViewById(R.id.nom);
        descriptionEd=findViewById(R.id.description);
        prixEd=findViewById(R.id.prix);
        update=findViewById(R.id.update);

        produits=(Produits) getIntent().getSerializableExtra("model");

        nomEd.setText(produits.getNom());
        descriptionEd.setText(produits.getDescription());
        prixEd.setText(produits.getPrix());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produits produitModel=new Produits(produits.getId(),nomEd.getText().toString(),descriptionEd.getText().toString(),prixEd.getText().toString());
                produitDao.update(produitModel);
                finish();

            }
        });
    }
}