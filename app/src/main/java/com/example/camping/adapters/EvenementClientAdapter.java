package com.example.camping.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.camping.R;
import com.example.camping.model.Evenement;
import com.example.camping.ui.evenement.client.EvenementListClientActivity;

import java.io.File;
import java.util.List;

public class EvenementClientAdapter extends RecyclerView.Adapter<EvenementClientAdapter.ViewHolder> {

    private List<Evenement> evenements;
    private EvenementListClientActivity evenementListActivity;

    public EvenementClientAdapter(List<Evenement> evenements, EvenementListClientActivity evenementListActivity) {
        this.evenements = evenements;
        this.evenementListActivity = evenementListActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.evenement_item_client, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Evenement evenement = evenements.get(position);

        Glide.with(holder.itemView.getContext())
                .load(evenement.getImagePath())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imageEvenement);

        holder.evenementName.setText(evenement.getNom());
        holder.textViewDebut.setText("Début: " + Evenement.formatDate(evenement.getDebut()));
        holder.textViewFin.setText("Fin: " + Evenement.formatDate(evenement.getFin()));

        // Share Button
        holder.buttonShare.setOnClickListener(view -> shareEvenement(view.getContext(), evenement));
    }

    private void shareEvenement(Context context, Evenement evenement) {
        String shareText = "Check out this event!\n" +
                "Name: " + evenement.getNom() + "\n" +
                "Début: " + evenement.getDebut() + "\n" +
                "Fin: " + evenement.getFin();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // Set the MIME type for text
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Event Details");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        File file = new File(evenement.getImagePath());

        // Attach the image URI to the Intent
        Uri uri = FileProvider.getUriForFile(
                context,
                context.getApplicationContext().getPackageName() + ".provider",
                file
        );

        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(Intent.createChooser(shareIntent, "Share Event Details"));
    }


    @Override
    public int getItemCount() {
        return evenements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final ImageView imageEvenement;
        public final TextView evenementName;
        public final TextView textViewDebut;
        public final TextView textViewFin;
        public final ImageButton buttonShare;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imageEvenement = view.findViewById(R.id.imageEvenement);
            evenementName = view.findViewById(R.id.evenement_name);
            textViewDebut = view.findViewById(R.id.textViewDebut);
            textViewFin = view.findViewById(R.id.textViewFin);
            buttonShare = view.findViewById(R.id.buttonShare);
        }
    }

}
