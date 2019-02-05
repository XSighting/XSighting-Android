package com.hwang.xsighting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;

import com.hwang.xsighting.models.Sighting;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.Toast;



public class AllSightings extends AppCompatActivity {

  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private CollectionReference collectionReference = db.collection("sighting");

  private AllSightingsAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_sightings);

    setUpRecyclerView();
  }

  private void setUpRecyclerView() {
    Query query = collectionReference.orderBy("description", Query.Direction.DESCENDING);

    FirestoreRecyclerOptions<Sighting> options = new FirestoreRecyclerOptions.Builder<Sighting>()
            .setQuery(query, Sighting.class)
            .build();

    adapter = new AllSightingsAdapter(options);

    RecyclerView recyclerView = findViewById(R.id.recyclerview_allsightings);
//    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);

    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.deleteItem(viewHolder.getAdapterPosition());
      }
    }).attachToRecyclerView(recyclerView);

    adapter.setOnItemClickListener(new AllSightingsAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
        Sighting sighting = documentSnapshot.toObject(Sighting.class);
        String id = documentSnapshot.getId();
        String path = documentSnapshot.getReference().getPath();
        Toast.makeText(AllSightings.this,
                "Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    adapter.startListening();
  }

  @Override
  protected void onStop() {
    super.onStop();
    adapter.stopListening();
  }
}