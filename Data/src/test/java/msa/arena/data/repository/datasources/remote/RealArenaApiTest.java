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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import msa.data.entities.remote.MovieSearchPojo;
import msa.data.repository.datasources.remote.ArenaApi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Abhimuktheeswarar on 30-08-2017.
 */
@RunWith(JUnit4.class)
public class RealArenaApiTest {

    private ArenaApi arenaApi;

    @Before
    public void createService() throws IOException {

        arenaApi = TestRemoteConnection.createService(ArenaApi.class);
    }

    @Test
    public void searchMovie() throws IOException, InterruptedException {

        MovieSearchPojo movieSearchPojo = arenaApi.searchForMovieObservable("abhi").blockingFirst();

        assertThat(movieSearchPojo, notNullValue());
        assertTrue(movieSearchPojo.getResults().size() > 0);
        //Will fail
        assertTrue("Size of the search result = " + movieSearchPojo.getResults().size(), movieSearchPojo.getResults().size() == 0);

    }

}
