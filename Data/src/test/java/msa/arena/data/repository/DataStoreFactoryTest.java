package msa.arena.data.repository;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.TimeoutException;

import io.reactivex.observers.TestObserver;
import msa.arena.data.BuildConfig;
import msa.arena.data.common.RunnableSchedulerRule;
import msa.data.repository.DataStoreFactory;
import msa.data.repository.datasources.remote.RemoteDataSource;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.holder.carrier.Status;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DataStoreFactoryTest {

    @Rule
    public final RunnableSchedulerRule runnableSchedulerRule = new RunnableSchedulerRule();
    private DataStoreFactory dataStoreFactory;

    //@BeforeClass
    public static void setupClass() {
        //RxJavaPlugins.setInitIoSchedulerHandler(__ -> Schedulers.trampoline());
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
    public void networkAvailableCaseZero() throws TimeoutException {

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
    public void networkAvailableCaseOne() throws TimeoutException {

        //RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

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
    public void networkAvailableCaseAsynchronous() throws TimeoutException {

        dataStoreFactory.setInternetAvailable(true);

        TestObserver<ResourceCarrier<RemoteDataSource>> testObserver = new TestObserver<>();

        dataStoreFactory.getRemoteDataSourceObservable().subscribe(testObserver);

        await().timeout(10, SECONDS).until(testObserver::valueCount, equalTo(1));

        testObserver.assertNoErrors();
        for (ResourceCarrier<RemoteDataSource> resourceCarrier : testObserver.values())
            System.out.println("Resource carrier status = " + resourceCarrier.status);

        testObserver.assertValueCount(1);
        testObserver.assertComplete();

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
    public void networkNotAvailableCaseZero() {

        dataStoreFactory.setInternetAvailable(false);

        TestObserver<ResourceCarrier<RemoteDataSource>> testObserver = new TestObserver<>();

        dataStoreFactory.getRemoteDataSourceObservable().doAfterNext(remoteDataSourceResourceCarrier -> {
            System.out.println("doAfterNext");
            dataStoreFactory.getNetworkConnectivityObservable().onNext(true);
        }).subscribe(testObserver);

        testObserver.awaitTerminalEvent(4, SECONDS);


        testObserver.assertNoErrors();
        for (ResourceCarrier<RemoteDataSource> resourceCarrier : testObserver.values())
            System.out.println("Resource carrier status = " + resourceCarrier.status);

        testObserver.assertValueCount(2);
        testObserver.assertComplete();

    }

    @Test
    public void networkNotAvailableCaseOne() throws TimeoutException {

        dataStoreFactory.setInternetAvailable(false);

        TestObserver<ResourceCarrier<RemoteDataSource>> testObserver = new TestObserver<>();

        dataStoreFactory.getRemoteDataSourceObservable().doAfterNext(remoteDataSourceResourceCarrier -> {
            System.out.println("doAfterNext");
            dataStoreFactory.getNetworkConnectivityObservable().onNext(true);
        }).subscribe(testObserver);

        await().timeout(10, SECONDS).until(testObserver::valueCount, equalTo(2));

        testObserver.assertNoErrors();
        for (ResourceCarrier<RemoteDataSource> resourceCarrier : testObserver.values())
            System.out.println("Resource carrier status = " + resourceCarrier.status);

        testObserver.assertValueCount(2);
        testObserver.assertComplete();

    }
}
