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

package msa.data.repository.datasources.local.realm;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by Abhimuktheeswarar on 13-06-2017.
 */

public class RealmObjectObservable<T extends RealmModel> implements
        ObservableOnSubscribe<T> {

    private final T object;

    private RealmObjectObservable(@NonNull T object) {
        this.object = object;
    }

    public static <T extends RealmModel> Observable<T> from(@NonNull T object) {
        return Observable.create(new RealmObjectObservable<T>(object));
    }

    @Override
    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
        // Initial element
        emitter.onNext(object);

        RealmChangeListener<T> changeListener = new RealmChangeListener<T>() {
            @Override
            public void onChange(T element) {
                emitter.onNext(element);
            }
        };

        RealmObject.addChangeListener(object, changeListener);

        emitter.setCancellable(new Cancellable() {
            @Override
            public void cancel() throws Exception {
                RealmObject.removeChangeListener(object, changeListener);
            }
        });
    }
}