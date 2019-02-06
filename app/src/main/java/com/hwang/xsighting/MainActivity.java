package com.hwang.xsighting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hwang.xsighting.models.Sighting;
import com.hwang.xsighting.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private static FirebaseUser user;
  private static final int RC_SIGN_IN = 482;
  private static final String TAG = "MainActivity";
  private AllSightingsAdapter adapter;
  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {

      // User is signed in
      Task<GetTokenResult> token = user.getIdToken(false);

      setNavigation();
      Log.i(TAG, user.toString());
    } else {

      // No user signed in, direct them to Login
      Log.i(TAG, "About to launch sign in");

      // Choose authentication providers
      List<AuthUI.IdpConfig> providers = Arrays.asList(
              new AuthUI.IdpConfig.EmailBuilder().build());

      // Create and launch sign-in intent
      startActivityForResult(
              AuthUI.getInstance()
                      .createSignInIntentBuilder()
                      .setAvailableProviders(providers)
                      .build(),
              RC_SIGN_IN);

      Log.i(TAG, "Sign up intent Sent");
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      IdpResponse response = IdpResponse.fromResultIntent(data);

      if (resultCode == RESULT_OK) {

        // Successfully signed in
        createNewUserIfUserDoesNotExist(FirebaseAuth.getInstance().getUid());
        updateRecyclerView();
        setNavigation();

      } else {
        // Sign in failed
      }
    }
  }

  // Creates new user document in Firebase if current user doesn't exist
  public void createNewUserIfUserDoesNotExist(final String loggedInUserId) {

    user = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(loggedInUserId);
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document.exists()) {
            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
          } else {
            Log.d(TAG, "No such document, creating user");

            // Saves user to Firebase
            db.collection("users").document(loggedInUserId)
                    .set(new User())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                      }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                      }
                    });
          }
        } else {
          Log.d(TAG, "get failed with ", task.getException());
        }
      }
    });
  }
  
  private void updateRecyclerView() {

    recyclerView = findViewById(R.id.recyclerview_allsightings);
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
                      Log.d(TAG, "New sighting: " + dc.getDocument().getData());
                      adapter.add(dc.getDocument().toObject(Sighting.class), dc.getDocument().getId());
                      break;
                    case MODIFIED:
                      Log.d(TAG, "Modified sighting: " + dc.getDocument().getData());
                      break;
                    case REMOVED:
                      Log.d(TAG, "Removed sighting: " + dc.getDocument().getData());
                      break;
                  }
                }

              }
            });
  }

  public void setNavigation(){

    setContentView(R.layout.activity_main);
    BottomNavigationView bottomNavigationView = (BottomNavigationView)
            findViewById(R.id.navigation);
    bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
    updateRecyclerView();
    bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                  case R.id.navigation_home:
                    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                  case R.id.navigation_add_sighting:
                    Intent addSighting = new Intent(MainActivity.this, CreateSighting.class);
                    startActivity(addSighting);
                }
                return true;
              }
            });
  }
}
