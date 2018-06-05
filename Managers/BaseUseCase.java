
import android.content.Context;

import com.requestum.android.motoguy.presentation.MotoguyApp;
import com.requestum.android.motoguy.presentation.dagger.AppComponent;
import com.requestum.android.motoguy.presentation.dagger.IDaggerInit;

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
