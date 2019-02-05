package com.hwang.xsighting;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.hwang.xsighting.models.Sighting;


import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllSightingsAdapter extends FirestoreRecyclerAdapter<Sighting, AllSightingsAdapter.SightingHolder> {
  private OnItemClickListener listener;

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

  public void deleteItem(int position) {
    getSnapshots().getSnapshot(position).getReference().delete();
  }

  class SightingHolder extends RecyclerView.ViewHolder {
    TextView textViewDescription;

    public SightingHolder(View itemView) {
      super(itemView);
      textViewDescription = itemView.findViewById(R.id.textView_allsightings_description);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int position = getAdapterPosition();
          if (position != RecyclerView.NO_POSITION && listener != null) {
            listener.onItemClick(getSnapshots().getSnapshot(position), position);
          }
        }
      });
    }


  }
  public interface OnItemClickListener {
    void onItemClick(DocumentSnapshot documentSnapshot, int position);
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }
}
