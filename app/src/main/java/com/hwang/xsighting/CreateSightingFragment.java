package com.hwang.xsighting;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hwang.xsighting.models.Sighting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class CreateSightingFragment extends Fragment {

  private final String TAG = CreateSightingFragment.class.getSimpleName();
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private FusedLocationProviderClient fusedLocationClient;
  private final int MY_PERMISSIONS_REQUEST_LOCATIONS = 1122;
  private String lastLocation;
  private GeoPoint geoPointLocation;
  private FirebaseUser user;
  private Context mContext;
  private Activity mActivity;

  // Picture variables
  // https://github.com/NJCrain/health-tracker/blob/master/app/src/main/java/com/njcrain/android/healthtracker/activity/ProfileActivity.java
  // https://developer.android.com/training/camera/photobasics
  // https://developer.android.com/training/permissions/requesting
  private final int MY_PERMISSIONS_ID = 0;
  private boolean CAMERA_PERMISSION_GRANTED = false;
  private boolean FILES_PERMISSION_GRANTED = false;
  static final int PICK_IMAGE = 2;
  private int REQUEST_TAKE_PHOTO = 3;
  String currentPhotoPath = null;
  ImageView sightingImage;




  public CreateSightingFragment() {
    // Required empty public constructor
  }

  public static CreateSightingFragment newInstance(String param1, String param2){
    CreateSightingFragment fragment = new CreateSightingFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.fragment_create_sighting, container, false);
    mActivity = getActivity();
    mContext = view.getContext();

    // Gets the imageView that will be updated when the user uploads a picture
    sightingImage = view.findViewById(R.id.sightingImage);

    // Get Location
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
    Log.i("LOCATION", "CALLING GET LOCATION");
    getLocation();

    // Set the username
    user = FirebaseAuth.getInstance().getCurrentUser();

    view.findViewById(R.id.button_submit_report).setOnClickListener(mListener);
    view.findViewById(R.id.takePictureButton).setOnClickListener(mListener);
    view.findViewById(R.id.choosePictureButton).setOnClickListener(mListener);


    return view;
  }

  private final View.OnClickListener mListener = new View.OnClickListener(){
    public void onClick(View view){
      switch (view.getId()){
        case R.id.button_submit_report:
          submitSighting();
          break;
        case R.id.takePictureButton:
          takePicture();
          break;
        case R.id.choosePictureButton:
          choosePictureFromFiles();
          break;
      }
    }
  };

  //TODO: throws exception on super, Commenting out because I am uncertain on what this does and it doesn't seem to break anything
