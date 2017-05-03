package msa.arena.data.repository.datasources.remote;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import msa.arena.data.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class RemoteConnection {

    private static final String BASE_URL = "";

    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    private static LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BODY)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .addHeader("version", BuildConfig.VERSION_NAME)
            .build();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(JacksonConverterFactory.create());

    static {

        if (BuildConfig.DEBUG) httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(chain -> {

            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}