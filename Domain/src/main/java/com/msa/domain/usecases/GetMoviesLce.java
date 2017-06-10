package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.interactor.UseCaseTypeTwo;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

public class GetMoviesLce extends UseCaseTypeTwo<Lce<LinkedHashMap<String, Movie>>, GetMoviesLce.Params> {

    private final Repository repository;

    @Inject
    public GetMoviesLce(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(mUiThread, mExecutorThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<Lce<LinkedHashMap<String, Movie>>> buildUseCaseObservable(Params params) {
        return repository.getMoviesLce(params.page).scan(new BiFunction<Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce1, @NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce2) throws Exception {
                LinkedHashMap<String, Movie> previousMovies = linkedHashMapLce1.getData();
                previousMovies.putAll(linkedHashMapLce2.getData());
                return Lce.data(previousMovies);
            }
        });
    }

    public static final class Params {

        private final int page;

        private Params(int page) {
            this.page = page;
        }

        public static GetMoviesLce.Params setPage(int page) {
            return new GetMoviesLce.Params(page);
        }
    }
}
