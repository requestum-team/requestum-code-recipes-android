
import android.content.Context;

import com.requestum.android.motoguy.data.rest.ApiLogManager;
import com.requestum.android.motoguy.data.rest.RetrofitManager;
import com.requestum.android.motoguy.data.rest.RetrofitManagerSerializedNulls;
import com.requestum.android.motoguy.domain.ConnectionManager;
import com.requestum.android.motoguy.presentation.dagger.scope.ApplicationScope;
import com.requestum.android.motoguy.tools.ImageUploadExecutor;

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
