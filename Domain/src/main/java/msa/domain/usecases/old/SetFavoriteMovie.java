package msa.domain.usecases.old;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import msa.domain.Repository;
import msa.domain.interactor.old.UseCaseTypeComplete;

/**
 * Created by Abhimuktheeswarar on 13-06-2017.
 */

public class SetFavoriteMovie extends UseCaseTypeComplete<SetFavoriteMovie.Params> {


    @Inject
    public SetFavoriteMovie(Repository repository, Scheduler mUiThread, Scheduler mExecutorThread) {
        super(repository, mUiThread, mExecutorThread);
    }

    @Override
    protected Completable buildUseCaseObservable(Params params) {
        return repository.setFavoriteMovie(params.movieId, params.isFavorite);
    }

    public static final class Params {

        private final String movieId;
        private final boolean isFavorite;

        private Params(String movieId, boolean isFavorite) {
            this.movieId = movieId;
            this.isFavorite = isFavorite;
        }

        public static SetFavoriteMovie.Params setFavorite(String movieId, boolean isFavorite) {
            return new SetFavoriteMovie.Params(movieId, isFavorite);
        }
    }
}
