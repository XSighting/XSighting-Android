package com.hwang.xsighting;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hwang.xsighting.models.Sighting;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class AllSightingsAdapter extends RecyclerView.Adapter<AllSightingsAdapter.ViewHolder> {
  private List<Sighting> sightings;

  // Provides a reference to the views for each Project
  public static class ViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public TextView description;
    public TextView location;
    public TextView timeStamp;

    public ViewHolder(View v) {
      super(v);
      mView = v;
      description = v.findViewById(R.id.textView_allsightings_description);
      location = v.findViewById(R.id.textview_allsightings_location);
      timeStamp = v.findViewById(R.id.textview_allsightings_time);
    }
  }

  // Constructor
  public AllSightingsAdapter(List<Sighting> sightings) {
    this.sightings = sightings;
  }

  // Adds a new project to projects
  // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MyAdapter.java
  public void add(Sighting sighting) {
    sightings.add(sighting);
    notifyItemInserted(sightings.size() - 1);
  }

  public void setSightings(List<Sighting> sightings) {
    this.sightings = sightings;
    this.notifyDataSetChanged();
  }

  // Create a new view (invoked by the layout manager)
  @Override
  public AllSightingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // create a new view
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View v = inflater.inflate(R.layout.recyclerview_allsightings, parent, false);

    // set the view's size, margins, padding and layout parameters
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  // Replaces the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    // Replaces the contents of the view with the project id and title
    Date toDate = sightings.get(position).getCreatedTime().toDate();
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
    String stringOfTime  = dateFormat.format(toDate);
    holder.location.setText(sightings.get(position).getLocationName());
    holder.timeStamp.setText(stringOfTime);
    if (sightings.get(position).getDescription().length() > 50){
      holder.description.setText(sightings.get(position).getDescription().substring(0, 50));
    }else {
      holder.description.setText(sightings.get(position).getDescription());
    }
  }

  // Returns the size of projects (invoked by the layout manager)
  @Override
  public int getItemCount() {
    return sightings.size();
  }

  // Takes the user to the ProjectWithTasks activity
  // https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
//  public void goToProject(View v, String id, String title) {
//    Intent goToProjectWithTasksIntent = new Intent(v.getContext(), TaskList.class);

    // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
//    goToProjectWithTasksIntent.putExtra("PROJECT_ID", id);
////    goToProjectWithTasksIntent.putExtra("PROJECT_TITLE", title);
////    v.getContext().startActivity(goToProjectWithTasksIntent);
////  }
}