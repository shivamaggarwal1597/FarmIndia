package farmindia.bmpl.com.farmindia;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

//import com.google.android.gms.location.LocationListener;

//import android.location.LocationListener;
//import android.location.LocationManager;

//import com.google.android.gms.location.LocationListener;

//import com.google.android.gms.location.LocationListener;

/**
 * Created by nikhilgupta on 02/04/17.
 */

class GPS_Service extends Service{
    private LocationListener listener;
    private LocationManager locationManager;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    int cnt=0;
    @Override
    public void onCreate() {
        listener = new LocationListener() {

            @Override
            public void onLocationChanged(android.location.Location location) {
                if(cnt==0){
                    Intent i= new Intent("location_updates");
                    i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                    sendBroadcast(i);
                }cnt++;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        if(locationManager != null)
        {
            locationManager.removeUpdates(listener);
        }

    }

}
