package msa.arena.data.repository;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import msa.data.repository.ArenaRepository;
import msa.data.repository.DataStoreFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@RunWith(JUnit4.class)
public class ArenaRepositoryTest {

    @Mock
    Context context;
    private DataStoreFactory dataStoreFactory;
    private ArenaRepository arenaRepository;

    @Before
    public void setup() {
        dataStoreFactory = mock(DataStoreFactory.class);
        arenaRepository = new ArenaRepository(dataStoreFactory);
    }

    @Test
    public void simpleCheck() {
        assertThat(dataStoreFactory, notNullValue());
        assertThat(arenaRepository, notNullValue());
    }

    @Test
    public void checkNetwork() {
        when(dataStoreFactory.isNetworkAvailable()).thenReturn(true);
        assertThat(dataStoreFactory.isNetworkAvailable(), is(true));
    }
}
