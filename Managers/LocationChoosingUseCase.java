package com.requestum.android.motoguy.domain;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.requestum.android.motoguy.presentation.interfaces.contracts.LocationChoosingContract;
import com.requestum.android.motoguy.presentation.navigators.LocationChoosingNavigator;
import com.requestum.android.motoguy.presentation.presenter.LocationChoosingPresenter;

import java.lang.ref.WeakReference;

import static android.app.Activity.RESULT_OK;

public class LocationChoosingUseCase implements LocationChoosingContract.UseCase {

    private final WeakReference<Activity> activityWeakRefence;

    public LocationChoosingUseCase(Activity activity) {
        this.activityWeakRefence = new WeakReference<>(activity);
    }

    @Override
    public Pair<Status, Place> getChosenPlace(int resultCode, @Nullable Intent data) {
        Status status;
        Place place = null;

        final Activity activity = activityWeakRefence.get();

        if (resultCode == RESULT_OK) {
            status = new Status(LocationChoosingPresenter.STATUS_SUCCESS);
            place = PlaceAutocomplete.getPlace(activity, data);
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            status = PlaceAutocomplete.getStatus(activity, data);
        } else {
            status = new Status(LocationChoosingPresenter.STATUS_UNDEFINED);
        }

        return new Pair<>(status, place);
    }
}
