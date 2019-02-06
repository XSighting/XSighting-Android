package com.hwang.xsighting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.hwang.xsighting.models.Sighting;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateSighting extends AppCompatActivity {
    private final String TAG = "Create.Sighting";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FusedLocationProviderClient fusedLocationClient;
    private final int MY_PERMISSIONS_REQUEST_LOCATIONS = 1122;
    private String lastLocation;
    private GeoPoint geoPointLocation;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sighting);

        // Get Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        // Set the username
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void submitSighting(View view) {
        Date dateData = new Date();
        Timestamp timestamp = new Timestamp(dateData);
        EditText descriptionEditText = findViewById(R.id.report_description);
        String description = descriptionEditText.getText().toString();
        Sighting sighting = new Sighting(user.getUid(), user.getDisplayName(), timestamp, lastLocation, geoPointLocation, "placeholder", description );

        // Add a new document with a generated ID
        db.collection("sighting")
                .add(sighting)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                        // https://developer.android.com/guide/topics/ui/notifiers/toasts
                        // Toast success message
                        Context context = getApplicationContext();
                        CharSequence text = "Your sighting was saved.";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        // Redirect
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        // Toast Failure message
                        Context context = getApplicationContext();
                        CharSequence text = "Something went wrong, please try again.";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                });
    }

    // Gets user's current location
    public void getLocation() {

        //Permission Granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
                                locationText.setText("Your location: " + lastLocation);

                                // Set the GeoPoint so location can be saved to Cloud Firestore Database
                                geoPointLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                                Log.i("Sighting.Location", "Got a location " + lastLocation);
                            } else {
                                lastLocation = "Unknown";
                            }
                        }
                    });
        } else { // permission denide
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else { // Request the permission
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
                } else { // Permission denied
                    lastLocation = "Unknown";
                }
                return;
            }
        }
    }
}
