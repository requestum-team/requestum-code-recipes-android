
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class App extends MultiDexApplication implements IHasComponent {
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
