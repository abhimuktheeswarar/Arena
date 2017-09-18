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
