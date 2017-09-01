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
