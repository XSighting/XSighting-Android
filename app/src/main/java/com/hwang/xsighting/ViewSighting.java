package com.hwang.xsighting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hwang.xsighting.models.Sighting;

public class ViewSighting extends AppCompatActivity {

    //TODO: Check that as 'final' it does not cause issues
    private final String sightingId = getIntent().getStringExtra("SIGHTING_ID");
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "SightingDetail";
    private Sighting sightingToDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sighting);


        //Get the unique sighting and render the info on the page
        DocumentReference docRef = db.collection("sighting").document(sightingId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        sightingToDisplay = document.toObject(Sighting.class);

                        // Set Fields and other things
                    } else {
                        Log.d(TAG, "No such document");

                        //TODO: Redirect to the main page with a failure toast
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    //TODO: Redirect to the main page with a failure toast
                }
            }
        });
    }
}
