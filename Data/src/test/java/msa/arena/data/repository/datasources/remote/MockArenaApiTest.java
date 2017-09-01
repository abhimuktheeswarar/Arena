package msa.arena.data.repository.datasources.remote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import msa.data.entities.remote.MovieSearchPojo;
import msa.data.entities.remote.MovieSearchResult;
import msa.data.repository.datasources.remote.ArenaApi;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Abhimuktheeswarar on 30-08-2017.
 */
@RunWith(JUnit4.class)
public class MockArenaApiTest {

    private ArenaApi arenaApi;

    private MockWebServer mockWebServer;

    @Before
    public void createService() throws IOException {
        mockWebServer = new MockWebServer();
        arenaApi = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ArenaApi.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void searchMovie() throws IOException, InterruptedException {
        enqueueResponse("search.json");

        MovieSearchPojo movieSearchPojo = arenaApi.searchForMovieObservable("abhi").blockingFirst();

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/search/movie?query=abhi"));
        assertThat(movieSearchPojo, notNullValue());
        assertThat(movieSearchPojo.getResults().size(), is(19));

        MovieSearchResult movieSearchResult = movieSearchPojo.getResults().get(0);

        assertThat(movieSearchResult.getTitle(), is("Abhi and Anu"));

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
        //simulate network delays
        //mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS);
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)));
    }

}
