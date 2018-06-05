
import dagger.Component;

@Component(modules = {AppModule.class, NetworkModule.class})
@ApplicationScope
public interface AppComponent {

    AuthComponent authComponent(AuthModule authModule);

    void inject(BaseActivity baseActivity);
    void inject(Navigator navigator);
}
