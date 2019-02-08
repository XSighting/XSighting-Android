package com.hwang.xsighting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hwang.xsighting.models.Sighting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ViewSighting extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String sightingId;
    private final String TAG = "SightingDetail";
    private Sighting sightingToDisplay;
    private FirebaseUser user;
    private ImageView sightingImageDetailView;
    private Integer upVoteCount;
    private Integer downVoteCount;
    private HashMap<String, Boolean> voteTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sighting);

        sightingId = getIntent().getStringExtra("SIGHTING_ID");
        user = FirebaseAuth.getInstance().getCurrentUser();
        sightingImageDetailView = findViewById(R.id.sightingImageDetailView);

        getContent();
        setNavigation();
    }

    public void setNavigation(){

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(homeIntent);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.navigation_add_sighting:
                                Intent addSighting = new Intent(getBaseContext(), CreateSighting.class);
                                startActivity(addSighting);
                                overridePendingTransition(0, 0);
                                break;
                        }
                        return true;
                    }
                });
    }

    public void getContent() {
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

                        // check to see if the logged in user created the post
                        Button deleteButton = findViewById(R.id.delete_sighting);

                        if (user.getUid().equals(sightingToDisplay.getAuthorId())) {
                            // Allow creator to delete the post
                            deleteButton.setVisibility(View.VISIBLE);
                        } else {
                            deleteButton.setVisibility(View.INVISIBLE);
                        }

                        // Set Text fields with the sighting
                        TextView date = findViewById(R.id.postDate);
                        TextView userName = findViewById(R.id.postUser);
                        TextView description = findViewById(R.id.postDescription);
                        TextView title = findViewById(R.id.postTitle);
                        getImageFromFireBase();
                        Button upVote = findViewById(R.id.button_up_vote);
                        Button downVote = findViewById(R.id.button_down_vote);

                        // Make the Date String Pretty
                        Date toDate = sightingToDisplay.getCreatedTime().toDate();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy @ HH:mm");
                        String stringOfTime = dateFormat.format(toDate);
                        upVoteCount = sightingToDisplay.getUpVote();
                        downVoteCount = sightingToDisplay.getDownVote();
                        String displayUpVote = Integer.toString(upVoteCount);
                        String displayDownVote = Integer.toString(downVoteCount);

                        userName.setText(sightingToDisplay.getAuthorUsername());
                        description.setText(sightingToDisplay.getDescription());
                        title.setText("Sighted in " + sightingToDisplay.getLocationName());
                        date.setText(stringOfTime);
                        upVote.setText(displayUpVote);
                        if(downVoteCount == 0) {
                            downVote.setText(displayDownVote);
                        }else{
                            downVote.setText("-" + displayDownVote);
                        }

                    } else {
                        Log.d(TAG, "No such document");

                        Context context = getApplicationContext();
                        CharSequence text = "Something went wrong! Try Again.";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        finish();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());

                    Context context = getApplicationContext();
                    CharSequence text = "Something went wrong! Try Again";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            }
        });
    }

    public void onClickUpVote(View view){

        DocumentReference docRef = db.collection("sighting").document(sightingId);


        if(sightingToDisplay.getVoted().get(user.getUid())){
            docRef.update("upVote", upVoteCount - 1);
            docRef.update("voted", sightingToDisplay.getVoted().put(user.getUid(), false));
            addRealtimeUpdate();
        }else {
            voteTracker.put(user.getUid(), true);
            docRef.update("upVote", upVoteCount + 1);
            docRef.update("voted", sightingToDisplay.getVoted().put(user.getUid(), true));
            addRealtimeUpdate();
        }
    }

    public void onClickDownVote(View view) {

        DocumentReference docRef = db.collection("sighting").document(sightingId);


        if(sightingToDisplay.getVoted().get(user.getUid()) != null && sightingToDisplay.getVoted().get(user.getUid())){
            voteTracker.put(user.getUid(), false);
            docRef.update("downVote", upVoteCount - 1);
            docRef.update("voted", sightingToDisplay.setVoted(user.getUid(), false));
            addRealtimeUpdate();
        }else {
            docRef.update("downVote", upVoteCount + 1);
            docRef.update("voted", sightingToDisplay.getVoted().put(user.getUid(), true));
            addRealtimeUpdate();
        }
    }

    // Gets the sighting's image from FireBase (if there is an image to retrieve)
    // https://firebase.google.com/docs/storage/android/download-files
    public void getImageFromFireBase() {
        if (sightingToDisplay.getImageUrl() != null) {
            // Create a storage reference from our app
            FirebaseStorage storage = FirebaseStorage.getInstance();

            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            // Gets the image from Firebase
            StorageReference pathReference = storageRef.child(sightingToDisplay.getImageUrl());

            // ImageView in your Activity
            ImageView imageView = findViewById(R.id.sightingImageDetailView);

            // Download directly from StorageReference using Glide (See MyAppGlideModule for Loader registration)
            GlideApp.with(this /* context */)
                    .load(pathReference)
                    .into(imageView);
        }
    }

    public void onDeleteButtonClicked(View v){

        db.collection("sighting").document(sightingId).delete();

        // Notify the user that delete was successful
        Context context = getApplicationContext();
        CharSequence text = "Sighting was deleted";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // Redirect to Main Activity
        finish();
    }

    private void addRealtimeUpdate() {
        DocumentReference contactListener = db.collection("sighting").document(sightingId);
        contactListener.addSnapshotListener(new EventListener< DocumentSnapshot >() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("ERROR", e.getMessage());
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    getContent();
                }
            }
        });
    }
}
