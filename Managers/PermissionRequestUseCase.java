package com.requestum.android.motoguy.domain;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.requestum.android.motoguy.domain.interfaces.IPermissionRequestUseCase;
import com.requestum.android.motoguy.presentation.interfaces.contracts.PictureChoosingContract;

import com.requestum.android.motoguy.domain.interfaces.IPermissionRequestUseCase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;

public class PermissionRequestUseCase implements IPermissionRequestUseCase {

    public static final int PERMISSION_WRITE_STORAGE_REQUEST_CODE = 101;
    public static final int PERMISSION_CAMERA_REQUEST_CODE = 102;
    public static final int PERMISSION_LOCATION_REQUEST_CODE = 103;

    private final WeakReference<Activity> activityWeakRefence;

    public PermissionRequestUseCase(Activity activity) {
        this.activityWeakRefence = new WeakReference<>(activity);
    }

    @Override
    public boolean isPermissionGranted(String permission) {
        final Activity activity = activityWeakRefence.get();
        if (activity != null) {
            return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return false;
        }
    }

    @Override
    public void requestPermission(int requestCode, String... permissions) {
        final Activity activity = activityWeakRefence.get();
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    @Override
    public boolean requestPermissionsIfNeeded(int requestCode, String... permissions) {
        List<String> notGrantedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                notGrantedPermissions.add(permission);
            }
        }
        if (notGrantedPermissions.isEmpty()) {
            return false;
        } else {
            requestPermission(
                    requestCode,
                    notGrantedPermissions.toArray(new String[notGrantedPermissions.size()])
            );
            return true;
        }
    }

    public boolean requestCameraPermission() {
        return requestPermissionsIfNeeded(PERMISSION_CAMERA_REQUEST_CODE, CAMERA, WRITE_EXTERNAL_STORAGE);
    }

    public boolean requestReadExternalStoragePermission() {
        return requestPermissionsIfNeeded(PERMISSION_WRITE_STORAGE_REQUEST_CODE, WRITE_EXTERNAL_STORAGE);
    }

    public boolean requestLocationAccessPermission() {
        return requestPermissionsIfNeeded(PERMISSION_LOCATION_REQUEST_CODE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION);
    }
}

//
//use class:
//useCase = new PermissionRequestUseCase(getActivity());
//
//boolean isLocationAvailable = useCase.isPermissionGranted(ACCESS_FINE_LOCATION);
//        isLocationAvailable &= useCase.isPermissionGranted(ACCESS_COARSE_LOCATION);
//        if (isLocationAvailable) {
//            bindLocationService();
//        } else {
//            useCase.requestLocationAccessPermission();
//        }
