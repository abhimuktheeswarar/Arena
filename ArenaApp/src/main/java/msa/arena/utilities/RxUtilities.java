package msa.arena.utilities;

import android.util.Log;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.Subject;

/**
 * Created by Abhimuktheeswarar on 25-08-2017.
 */

public final class RxUtilities {

    public static <D> DisposableObserver<D> get(final Subject<D> subject) {
        return new DisposableObserver<D>() {
            @Override
            public void onNext(D d) {
                subject.onNext(d);
            }

            @Override
            public void onError(Throwable throwable) {
                subject.onError(throwable);
                Log.e(this.getClass().getSimpleName(), throwable.getMessage());
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                subject.onComplete();
            }
        };
    }
}
