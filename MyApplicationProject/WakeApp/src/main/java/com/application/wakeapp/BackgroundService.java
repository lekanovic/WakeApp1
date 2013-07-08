package com.application.wakeapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Radovan on 2013-07-07.
 */
public class BackgroundService extends Service {
    private static final long POINT_RADIUS = 1000; // in Meters
    private static final long ALERT_EXPIRATION = -1;

    private Float currentSpeed;
    private LocationListener mLocationListener;
    private NotificationManager mNotificationManager;
    private Location finalDestination;
    private LocationManager locationManager;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Radde123 Service: onStartCommand");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification not = new Notification(R.drawable.ic_launcher, "WakeApp", System.currentTimeMillis());
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),
                        Notification.FLAG_ONGOING_EVENT);

        not.flags = Notification.FLAG_ONGOING_EVENT;
        not.setLatestEventInfo(this, "Application Name", "Application Description", contentIntent);
        mNotificationManager.notify(1, not);

        finalDestination = new Location("Destination");

        finalDestination.setLongitude(intent.getExtras().getDouble("lng"));
        finalDestination.setLatitude(intent.getExtras().getDouble("lat"));

        mLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Float distance;
                currentSpeed = location.getSpeed();
                distance = location.distanceTo(finalDestination);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        startGPS();

        return Service.START_STICKY;
    }
    public void startGPS(){
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                //LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,
                mLocationListener);
    }
    public void stopGPS(){
        locationManager.removeUpdates(mLocationListener);
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
}
