package msa.arena.data.repository;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.LinkedHashMap;

import io.reactivex.observers.TestObserver;
import msa.arena.data.BuildConfig;
import msa.arena.data.common.RunnableSchedulerRule;
import msa.data.repository.ArenaRepository;
import msa.domain.entities.Movie;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.holder.carrier.Status;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ArenaRepositoryTest {

    @Rule
    public final RunnableSchedulerRule runnableSchedulerRule = new RunnableSchedulerRule();

    private MockDataStoreFactory mockDataStoreFactory;

    private ArenaRepository arenaRepository;

    @Before
    public void setup() {
        Context appContext = RuntimeEnvironment.application;
        mockDataStoreFactory = new MockDataStoreFactory(appContext);
        arenaRepository = new ArenaRepository(mockDataStoreFactory);
    }

    @After
    public void stopService() throws IOException {
        mockDataStoreFactory.getMockWebServer().shutdown();
    }

    @Test
    public void simpleCheck() {
        assertThat(arenaRepository, notNullValue());
        assertThat(arenaRepository.getDataStoreFactory(), notNullValue());

        System.out.println("isInternetAvailable = " + arenaRepository.getDataStoreFactory().isInternetAvailable());
        arenaRepository.getDataStoreFactory().setInternetAvailable(false);
        assertFalse(arenaRepository.getDataStoreFactory().isInternetAvailable());

        arenaRepository.getDataStoreFactory().setInternetAvailable(true);
        assertTrue(arenaRepository.getDataStoreFactory().isInternetAvailable());
    }

    @Test
    public void searchMoviePass() throws IOException, InterruptedException {

        mockDataStoreFactory.setInternetAvailable(true);

        mockDataStoreFactory.enqueueResponse("search.json");

        TestObserver<ResourceCarrier<LinkedHashMap<String, Movie>>> resourceCarrierTestObserver = new TestObserver<>();

        arenaRepository.searchMoviesObservable("abhi").subscribe(resourceCarrierTestObserver);

        resourceCarrierTestObserver.assertNoErrors();
        resourceCarrierTestObserver.assertValueCount(1);
        resourceCarrierTestObserver.assertComplete();
        assertThat(resourceCarrierTestObserver.values().get(0).data, notNullValue());
        assertThat(resourceCarrierTestObserver.values().get(0).data.size(), is(19));
        assertThat(resourceCarrierTestObserver.values().get(0).status, is(Status.SUCCESS));
        assertThat(resourceCarrierTestObserver.values().get(0).data.get("472947").getMovieName(), is("Abhi and Anu"));
    }

    @Test
    public void searchMovieFailAndPass() throws IOException, InterruptedException {

        mockDataStoreFactory.setInternetAvailable(false);

        mockDataStoreFactory.enqueueResponse("search.json");

        TestObserver<ResourceCarrier<LinkedHashMap<String, Movie>>> resourceCarrierTestObserver = new TestObserver<>();

        arenaRepository.searchMoviesObservable("abhi").subscribe(resourceCarrierTestObserver);

        mockDataStoreFactory.getNetworkConnectivityObservable().onNext(true);

        await().timeout(10, SECONDS).until(resourceCarrierTestObserver::valueCount, equalTo(2));

        resourceCarrierTestObserver.assertNoErrors();
        resourceCarrierTestObserver.assertValueCount(2);
        resourceCarrierTestObserver.assertComplete();
        assertThat(resourceCarrierTestObserver.values().get(1).data, notNullValue());
        assertThat(resourceCarrierTestObserver.values().get(1).data.size(), is(19));
        assertThat(resourceCarrierTestObserver.values().get(1).status, is(Status.SUCCESS));
        assertThat(resourceCarrierTestObserver.values().get(1).data.get("472947").getMovieName(), is("Abhi and Anu"));
    }

    @Test
    public void searchMovieFail() throws IOException, InterruptedException {

        mockDataStoreFactory.setInternetAvailable(false);

        mockDataStoreFactory.enqueueResponse("search.json");

        TestObserver<ResourceCarrier<LinkedHashMap<String, Movie>>> resourceCarrierTestObserver = new TestObserver<>();

        arenaRepository.searchMoviesObservable("abhi").subscribe(resourceCarrierTestObserver);

        await().timeout(10, SECONDS).until(resourceCarrierTestObserver::valueCount, equalTo(1));

        resourceCarrierTestObserver.assertNoErrors();
        resourceCarrierTestObserver.assertValueCount(1);
        resourceCarrierTestObserver.assertNotComplete();
        assertThat(resourceCarrierTestObserver.values().get(0).status, is(Status.ERROR));
        assertNull(resourceCarrierTestObserver.values().get(0).data);
    }
}
