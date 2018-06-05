package com.requestum.android.motoguy.presentation.navigators;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.requestum.android.motoguy.R;
import com.requestum.android.motoguy.presentation.interfaces.contracts.PictureChoosingContract;

import java.lang.ref.WeakReference;

public class ImagePickerNavigator implements PictureChoosingContract.Navigator {

    private static final String TAG = "Image Picker Navigator";

    public static final int PICK_IMAGE_REQUEST = 0x0001;
    public static final int TAKE_IMAGE_REQUEST = 0x0002;

    private static final String IMAGE_TYPE = "image/*";

    private WeakReference<Activity> activityWeakReference;

    public ImagePickerNavigator(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void goToCameraActivity(Uri imageURI) {
        final Activity activity = activityWeakReference.get();
        if (activity != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            activity.startActivityForResult(
                    intent,
                    TAKE_IMAGE_REQUEST
            );
        }
    }

    @Override
    public void goToImagePickingActivity() {
        final Activity activity = activityWeakReference.get();
        if (activity != null) {
            Intent pickIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            pickIntent.setType(IMAGE_TYPE);
            Intent chooserIntent = Intent.createChooser(
                    pickIntent,
                    activity.getResources().getString(R.string.select_image)
            );
            activity.startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
        }
    }
}
