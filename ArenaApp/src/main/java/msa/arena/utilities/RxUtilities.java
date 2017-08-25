package msa.arena.utilities;

import android.util.Log;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.subjects.Subject;
import io.reactivex.subscribers.DisposableSubscriber;

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

    public static <D> DisposableSubscriber<D> get(final FlowableProcessor<D> processor) {
        return new DisposableSubscriber<D>() {
            @Override
            public void onNext(D d) {
                //Log.d(RxUtilities.class.getSimpleName(),"onNext");
                processor.onNext(d);
            }

            @Override
            public void onError(Throwable throwable) {
                //Log.d(RxUtilities.class.getSimpleName(),"onError");
                processor.onError(throwable);
                Log.e(this.getClass().getSimpleName(), throwable.getMessage());
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                //Log.d(RxUtilities.class.getSimpleName(),"onComplete");
                processor.onComplete();
            }
        };
    }
}
