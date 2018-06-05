
import com.requestum.android.motoguy.data.repository.SessionRepository;
import com.requestum.android.motoguy.data.repository.UserRepository;
import com.requestum.android.motoguy.data.rest.RESTClient;
import com.requestum.android.motoguy.data.rest.RESTInterceptor;
import com.requestum.android.motoguy.domain.ResourceManager;
import com.requestum.android.motoguy.presentation.navigators.Navigator;
import com.requestum.android.motoguy.presentation.base.BaseActivity;
import com.requestum.android.motoguy.presentation.dagger.scope.ApplicationScope;

import dagger.Component;

@Component(modules = {AppModule.class, NetworkModule.class})
@ApplicationScope
public interface AppComponent {

    AuthComponent authComponent(AuthModule authModule);
.....
    void inject(BaseActivity baseActivity);
    void inject(Navigator navigator);
}
