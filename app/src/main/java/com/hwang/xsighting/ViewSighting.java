package com.hwang.xsighting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

                        // Set Text fields with the sighting
                        TextView date = findViewById(R.id.postDate);
                        TextView location = findViewById(R.id.postLocation);
                        TextView user = findViewById(R.id.postUser);
                        TextView description = findViewById(R.id.postDescription);

                        date.setText(sightingToDisplay.getTimestamp().toString());
                        location.setText(sightingToDisplay.getLocation());
                        user.setText(sightingToDisplay.getAuthorUsername());
                        description.setText(sightingToDisplay.getDescription());


                    } else {
                        Log.d(TAG, "No such document");

                        //TODO: Redirect to the main page with a failure toast

                        Context context = getApplicationContext();
                        CharSequence text = "Something went wrong! Try Again.";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        finish();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    //TODO: Redirect to the main page with a failure toast

                    Context context = getApplicationContext();
                    CharSequence text = "Something went wrong! Try Again";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            }
        });
    }

}
