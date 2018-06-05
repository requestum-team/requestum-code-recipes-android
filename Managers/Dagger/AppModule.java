
import android.content.Context;

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
