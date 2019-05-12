package com.example.tundex_droid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import android.app.Activity;


public class MyLocationListener extends Activity implements LocationListener {

    static Location curLoc;


    //Toast toast=Toast.makeText(getApplicationContext(),"hihi",Toast.LENGTH_LONG);toast.show();

    public static String getMyLocPLS(Context context) {

        double latitude;
        double longitude;

        Context mContext = context;

        // The minimum distance to change updates in metters
        final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

        // The minimum time beetwen updates in milliseconds
        final long MIN_TIME_BW_UPDATES = 0;

        LocationManager locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        // getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) return "No provider";

        locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

        if (locationManager == null) return "Can't update location";

        try {
                curLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (curLoc == null) {
                return "empty";
            } else {
                latitude = curLoc.getLatitude();
                longitude = curLoc.getLongitude();
            }
        } catch (SecurityException e) {
            return "Error in rights";
        } catch (Exception e) {
            return "Error in vars";
        }
        return "Seems like all OK";
        //return String.valueOf(latitude);

    }

    @Override
    public void onLocationChanged(Location loc) {
            curLoc = loc;

/*            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            curLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            latitude = curLoc.getLatitude();
            longitude = curLoc.getLongitude();
*/
            Toast toast=Toast.makeText(getApplicationContext(),"Changing location",Toast.LENGTH_LONG);toast.show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        Toast toast=Toast.makeText(getApplicationContext(),"Status changed",Toast.LENGTH_LONG);toast.show();

    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {


        Toast toast=Toast.makeText(getApplicationContext(),"Provider Enabled",Toast.LENGTH_LONG);toast.show();

    }
}
