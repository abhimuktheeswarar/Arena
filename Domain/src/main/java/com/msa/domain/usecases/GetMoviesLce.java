package com.msa.domain.usecases;

import com.msa.domain.Repository;
import com.msa.domain.entities.Lce;
import com.msa.domain.entities.Movie;
import com.msa.domain.interactor.UseCaseTypeTwo;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

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
        return repository.getMoviesLce(params.page)/*.scan(Lce.<LinkedHashMap<String, Movie>>loading(), new BiFunction<Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>, Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public Lce<LinkedHashMap<String, Movie>> apply(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce1, @NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce2) throws Exception {
                if (linkedHashMapLce1.getData() != null) {
                    LinkedHashMap<String, Movie> previous = linkedHashMapLce1.getData();
                    previous.putAll(linkedHashMapLce2.getData());
                    Lce<LinkedHashMap<String, Movie>> total = Lce.data(previous);
                    return total;
                } else return linkedHashMapLce1;
            }
        }).filter(new Predicate<Lce<LinkedHashMap<String, Movie>>>() {
            @Override
            public boolean test(@NonNull Lce<LinkedHashMap<String, Movie>> linkedHashMapLce) throws Exception {
                return linkedHashMapLce.getData() != null;
            }
        })*/;
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
