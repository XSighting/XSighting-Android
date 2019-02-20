package com.hwang.xsighting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hwang.xsighting.models.Sighting;
import com.hwang.xsighting.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//TODO: used for redirection
//  private static final int VIEW_SIGHTING_ACTIVITY_REQUEST_CODE = 1331;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


      loadFragment(new SightingsFragment());

      BottomNavigationView bottomNavigationView = (BottomNavigationView)
              findViewById(R.id.navigation);
      bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationSelectedListener);

  }

  //TODO: if we want to use this as a receiver for redirection, then we need to edit the onactivity for createsighting to onactivityresult
//  @Override
//  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//
//    // Check that it is the SecondActivity with an OK result
//    if (requestCode == VIEW_SIGHTING_ACTIVITY_REQUEST_CODE) {
//      if (resultCode == RESULT_OK) {
//        String intentFragment = data.getStringExtra("fragmentToLoad");
//        switch (intentFragment) {
//          case "FRAGMENT_SIGHTINGS":
//            loadFragment(new SightingsFragment());
//            break;
//          case "FRAGMENT_CREATE_SIGHTING":
//            loadFragment(new CreateSightingFragment());
//            break;
//        }
//
//      }
//    }
//  }

   private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                  case R.id.navigation_home:
                    // Possibly add in toolbar.setTitle("Home")
                    fragment = new SightingsFragment();
                    loadFragment(fragment);
                    return true;
                  case R.id.navigation_add_sighting:
                  // Possibly add in toolbar.setTitle("Add Sighting");
                   fragment = new CreateSightingFragment();
                   loadFragment(fragment);
                    return true;
                }
                return false;
              }
   };

   private void loadFragment(Fragment fragment){
     FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
     transaction.replace(R.id.frame_container, fragment);
     transaction.addToBackStack(null);
     transaction.commit();
   }



}
