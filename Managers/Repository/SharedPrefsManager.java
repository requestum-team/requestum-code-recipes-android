
import android.content.Context;

import javax.inject.Inject;

/**
 * Created by yuliia on 01.03.18.
 */

public class SharedPrefsManager extends BaseSharePrefs {

    private static final String LOG_IN = "login";
    private static final int DEFAULT_NUMBER = -1;

    @Inject
    public SharedPrefsManager(Context context) {
        super(context);
    }

    void setUserLogin(boolean sessionExist) {
        editor.putBoolean(LOG_IN, sessionExist).apply();
    }

    boolean isUserLogin() {
        return sharedPreferences.getBoolean(LOG_IN, false);
    }

}
