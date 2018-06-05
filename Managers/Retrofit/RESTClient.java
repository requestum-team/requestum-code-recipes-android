
import android.content.Context;
import javax.inject.Inject;
import retrofit2.Callback;
import retrofit2.Response;


public class RESTClient {

    @Inject
    RetrofitManager retrofitServerManager;

    @Inject
    public RESTClient(Context context) {
        ((App) context).getAppComponent().inject(this);
    }

 
//todo make api calls here
    public void getMe(Callback<UserEntity> callback) {
        retrofitServerManager.getRestService().getMe().enqueue(callback);
    }

}
