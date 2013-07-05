package com.application.wakeapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Location myLocation;
    private Integer radius = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocation = getCurrentLocation();

        System.out.println("Radde123 lat: " + myLocation.getLatitude() +
                " lng: " + myLocation.getLongitude() );

        new Background().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private Location getCurrentLocation(){
        Location currentLocation;

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if ( currentLocation == null)
            currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        return currentLocation;
    }

    class Background extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            Stations stations =
                    new Stations(myLocation.getLongitude(),
                            myLocation.getLatitude(),
                            radius);

            ArrayList<String> list =
                    stations.getAllStations();
            /*
            for (int i=0;i<list.size();i++){
                System.out.println("Radde123 " + list.get(i));
            }*/

            return null;
        }
    }
}