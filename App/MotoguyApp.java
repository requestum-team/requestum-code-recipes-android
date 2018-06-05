package com.requestum.android.motoguy.presentation;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.requestum.android.motoguy.presentation.dagger.AppComponent;
import com.requestum.android.motoguy.presentation.dagger.AppModule;
import com.requestum.android.motoguy.presentation.dagger.DaggerAppComponent;
import com.requestum.android.motoguy.presentation.dagger.IHasComponent;
import com.requestum.android.motoguy.presentation.dagger.NetworkModule;

import io.fabric.sdk.android.Fabric;

/**
 * Created by yuliia on 01.03.18.
 */

public class MotoguyApp extends MultiDexApplication implements IHasComponent {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initComponents();
    }

    private void initComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }

}
