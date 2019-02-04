package com.hwang.xsighting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hwang.xsighting.models.Sighting;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateSighting extends AppCompatActivity {

    private final String TAG = "Create.Sighting";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FusedLocationProviderClient fusedLocationClient;
    private final int MY_PERMISSIONS_REQUEST_LOCATIONS = 1122;
    private String lastLocation;
    public Sighting sighting = new Sighting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sighting);

        // Get Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
    }


    public void submitSighting(View view) {

        

        Map<String, Object> user = new HashMap<>();
        user.put("author", "Alan");
        user.put("createdTime", "Mathison");
        user.put("description", "Turing");
        user.put("image_url", 1912);
        user.put("location", );
        user.put("userId", );


// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }



    public void getLocation() {

        //Permission Granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {

                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {

                                //Turn the location to a String object
                                Geocoder geocoder = new Geocoder(CreateSighting.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                lastLocation = addresses.get(0).getLocality();
                                // Set the location text
                                TextView locationText = findViewById(R.id.report_location);
                                locationText.setText(lastLocation);

                                Log.i("Sighting.Location", "Got a location " + lastLocation);
                            }
                            else {
                                lastLocation = "Unknown";
                            }
                        }
                    });
        }
        // Permission denied, request permission
        else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //TODO: Customize the alert message

            } else {
                // Request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATIONS);

            }
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, get the location
                    getLocation();

                } else {
                    // Permission denied
                    lastLocation = "Unknown";
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
