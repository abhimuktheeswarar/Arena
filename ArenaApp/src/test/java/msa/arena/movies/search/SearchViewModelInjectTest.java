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

package msa.arena.movies.search;

/**
 * Created by Abhimuktheeswarar on 29-08-2017.
 */


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import msa.domain.holder.datastate.DataState;
import msa.domain.usecases.SearchMoviesObservable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link SearchViewModel}
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class SearchViewModelInjectTest {

    //@Rule
    //public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    SearchMoviesObservable searchMoviesObservable;

    @InjectMocks
    SearchViewModel searchViewModel;


  /*  @Before
    public void setup() {

        searchMoviesObservable = mock(SearchMoviesObservable.class);
        searchViewModel = new SearchViewModel(searchMoviesObservable);

    }*/

    @Test
    public void firstTest() {

        assertThat(searchMoviesObservable, notNullValue());

        assertThat(searchViewModel.dataStateContainer.getDataState(), equalTo(DataState.INITIALIZE));

        searchViewModel.setQueryText("Abhi");
        assertThat(searchViewModel.getQueryText(), is("Abhi"));
    }
}
