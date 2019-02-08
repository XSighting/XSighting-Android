package com.hwang.xsighting;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwang.xsighting.models.Sighting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

// http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
public class AllSightingsAdapter extends RecyclerView.Adapter<AllSightingsAdapter.ViewHolder> {
  private List<Sighting> sightings;

  // Provides a reference to the views for each Project
  public static class ViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public TextView description;
    public TextView location;
    public TextView timeStamp;
    public TextView sightingId;

    public ViewHolder(View v) {
      super(v);
      mView = v;
      description = v.findViewById(R.id.textView_allsightings_description);
      location = v.findViewById(R.id.textview_allsightings_location);
      timeStamp = v.findViewById(R.id.textview_allsightings_time);
      sightingId = v.findViewById(R.id.sightingId);
    }
  }

  // Constructor
  public AllSightingsAdapter(List<Sighting> sightings) {
    this.sightings = sightings;
  }

  // Adds a new project to projects
  // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MyAdapter.java
  public void add(Sighting sighting, String id) {
    sighting.setFirebaseId(id);
    sightings.add(0, sighting);
    notifyItemInserted(0);
  }

  public void remove(Sighting sighting) {
    for (int i = 0; i < sightings.size(); i++) {
      if (sighting.equals(sightings.get(i))){
        sightings.remove(i);
        notifyItemRemoved(i);
      }
    }
  }

  public void setSightings(List<Sighting> sightings) {
    this.sightings = sightings;
    this.notifyDataSetChanged();
  }

  // Create a new view (invoked by the layout manager)
  @Override
  public AllSightingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View v = inflater.inflate(R.layout.recyclerview_allsightings, parent, false);

    // Adds an onClick listener
    // https://stackoverflow.com/questions/13485918/android-onclick-listener-in-a-separate-class
    v.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(final View view) {
                TextView idView = view.findViewById(R.id.sightingId);
                String id = idView.getText().toString();
                Log.i("Sighting Id", id);
                goToSighting(view, id);
              }
            });

    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  // Replaces the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    // Replaces the contents of the view with the project id and title
    Sighting sighting = sightings.get(position);
    Date toDate = sighting.getCreatedTime().toDate();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");
    String stringOfTime = dateFormat.format(toDate);

//     Injects sighting's content into the view
    holder.timeStamp.setText(stringOfTime);
    holder.location.setText(sighting.getLocationName());
    holder.sightingId.setText(sighting.getFirebaseId());
    if (sighting.getDescription().length() > 60){
      holder.description.setText(sighting.getDescription().substring(0, 60) + "...");
    } else {
      holder.description.setText(sighting.getDescription());
    }
  }

  // Returns the size of projects (invoked by the layout manager)
  @Override
  public int getItemCount() {
    return sightings.size();
  }

  // Takes the user to ViewSighting
  // https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
  public void goToSighting(View v, String id) {
    Intent goToViewSighting = new Intent(v.getContext(), ViewSighting.class);

    // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
    goToViewSighting.putExtra("SIGHTING_ID", id);
    v.getContext().startActivity(goToViewSighting);
  }
}
