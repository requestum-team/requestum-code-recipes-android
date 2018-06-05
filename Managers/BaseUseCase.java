
import android.content.Context;

import java.util.Observable;

public abstract class BaseUseCase implements IDaggerInit {

    protected Context context;

    @Override
    public void getDaggerInjecting(Context context) {
        this.context = context;
        AppComponent component = ((MotoguyApp) context.getApplicationContext()).getAppComponent();
        injectComponent(component);
    }

    protected abstract void injectComponent(AppComponent component);
}
