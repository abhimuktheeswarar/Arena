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

package msa.arena.data.repository.datasources.remote;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import java.io.IOException;
import java.util.LinkedHashMap;

import io.reactivex.observers.TestObserver;
import msa.data.repository.datasources.remote.ArenaApi;
import msa.data.repository.datasources.remote.RemoteDataSource;
import msa.domain.entities.Movie;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.holder.carrier.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@RunWith(JUnit4.class)
public class RealRemoteDataSourceTest {

    @Mock
    Context context;
    private RemoteDataSource remoteDataSource;
    private ArenaApi arenaApi;

    @Before
    public void setup() {
        arenaApi = TestRemoteConnection.createService(ArenaApi.class);
        remoteDataSource = new RemoteDataSource(arenaApi, context);
    }

    @Test
    public void searchMovie() throws IOException, InterruptedException {


        TestObserver<ResourceCarrier<LinkedHashMap<String, Movie>>> resourceCarrierTestObserver = new TestObserver<>();

        remoteDataSource.searchMoviesObservable("abhi").subscribe(resourceCarrierTestObserver);

        resourceCarrierTestObserver.assertNoErrors();
        resourceCarrierTestObserver.assertComplete();

        assertThat(remoteDataSource, notNullValue());

        ResourceCarrier<LinkedHashMap<String, Movie>> linkedHashMapResourceCarrier = remoteDataSource.searchMoviesObservable("abhi").blockingFirst();

        assertThat(linkedHashMapResourceCarrier.data, notNullValue());
        assertThat(linkedHashMapResourceCarrier.status, is(Status.SUCCESS));
        assertThat(linkedHashMapResourceCarrier.data.size(), is(19));
        assertThat(linkedHashMapResourceCarrier.data.get("472947").getMovieName(), is("Abhi and Anu"));

    }
}
