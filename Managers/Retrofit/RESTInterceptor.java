
import android.content.Context;

import com.google.android.gms.common.api.Api;

import java.io.IOException;
import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class RESTInterceptor implements Interceptor {

    @Inject
    UserRepository userRepository;
    @Inject
    ApiLogManager logManager;

    public RESTInterceptor(Context context) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        Buffer buffer = new Buffer();
        if (request.body() != null)
            request.body().writeTo(buffer);

        String token = userRepository.getOAthToken();

        if (token != null && !token.isEmpty()) {
            request = request.newBuilder()
                    .addHeader(ServerApiConstants.HEADER_AUTHORIZATION, ServerApiConstants.HEADER_AUTHORIZATION_BEARER + token)
                    .build();
        }
        logManager.printRequest(request.url().toString(), request.headers().toString(), buffer.readUtf8());

        response = chain.proceed(request);
        String msg = response.body().string();
        msg = msg.replace("\r", ""); // Note: Messages with '\r' not displayed correctly in logcat

        logManager.printResponce(response.request().url().toString(),  response.code(), msg);

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), msg))
                .build();
    }
}
