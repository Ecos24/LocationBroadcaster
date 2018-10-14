package info.divyang.locationbroadcaster;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class Tracker extends Service
{
    @Override
    public void onCreate()
    {
        //super.onCreate();
        Log.i("CheckSelf", "Service Started");

        // Acquire a reference to the system Location Manager
        LocationManager trackerManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener trackerListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                Log.i(BusTracker.TAG, "onLocationChanged Current Location --> "+location.toString());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle)
            {
                Log.i(BusTracker.TAG, "onStatusChanged Status --> "+s);
            }

            @Override
            public void onProviderEnabled(String s)
            {
                Log.i(BusTracker.TAG, "onProviderEnabled --> " + s);
            }

            @Override
            public void onProviderDisabled(String s)
            {
                Log.i(BusTracker.TAG, "onProviderDisabled --> " + s);
            }
        };

        trackerManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, trackerListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i("CheckSelf", "Service Stopped");
    }
}
