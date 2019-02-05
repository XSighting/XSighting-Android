package com.hwang.xsighting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void goToCreateSighting(View v) {
    Intent createSightingIntent = new Intent(this, CreateSighting.class);
    startActivity(createSightingIntent);
  }

  public void goToAllSightings(View v) {
    Intent AllSightingsIntent = new Intent(this, AllSightings.class);
    startActivity(AllSightingsIntent);
  }
}
