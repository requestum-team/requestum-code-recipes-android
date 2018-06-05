
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    private static final String FORMAT_DATA = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String TAG = DateConverter.class.getSimpleName();
    private static final String TIME_PLACEHOLDER = "%s : %s";
    private static final String DATE_PLACEHOLDER = "%s %s %s";
    private static final String TIME_DATE_PLACEHOLDER = "%s %s";
    private static final String[] MONTH_NAME = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    private static Date convertStringToDate(String dateString) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATA, Locale.getDefault());
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            Logger.e(TAG, e.getMessage());
        }
        return date;
    }

    public static String getStringTime(Context context, String dateString) {
        Date date = convertStringToDate(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String hours = String.format(Locale.getDefault(), context.getResources().getString(R.string.format_time), calendar.get(Calendar.HOUR_OF_DAY));
        String minutes = String.format(Locale.getDefault(), context.getResources().getString(R.string.format_time), calendar.get(Calendar.MINUTE));
        return String.format(Locale.getDefault(), TIME_PLACEHOLDER, hours, minutes);
    }

    public static String getStringDate(Context context, String dateString) {
        Date date = convertStringToDate(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        String dayString = String.format(Locale.getDefault(), context.getResources().getString(R.string.format_time), dayOfMonth);
        String yearString = String.format(Locale.getDefault(), context.getResources().getString(R.string.format_year), year);
        return String.format(Locale.getDefault(), DATE_PLACEHOLDER, dayString, MONTH_NAME[month], yearString);
    }

    public static String buildStringDate(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATA, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, hourOfDay, minute);
        return sdf.format(calendar.getTime());
    }

    public static String getTimeDateString(Context context, String date) {
        return String.format(Locale.getDefault(), TIME_DATE_PLACEHOLDER, DateConverter.getStringTime(context, date),
                DateConverter.getStringDate(context, date));

    }
}
