package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Room.ProduitDao;
import com.example.myapplication.Room.Produits;
import com.example.myapplication.Room.ProduitsDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterListener{

    EditText nomEd,descriptionEd,prixEd;
    Button insertBtn;
    RecyclerView myrecycler;
    private ProduitsDatabase produitsDatabase;
    private ProduitDao produitDao;
    private ProduitAdapter produitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produitsDatabase = ProduitsDatabase.getInstance(this);
        produitDao = produitsDatabase.getDao();


        nomEd=findViewById(R.id.nom);
        descriptionEd=findViewById(R.id.description);
        prixEd=findViewById(R.id.prix);
        insertBtn=findViewById(R.id.insert);
        myrecycler=findViewById(R.id.produitsRecycler);


        produitAdapter=new ProduitAdapter(this, this);
        myrecycler.setAdapter(produitAdapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom=nomEd.getText().toString();
                String description=descriptionEd.getText().toString();
                String prix=prixEd.getText().toString();

                Produits produits=new Produits(0,nom,description,prix);
                produitAdapter.addProduit(produits);
                produitDao.insert(produits);
                nomEd.setText("");
                descriptionEd.setText("");
                prixEd.setText("");
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();

            }
        });
        }

        private void fetchData(){
            produitAdapter.clearData();
            List<Produits> produitsList =produitDao.getAllProduits();
            for (int i=0;i<produitsList.size();i++){
                Produits produits=produitsList.get(i);
                produitAdapter.addProduit(produits);
            }
        }

    @Override
    public void OnUpdate(Produits produits) {
        Intent intent=new Intent(this,UpdateActivity.class);
        intent.putExtra("model",produits);
        startActivity(intent);

    }

    @Override
    public void OnDelete(int id, int pos) {

        produitDao.delete(id);
        produitAdapter.removeProduit(pos);
    }

    @Override
    protected void onResume(){
        super.onResume();
        fetchData();

    }
}
