package com.hwang.xsighting;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hwang.xsighting.models.Sighting;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllSightingsAdapter extends FirestoreRecyclerAdapter<Sighting, AllSightingsAdapter.SightingHolder> {

  public AllSightingsAdapter(@NonNull FirestoreRecyclerOptions<Sighting> options) {
    super(options);
  }

  @Override
  protected void onBindViewHolder(@NonNull SightingHolder holder, int position, @NonNull Sighting model) {
    holder.textViewDescription.setText(model.getDescription());

  }

  @NonNull
  @Override
  public SightingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_allsightings,
            parent, false);
    return new SightingHolder(v);
  }

  class SightingHolder extends RecyclerView.ViewHolder {
    TextView textViewDescription;

    public SightingHolder(View itemView) {
      super(itemView);
      textViewDescription = itemView.findViewById(R.id.textView_allsightings_description);

    }
  }
}
