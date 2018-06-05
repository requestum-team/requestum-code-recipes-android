
public class ApiLogManager {

    private static final String TAG_RESPONSE = "Response from ";
    private static final String TAG_REQUEST = "Request to ";
    private static final String FORMAT_RESPONSE = "%s in %.1fms code %d\n%s";
    private long startTime;
    private long endTime;

    public void printRequest(String url, String headers, String readUtf8) {
        startTime = System.nanoTime();
        Logger.i(TAG_REQUEST , url
                + "\n" + headers
                + "\n" + readUtf8);
    }

    public void printResponce(String url, int code, String msg) {
        endTime = System.nanoTime();
        Logger.i(TAG_RESPONSE, String.format(FORMAT_RESPONSE, url, (endTime - startTime) / 1e6d, code, msg));
    }
}
