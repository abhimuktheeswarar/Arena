package msa.domain.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import msa.domain.holder.carrier.ResourceCarrier;

/**
 * Created by Abhimuktheeswarar on 25-08-2017.
 */

public class CustomRetryWithDelay<D> implements Function<Observable<ResourceCarrier<D>>, Observable<?>> {

    private final int _maxRetries;
    private final int _retryDelayMillis;
    private int _retryCount;

    public CustomRetryWithDelay(final int maxRetries, final int retryDelayMillis) {
        _maxRetries = maxRetries;
        _retryDelayMillis = retryDelayMillis;
        _retryCount = 0;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<ResourceCarrier<D>> resourceCarrierObservable) throws Exception {
        return resourceCarrierObservable.flatMap(new Function<ResourceCarrier<D>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull ResourceCarrier<D> dResourceCarrier) throws Exception {
                if (++_retryCount < _maxRetries) {

                    // When this Observable calls onNext, the original
                    // Observable will be retried (i.e. re-subscribed)


                    return Observable.timer(_retryCount * _retryDelayMillis, TimeUnit.MILLISECONDS);
                }


                // Max retries hit. Pass an error so the chain is forcibly completed
                // only onNext triggers a re-subscription (onError + onComplete kills it)
                return Observable.just(dResourceCarrier);
            }
        });
    }

    // this is a notificationhandler, all that is cared about here
    // is the emission "type" not emission "content"
    // only onNext triggers a re-subscription (onError + onComplete kills it)


}