package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Room.Produits;

import java.util.ArrayList;
import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.MyViewHolder>{

    private Context context;
    private List<Produits> produitsList;

    private AdapterListener adapterListener;

    public ProduitAdapter(Context context, AdapterListener listener){
        this.context = context;
        produitsList=new ArrayList<>();
        this.adapterListener=listener;
    }

    public void addProduit(Produits produits){
        produitsList.add(produits);
        notifyDataSetChanged();
    }

    public void removeProduit(int position){
        produitsList.remove(position);
        notifyDataSetChanged();
    }

    public void clearData(){
        produitsList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produits produits=produitsList.get(position);
        holder.nom.setText(produits.getNom());
        holder.description.setText(produits.getDescription());
        holder.prix.setText(produits.getPrix());


        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.OnUpdate(produits);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    adapterListener.OnDelete(produits.getId(),position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return produitsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nom,description,prix;
        private ImageView update,delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nom=itemView.findViewById(R.id.nom);
            description=itemView.findViewById(R.id.description);
            prix=itemView.findViewById(R.id.prix);
            update=itemView.findViewById(R.id.update);
            delete=itemView.findViewById(R.id.delete);
        }
    }

}
