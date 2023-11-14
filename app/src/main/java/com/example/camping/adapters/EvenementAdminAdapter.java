package com.example.camping.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.camping.R;
import com.example.camping.database.EvenementDBHelper;
import com.example.camping.model.Evenement;
import com.example.camping.ui.evenement.admin.EvenementListAdminActivity;
import com.example.camping.ui.evenement.admin.ManageEvenementAdminActivity;

import java.util.List;

public class EvenementAdminAdapter extends RecyclerView.Adapter<EvenementAdminAdapter.ViewHolder> {

    private List<Evenement> evenements;
    private EvenementListAdminActivity evenementListActivity;

    public EvenementAdminAdapter(List<Evenement> evenements, EvenementListAdminActivity evenementListActivity) {
        this.evenements = evenements;
        this.evenementListActivity = evenementListActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.evenement_item_admin, parent, false);
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


        // Update Button
        holder.buttonUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ManageEvenementAdminActivity.class);
            intent.putExtra("evenement_id", evenement.getId());
            view.getContext().startActivity(intent);
        });

        // Delete Button
        holder.buttonDelete.setOnClickListener(view -> {
            showDeleteConfirmationDialog(view.getContext(), evenement.getId());
        });
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
        public final ImageButton buttonUpdate;
        public final ImageButton buttonDelete;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imageEvenement = view.findViewById(R.id.imageEvenement);
            evenementName = view.findViewById(R.id.evenement_name);
            textViewDebut = view.findViewById(R.id.textViewDebut);
            textViewFin = view.findViewById(R.id.textViewFin);
            buttonUpdate = view.findViewById(R.id.buttonUpdate);
            buttonDelete = view.findViewById(R.id.buttonDelete);
        }
    }

    private void showDeleteConfirmationDialog(Context context, long evenementId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this evenement?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            deleteEvenement(context, evenementId);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }

    private void deleteEvenement(Context context, long evenementId) {
        EvenementDBHelper evenementDBHelper = new EvenementDBHelper(context);
        evenementDBHelper.open();
        int deleted = evenementDBHelper.delete(evenementId);
        evenementDBHelper.close();

        if (deleted != -1) {
            Toast.makeText(context, "Evenement deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to delete evenement", Toast.LENGTH_SHORT).show();
        }

        evenementListActivity.loadData();
    }
}
