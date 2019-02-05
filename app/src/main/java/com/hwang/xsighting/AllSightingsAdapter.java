package com.hwang.xsighting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwang.xsighting.models.Sighting;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AllSightingsAdapter extends RecyclerView.Adapter<AllSightingsAdapter.AllSightingsViewHolder> {

  private Context myContext;
  private List<Sighting> sightingList;


  public AllSightingsAdapter(Context myContext, List<Sighting> sightingList){
    this.myContext = myContext;
    this.sightingList = sightingList;
  }

  @Override
  public AllSightingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    View view = LayoutInflater.from(myContext).inflate(R.layout.recyclerview_allsightings, parent, false);
    return new AllSightingsViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final AllSightingsViewHolder holder, int position){
    Sighting t = sightingList.get(position);
    holder.allSightingsDescription.setText(t.getDescription());
  }

  @Override
  public int getItemCount(){
    return sightingList.size();
  }

  class AllSightingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView allSightingsDescription;

    public AllSightingsViewHolder(View itemView){
      super(itemView);
      allSightingsDescription = itemView.findViewById(R.id.textView_allsightings_description);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      Sighting sighting = sightingList.get(getAdapterPosition());

      Intent intent = new Intent(myContext, AllSightings.class);
      intent.putExtra("description", sighting.getDescription());



      myContext.startActivity(intent);
    }
  }
}
