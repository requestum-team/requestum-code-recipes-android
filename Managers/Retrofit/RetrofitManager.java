
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


public class RetrofitManager {
    private IApiRequest restInterface;

    public RetrofitManager(Context context) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new RESTInterceptor(context))
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restInterface = client.create(IApiRequest.class);
    }

    public IApiRequest getRestService() {
        return restInterface;
    }

}
