package msa.arena.data.repository;

import android.content.Context;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.TimeoutException;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import msa.arena.data.BuildConfig;
import msa.data.repository.DataStoreFactory;
import msa.data.repository.datasources.remote.RemoteDataSource;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.holder.carrier.Status;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DataStoreFactoryTest {

    private DataStoreFactory dataStoreFactory;

    @BeforeClass
    public static void setupClass() {
        RxJavaPlugins.setInitIoSchedulerHandler(__ -> Schedulers.from(Runnable::run));
    }

    @Before
    public void setup() {
        Context appContext = RuntimeEnvironment.application;
        dataStoreFactory = new DataStoreFactory(appContext);
    }

    @Test
    public void simpleCheck() {
        assertThat(dataStoreFactory, notNullValue());
        assertThat(dataStoreFactory.getRemoteDataSource(), notNullValue());
    }

    @Test
    public void networkConnectivity() {

        TestObserver<Boolean> testObserver = new TestObserver<>();

        dataStoreFactory.observeNetworkConnectivity().subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertNotComplete();

        assertFalse(dataStoreFactory.isInternetAvailable());
    }

    @Test
    public void networkAvailableCase() throws TimeoutException {

        dataStoreFactory.setInternetAvailable(true);

        TestObserver<ResourceCarrier<RemoteDataSource>> testObserver = new TestObserver<>();

        dataStoreFactory.getRemoteDataSourceObservable().subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertComplete();

        ResourceCarrier<RemoteDataSource> resourceCarrier = dataStoreFactory.getRemoteDataSourceObservable().timeout(20, SECONDS).blockingFirst();

        assertThat(resourceCarrier.status, is(Status.SUCCESS));

    }

    @Test
    public void networkNotAvailableCaseSynchronous() throws TimeoutException {

        dataStoreFactory.setInternetAvailable(false);

        ResourceCarrier<RemoteDataSource> remoteDataSourceResourceCarrier = dataStoreFactory.getRemoteDataSourceObservable().doAfterNext(remoteDataSourceResourceCarrier1 -> {
            System.out.println("doAfterNext");
            dataStoreFactory.getNetworkConnectivityObservable().onNext(true);
        }).blockingLast();

        assertThat(remoteDataSourceResourceCarrier, notNullValue());
        assertThat(remoteDataSourceResourceCarrier.status, is(Status.SUCCESS));

    }

    @Test
    public void networkNotAvailableCase() {

        dataStoreFactory.setInternetAvailable(false);

        TestObserver<ResourceCarrier<RemoteDataSource>> testObserver = new TestObserver<>();

        dataStoreFactory.getRemoteDataSourceObservable().doAfterNext(new Consumer<ResourceCarrier<RemoteDataSource>>() {
            @Override
            public void accept(ResourceCarrier<RemoteDataSource> remoteDataSourceResourceCarrier) throws Exception {
                System.out.println("doAfterNext");
                dataStoreFactory.getNetworkConnectivityObservable().onNext(true);
            }
        }).subscribe(testObserver);

        testObserver.awaitTerminalEvent(4, SECONDS);


        testObserver.assertNoErrors();
        for (ResourceCarrier<RemoteDataSource> resourceCarrier : testObserver.values())
            System.out.println("Resource carrier status = " + resourceCarrier.status);

        testObserver.assertValueCount(2);
        testObserver.assertComplete();

    }
}
