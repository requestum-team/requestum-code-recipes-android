
import android.util.Log;

public class Logger {
    private static boolean logEnabled = BuildConfig.DEBUG;

    public static void e(String tag, String message) {
        if (logEnabled && message != null && !message.isEmpty()) {
            Log.e(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (logEnabled) {
            Log.i(tag, message);
        }
    }

}
