package msa.arena.base;

import android.util.Log;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Abhimuktheeswarar on 26-06-2017.
 */

public abstract class BaseDisposableSubscriber<D> extends DisposableSubscriber<D> {


    @Override
    public void onNext(D data) {

    }

    @Override
    public void onError(Throwable throwable) {
        Log.e(this.getClass().getSimpleName(), throwable.getMessage());
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
