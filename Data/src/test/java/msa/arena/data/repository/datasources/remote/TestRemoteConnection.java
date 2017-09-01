package msa.arena.data.repository.datasources.remote;

import android.support.annotation.NonNull;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import msa.arena.data.BuildConfig;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Abhimuktheeswarar on 01-09-2017.
 */

public class TestRemoteConnection {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    //private static final String BASE_URL = BuildConfig.MEDI_BASE_URL;

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

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(JacksonConverterFactory.create());

    static {

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(httpLoggingInterceptor);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.THEMOVIEDB_API_KEY)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    //.header(BuildConfig.MEDI_TOKEN_KEY, BuildConfig.MEDI_TOKEN)
                    .method(original.method(), original.body())
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        httpClient.dispatcher(new Dispatcher(new ImmediateExecutor()));

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    private static class ImmediateExecutor implements ExecutorService {
        @Override
        public void execute(Runnable command) {
            command.run();
        }

        @Override
        public void shutdown() {

        }

        @NonNull
        @Override
        public List<Runnable> shutdownNow() {
            return null;
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public boolean awaitTermination(long l, @NonNull TimeUnit timeUnit) throws InterruptedException {
            return false;
        }

        @NonNull
        @Override
        public <T> Future<T> submit(@NonNull Callable<T> callable) {
            return null;
        }

        @NonNull
        @Override
        public <T> Future<T> submit(@NonNull Runnable runnable, T t) {
            return null;
        }

        @NonNull
        @Override
        public Future<?> submit(@NonNull Runnable runnable) {
            return null;
        }

        @NonNull
        @Override
        public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException {
            return null;
        }

        @NonNull
        @Override
        public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection, long l, @NonNull TimeUnit timeUnit) throws InterruptedException {
            return null;
        }

        @NonNull
        @Override
        public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection, long l, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }


}
