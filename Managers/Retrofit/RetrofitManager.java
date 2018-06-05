
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.requestum.android.motoguy.data.constants.ServerApiConstants.BASE_URL;

/**
 * Created by yuliia on 16.04.18.
 */

public class RetrofitManager {
    private IApiRequest restInterface;

    public RetrofitManager(Context context) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new RESTInterceptor(context))
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(MotoguyGsonConverterFactory.create())
                .build();
        restInterface = client.create(IApiRequest.class);
    }

    public IApiRequest getRestService() {
        return restInterface;
    }

}
