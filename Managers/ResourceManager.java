
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Inject;

public class ResourceManager {

    @Inject
    Context context;
    @Inject
    public ResourceManager(Context context) {
        this.context = context;
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
    }

    public Resources getResourceInstance() {
        return context.getResources();
    }
}
