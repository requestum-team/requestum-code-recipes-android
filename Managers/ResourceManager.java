
import android.content.Context;
import android.content.res.Resources;

import com.requestum.android.motoguy.presentation.MotoguyApp;

import javax.inject.Inject;

public class ResourceManager {

    @Inject
    Context context;
    @Inject
    public ResourceManager(Context context) {
        this.context = context;
        ((MotoguyApp) context.getApplicationContext()).getAppComponent().inject(this);
    }

    public Resources getResourceInstance() {
        return context.getResources();
    }
}
