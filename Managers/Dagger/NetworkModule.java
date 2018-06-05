
import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @ApplicationScope
    @Provides
    @Inject
    RetrofitManager retrofitManager(Context context) {
        return new RetrofitManager(context);
    }
......
    @ApplicationScope
    @Provides
    @Inject
    ApiLogManager apiLogManager() {
        return new ApiLogManager();
    }
}
