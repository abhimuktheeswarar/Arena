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

package msa.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import msa.domain.Repository;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;
import msa.domain.usecases.SearchMoviesObservable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by Abhimuktheeswarar on 19-09-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchMoviesObservableTest {

    private SearchMoviesObservable searchMoviesObservable;

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private Repository mockUserRepository;

    @Before
    public void setUp() {
        searchMoviesObservable = new SearchMoviesObservable(mockUserRepository, mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testSearchMoviesObservable() {
        searchMoviesObservable.buildUseCaseObservable(SearchMoviesObservable.Params.setQuery("abhi"));
        verify(mockUserRepository).searchMoviesObservable("abhi");
        verifyNoMoreInteractions(mockUserRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}
