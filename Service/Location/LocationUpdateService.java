
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class LocationUpdateService extends Service implements OnSuccessListener<Location>, LocationListener {

    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 1000;

    @Nullable
    private FusedLocationProviderClient locationClient;
    @Nullable
    private Location currentLocation;
    @NonNull
    private List<OnLocationChangedListener> listeners = new ArrayList<>();
    @NonNull
    private final IBinder binder = new LocalBinder();

    private volatile boolean shouldUpdateMapLocation;

    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            onSuccess(locationResult.getLastLocation());
        }
    };

    @Override
    public void onDestroy() {
        listeners.clear();
        super.onDestroy();
    }

    @SuppressLint("MissingPermission")
    private void initializeLocationClient() {
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onSuccess(Location location) {
        onLocationChanged(location);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLocation = location;
            for (OnLocationChangedListener listener : listeners) {
                listener.onCurrentLocationChanged(currentLocation, shouldUpdateMapLocation);
            }
            shouldUpdateMapLocation = false;
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        if (currentLocation == null) {
            locationClient.getLastLocation().addOnSuccessListener(this);
        } else {
            onSuccess(currentLocation);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initializeLocationClient();
        return binder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        listeners.clear();
    }

    public class LocalBinder extends Binder {

        public void registerLocationChanging(@NonNull OnLocationChangedListener listener) {
            listeners.add(listener);
        }

        public void requestLocationImmediately() {
            shouldUpdateMapLocation = true;
            requestLocation();
        }

        public void unregisterLocationChanging(@NonNull OnLocationChangedListener listener) {
            listeners.remove(listener);
        }

        public void unregisterAll() {
            listeners.clear();
        }
    }

    public interface OnLocationChangedListener {

        void onCurrentLocationChanged(Location location, boolean shouldUpdateMap);
    }
}
