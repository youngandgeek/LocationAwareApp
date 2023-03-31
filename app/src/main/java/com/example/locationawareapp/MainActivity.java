package com.example.locationawareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button locationBtn;
    //location manager and reference of location listener class
    LocationManager mgr;
    MyLocationListener listener;
    //to check if permission granted or not while runtime
    String [] permission={ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public final static int _requestCode=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //obj of location listener class
        listener = new MyLocationListener();
        //cast the mgr
        mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationBtn = (Button) findViewById(R.id.location_btn);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //request location update and pass the listener
                //take the two permission name and save it to array of String in mainclass
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                 ActivityCompat.requestPermissions(MainActivity.this,permission,_requestCode);
                    /** to check whether the user allow access or not override this method
                    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    int[] grantResults)
                    **/
                    // to handle the case where the user grants the permission. See the documentation

                }
                //add the permission to Manifest file ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION
                //get location
                mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            }
        });

    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {

            //get the longitude and latitude in a toast message
            Toast.makeText(MainActivity.this, "Longitude" + location.getLongitude()
                            + "\n" + "Latitude:" + location.getLatitude(),
                    Toast.LENGTH_SHORT).show();
            //to show the toast message one time only
            mgr.removeUpdates(listener);
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {

        }

        @Override
        public void onFlushComplete(int requestCode) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults)
    {
        //switch case on the request code i received
        switch (requestCode){
            //if request code=0 > check permission granted if not -> get the location
            case _requestCode:
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this,permission,_requestCode);

                }
                else{
                    //get location
                    mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
                }
        break;
        }

    }
}