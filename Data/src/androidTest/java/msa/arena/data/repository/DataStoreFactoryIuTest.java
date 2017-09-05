package msa.arena.data.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.TestObserver;
import msa.data.repository.DataStoreFactory;
import msa.data.repository.datasources.remote.RemoteDataSource;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.holder.carrier.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class DataStoreFactoryIuTest {


    private DataStoreFactory dataStoreFactory;

    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        dataStoreFactory = new DataStoreFactory(appContext);
    }

    @Test
    public void simpleCheck() {
        assertThat(dataStoreFactory, notNullValue());
        assertThat(dataStoreFactory.getRemoteDataSource(), notNullValue());
    }

    @Test
    public void checkNetwork() throws TimeoutException {


        TestObserver<ResourceCarrier<RemoteDataSource>> testObserver = new TestObserver<>();

        dataStoreFactory.getRemoteDataSourceObservable().subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertNotComplete();


        ResourceCarrier<RemoteDataSource> resourceCarrier = dataStoreFactory.getRemoteDataSourceObservable().timeout(1, TimeUnit.MINUTES).blockingLast();

        if (dataStoreFactory.isNetworkAvailable())
            assertThat(resourceCarrier.status, is(Status.SUCCESS));
        else assertThat(resourceCarrier.status, is(Status.ERROR));

    }
}