//  @Override
//  protected void onRestart(){
//    super.onRestart();
//
//    Log.i("LOCATION", "CALLING GET LOCATION in RESTART");
//    getLocation();
//
//  }

  public void submitSighting() {


    Date dateData = new Date();
    Timestamp timestamp = new Timestamp(dateData);
    EditText descriptionEditText = mActivity.findViewById(R.id.report_description);
    String description = descriptionEditText.getText().toString();
    final String imageUrl = currentPhotoPath != null ? currentPhotoPath.replace("/", "") : null;
    Sighting sighting = new Sighting(user.getUid(), user.getDisplayName(), timestamp, geoPointLocation, lastLocation, imageUrl, description);

    // Add a new document with a generated ID
    db.collection("sighting")
            .add(sighting)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
              @Override
              public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                savePictureToFireBase(imageUrl); // Saves picture to FireBase

                // https://developer.android.com/guide/topics/ui/notifiers/toasts
                // Toast success message
                Context context = mContext.getApplicationContext();
                CharSequence text = "Your sighting was saved.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                // Redirect
                //TODO: need to finish finish();
//                finish();
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
                // Toast Failure message
                //TODO: getApplicationContext(); needs to be fixed
                Context context = mContext.getApplicationContext();
                CharSequence text = "Something went wrong, please try again.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

              }
            });
  }

  // Saves the picture to the FireBase if
  // https://firebase.google.com/docs/storage/android/upload-files
  public void savePictureToFireBase(String imageUrl) {
    if (currentPhotoPath != null) { // Doesn't save anything if the user did not take a picture

      // Create a storage reference from our app
      FirebaseStorage storage = FirebaseStorage.getInstance();
      StorageReference storageRef = storage.getReference();

      // Create a child reference, imagesRef now points to "images"
      StorageReference imagesRef = storageRef.child(imageUrl);

      // Get the data from an ImageView as bytes
      sightingImage.setDrawingCacheEnabled(true);
      sightingImage.buildDrawingCache();
      Bitmap bitmap = ((BitmapDrawable) sightingImage.getDrawable()).getBitmap();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
      byte[] data = baos.toByteArray();

      UploadTask uploadTask = imagesRef.putBytes(data);
      uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          // DO NOTHING
        }
      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          // DO NOTHING
        }
      });
    }
  }

  // Gets user's current location
  public void getLocation() {

    //Permission Granted
    if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      fusedLocationClient.getLastLocation()
              .addOnSuccessListener(mActivity, new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {

                  // Got last known location. In some rare situations this can be null.
                  if (location != null) {

                    //Turn the location to a String object
                    Geocoder geocoder = new Geocoder(mActivity.getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                      addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                    lastLocation = addresses.get(0).getLocality();

                    // Set the location text
                    TextView locationText =  mActivity.findViewById(R.id.report_location);
                    locationText.setText(lastLocation);

                    // Set the GeoPoint so location can be saved to Cloud Firestore Database
                    geoPointLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                    Log.i("Sighting.Location", "Got a location " + lastLocation);
                  } else {
                    lastLocation = "Unknown";
                  }
                }
              });
    } else { // permission denied
      if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
              Manifest.permission.ACCESS_COARSE_LOCATION)) {
      } else { // Request the permission
        ActivityCompat.requestPermissions(mActivity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATIONS);
      }
    }
  }

  // Allows the user to take and save a new picture
  public void takePicture() {

    // Checks to see if required permission have been granted
    checkForPermissions();

    // If required permission have been granted, invoke intent to take picture
    if (CAMERA_PERMISSION_GRANTED
            && FILES_PERMISSION_GRANTED) {
      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

      // Ensure that there's a camera activity to handle the intent
      if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {

        // Create the File where the photo should go
        File photoFile = null;
        try {
          photoFile = createImageFile();
        } catch (IOException error) {

          // Error occurred while creating the File
          Log.i("ErrorCreatingFile", error.toString());
        }

        // Continue only if the File was successfully created
        if (photoFile != null) {
          Uri photoURI = FileProvider.getUriForFile(mActivity,
                  "com.example.android.fileprovider",
                  photoFile);
          takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
          startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
      }
    }
  }

  // Creates a collision-resistant file name for the photo that was taken
  private File createImageFile() throws IOException {

    // Create an image file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    File storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File image = File.createTempFile(
            imageFileName,  // prefix
            ".jpg",   // suffix
            storageDir      // directory
    );

    // Save a file path for use with ACTION_VIEW intents
    currentPhotoPath = image.getAbsolutePath();
    return image;
  }

  // Lets the user pick an image from their file system
  // https://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
  public void choosePictureFromFiles() {

    // Checks to see if the user has granted permissions to access the files
    checkForPermissions();
    if (FILES_PERMISSION_GRANTED) {
      Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
      getIntent.setType("image/*");

      Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      pickIntent.setType("image/*");

      Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
      chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

      startActivityForResult(chooserIntent, PICK_IMAGE);
    }
  }

  // Updates the profilePicture ImageView with a new photo or one from the user's files
  // Automatically invoked after a user interaction
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {

    // If the interaction was taking a photo
    if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
      // TODO: upload picture to Firebase
      File image = new File(currentPhotoPath);
      Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
      sightingImage.setImageBitmap(bitmap);

      // If the interaction was selecting a photo from files
    } else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
      // https://stackoverflow.com/questions/3401579/get-filename-and-path-from-uri-from-mediastore
      Uri imagePath = data.getData();
      currentPhotoPath = imagePath.getPath().toString();
      try {
        InputStream is = mContext.getContentResolver().openInputStream(imagePath);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        is.close();
        sightingImage.setImageBitmap(bitmap);
      } catch (Exception e) {
        Log.i("ERROR", "IT'S NOT WORKING " + e);
      }
    }
  }

  // Checks to see if the user has granted necessary permissions
  public void checkForPermissions() {

    // Checks to see if the user has already granted CAMERA and WRITE_EXTERNAL_STORAGE permission
    if ((ContextCompat.checkSelfPermission(mContext,
            Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)) {

      // Request the permissions if not
      ActivityCompat.requestPermissions(mActivity,
              new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
              MY_PERMISSIONS_ID);
    } else {

      // 'Remembers' if permission has been granted
      CAMERA_PERMISSION_GRANTED = true;
      FILES_PERMISSION_GRANTED = true;
    }
  }

  // Automatically invoked after the user responds to the permission request in checkForPermissions()
  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_ID: {

        // Checks to see if CAMERA permission was granted, if request is cancelled, the result arrays are empty
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          // CAMERA permission was granted
          CAMERA_PERMISSION_GRANTED = true;
        }

        // Checks to see if WRITE_EXTERNAL_STORAGE permission was granted
        if (grantResults.length > 0
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

          // WRITE_EXTERNAL_STORAGE permission was granted
          FILES_PERMISSION_GRANTED = true;
        }
      }
    }
  }




}
