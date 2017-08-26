package msa.domain.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by Abhimuktheeswarar on 25-08-2017.
 */

public class RetryWithDelayF implements Function<Flowable<? extends Throwable>, Flowable<?>> {

    private final int _maxRetries;
    private final int _retryDelayMillis;
    private int _retryCount;

    public RetryWithDelayF(final int maxRetries, final int retryDelayMillis) {
        _maxRetries = maxRetries;
        _retryDelayMillis = retryDelayMillis;
        _retryCount = 0;
    }

    // this is a notificationhandler, all that is cared about here
    // is the emission "type" not emission "content"
    // only onNext triggers a re-subscription (onError + onComplete kills it)

    @Override
    public Flowable<?> apply(Flowable<? extends Throwable> inputObservable) {

        // it is critical to use inputObservable in the chain for the result
        // ignoring it and doing your own thing will break the sequence

        return inputObservable.flatMap(
                new Function<Throwable, Flowable<?>>() {
                    @Override
                    public Flowable<?> apply(Throwable throwable) {
                        if (++_retryCount < _maxRetries) {

                            // When this Observable calls onNext, the original
                            // Observable will be retried (i.e. re-subscribed)


                            return Flowable.timer(_retryCount * _retryDelayMillis, TimeUnit.MILLISECONDS);
                        }


                        // Max retries hit. Pass an error so the chain is forcibly completed
                        // only onNext triggers a re-subscription (onError + onComplete kills it)
                        return Flowable.error(throwable);
                    }
                });
    }
}