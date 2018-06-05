package com.requestum.android.motoguy.presentation.dagger;

import android.content.Context;

import com.requestum.android.motoguy.data.repository.SessionRepository;
import com.requestum.android.motoguy.data.repository.SharedPrefsManager;
import com.requestum.android.motoguy.data.repository.UserRepository;
import com.requestum.android.motoguy.domain.ResourceManager;
import com.requestum.android.motoguy.presentation.dagger.scope.ApplicationScope;
import com.requestum.android.motoguy.presentation.presenter.PicturePickPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @ApplicationScope
    @Provides
    Context context() {
        return context;
    }

    @ApplicationScope
    @Provides
    SharedPrefsManager sharedPrefsManager() {
        return new SharedPrefsManager(context);
    }

    @ApplicationScope
    @Provides
    ResourceManager resourceManager() {
        return new ResourceManager(context);
    }
}
