package tn.esprit.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tn.esprit.customlistview.entities.Reclamation;

public class ReclamationListAdapter extends ArrayAdapter<Reclamation> {
    public ReclamationListAdapter(@NonNull Context context, ArrayList<Reclamation> dataArrayList) {
        super(context, R.layout.list_reclamation, dataArrayList);
    }

    @NonNull
    @Override
    // el view heya el element taa liste wel parent heya el Listeview
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Reclamation reclamation = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_reclamation, parent, false);
        }

        //nepwinti 3la textView
        TextView listName = view.findViewById(R.id.listName);
        TextView listTime = view.findViewById(R.id.listTime);

        // n3abii les text view
        listName.setText(reclamation.getObjet());
        listTime.setText(reclamation.getDate());

        return view;
    }
}