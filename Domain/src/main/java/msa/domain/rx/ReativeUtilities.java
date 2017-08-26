package msa.domain.rx;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.Subject;

/**
 * Created by Abhimuktheeswarar on 25-08-2017.
 */

public class ReativeUtilities {

    public static <D> DisposableObserver<D> get(final Subject<D> subject) {
        return new DisposableObserver<D>() {
            @Override
            public void onNext(D d) {
                subject.onNext(d);
            }

            @Override
            public void onError(Throwable t) {
                subject.onError(t);

            }

            @Override
            public void onComplete() {
                subject.onComplete();
            }
        };
    }


}
