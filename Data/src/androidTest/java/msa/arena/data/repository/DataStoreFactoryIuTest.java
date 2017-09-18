/*
 * Copyright 2017, Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
@Deprecated
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
