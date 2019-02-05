package com.hwang.xsighting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import com.google.firebase.firestore.QuerySnapshot;
import com.hwang.xsighting.models.Sighting;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class AllSightings extends AppCompatActivity {

  private FirebaseFirestore db = FirebaseFirestore.getInstance();

  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;

  private AllSightingsAdapter adapter;
  private final String TAG = "AllSighting";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_sightings);
    updateRecyclerView();

  }

  private void updateRecyclerView() {
    recyclerView = (RecyclerView) findViewById(R.id.recyclerview_allsightings);
    recyclerView.setHasFixedSize(true);

    // Creates a layout manager and assigns it to the recycler view
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    // Specifies which adapter the recycler view should use
    adapter = new AllSightingsAdapter(new ArrayList<Sighting>());
    recyclerView.setAdapter(adapter);

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("sighting")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
              @Override
              public void onEvent(@Nullable QuerySnapshot snapshots,
                                  @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                  Log.w(TAG, "listen:error", e);
                  return;
                }

                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                  switch (dc.getType()) {
                    case ADDED:
                      Log.d(TAG, "New project: " + dc.getDocument().getData());
                      adapter.add(dc.getDocument().toObject(Sighting.class));
                      break;
                    case MODIFIED:
                      Log.d(TAG, "Modified project: " + dc.getDocument().getData());
                      //TODO: Update the project
                      break;
                    case REMOVED:
                      Log.d(TAG, "Removed project: " + dc.getDocument().getData());
                      //TODO: Remove the project
                      break;
                  }
                }

              }
            });
  }
}