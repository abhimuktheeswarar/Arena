package msa.arena.data.repository;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import msa.data.repository.ArenaRepository;
import msa.data.repository.DataStoreFactory;
import msa.data.repository.datasources.remote.ArenaApi;
import msa.data.repository.datasources.remote.RemoteDataSource;
import msa.domain.entities.Movie;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.holder.carrier.Status;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@RunWith(JUnit4.class)
public class MockArenaRepositoryTest {

    @Mock
    Context context;

    private DataStoreFactory dataStoreFactory;

    private ArenaRepository arenaRepository;

    private RemoteDataSource remoteDataSource;
    private MockWebServer mockWebServer;
    private ArenaApi arenaApi;

    @Before
    public void setup() {
        dataStoreFactory = mock(DataStoreFactory.class);
        arenaRepository = new ArenaRepository(dataStoreFactory);
        mockWebServer = new MockWebServer();
        arenaApi = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ArenaApi.class);

        remoteDataSource = new RemoteDataSource(arenaApi, context);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void simpleCheck() {
        assertThat(dataStoreFactory, notNullValue());
        assertThat(arenaRepository, notNullValue());
    }

    @Test
    public void searchMovie() throws IOException, InterruptedException {

        when(dataStoreFactory.getRemoteDataSourceObservable()).thenReturn(Observable.just(ResourceCarrier.success(remoteDataSource)));

        enqueueResponse("search.json");

        TestObserver<ResourceCarrier<LinkedHashMap<String, Movie>>> resourceCarrierTestObserver = new TestObserver<>();

        arenaRepository.searchMoviesObservable("abhi").subscribe(resourceCarrierTestObserver);

        resourceCarrierTestObserver.assertNoErrors();
        resourceCarrierTestObserver.assertComplete();

        enqueueResponse("search.json");

        ResourceCarrier<LinkedHashMap<String, Movie>> linkedHashMapResourceCarrier = arenaRepository.searchMoviesObservable("abhi").blockingFirst();

        assertThat(linkedHashMapResourceCarrier.data, notNullValue());
        assertThat(linkedHashMapResourceCarrier.status, is(Status.SUCCESS));
        assertThat(linkedHashMapResourceCarrier.data.size(), is(19));
        assertThat(linkedHashMapResourceCarrier.data.get("472947").getMovieName(), is("Abhi and Anu"));
    }

    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)));
    }
}