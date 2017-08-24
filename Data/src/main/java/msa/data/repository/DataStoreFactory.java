/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package msa.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.realm.Realm;
import msa.data.repository.datasources.dummy.DummyDataSource;
import msa.data.repository.datasources.local.realm.RealmDataSource;
import msa.data.repository.datasources.local.sp.SharedPreferenceDataSource;
import msa.data.repository.datasources.remote.ArenaApi;
import msa.data.repository.datasources.remote.RemoteConnection;
import msa.data.repository.datasources.remote.RemoteDataSource;
import msa.domain.holder.carrier.ResourceCarrier;
import msa.domain.rx.RetryWithDelay;

/**
 * Factory that creates different implementations of {@link BaseDataSource}.
 */
@Singleton
public class DataStoreFactory {

    private static final String TAG = DataStoreFactory.class.getSimpleName();

    private final Context context;
    private final RemoteDataSource remoteDataSource;
    private final SharedPreferenceDataSource sharedPreferenceDataSource;
    private final RealmDataSource realmDataSource;
    private final DummyDataSource dummyDataSource;
    private final Observable<Connectivity> connectivityObservable;
    private final BehaviorSubject<Boolean> behaviorSubject;
    private boolean isInternetAvailable;

    @Inject
    public DataStoreFactory(@NonNull Context context) {
        this.context = context.getApplicationContext();
        behaviorSubject = BehaviorSubject.create();
        remoteDataSource = new RemoteDataSource(RemoteConnection.createService(ArenaApi.class), context);

        connectivityObservable = ReactiveNetwork.observeNetworkConnectivity(context);


        connectivityObservable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).map(new Function<Connectivity, Boolean>() {
            @Override
            public Boolean apply(@io.reactivex.annotations.NonNull Connectivity connectivity) throws Exception {
                return connectivity.isAvailable();
            }
        }).doOnNext(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                isInternetAvailable = aBoolean;
                Log.d(TAG, "1st, Is Network Available = " + isInternetAvailable);
            }
        }).subscribe(behaviorSubject);

       /* connectivityObservable.subscribe(new Consumer<Connectivity>() {
            @Override
            public void accept(Connectivity connectivity) throws Exception {
                isInternetAvailable = connectivity.isAvailable();
                Log.d(TAG, "Is Network Available = " + isInternetAvailable);

            }
        });*/

        sharedPreferenceDataSource = new SharedPreferenceDataSource(context);
        Realm.init(context);
        realmDataSource = new RealmDataSource(Realm.getDefaultInstance());
        dummyDataSource = new DummyDataSource(context);
    }

    /**
     * Create {@link RemoteDataSource} to retrieve data from the Cloud.
     */
    RemoteDataSource getRemoteDataSource() {

        return remoteDataSource;
    }

    Single<ResourceCarrier<RemoteDataSource>> getRemoteDataSourceSingle() {

       /* return connectivityObservable.concatMap(new Function<Connectivity, Observable<ResourceCarrier<RemoteDataSource>>>() {
            @Override
            public Observable<ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull Connectivity connectivity) throws Exception {
                Log.d(RemoteDataSource.class.getSimpleName(), "Is network available 0 = " + connectivity.isAvailable());
                if (connectivity.isAvailable())
                    return Observable.just(ResourceCarrier.success(getRemoteDataSource()));
                else return Observable.just(ResourceCarrier.error("No internet", 2));
            }
        }).single(ResourceCarrier.error("Unknown error"));*/

        return connectivityObservable.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Log.d(TAG, "connectivityObservable = doOnSubscribe 1");
            }
        }).doOnNext(new Consumer<Connectivity>() {
            @Override
            public void accept(Connectivity connectivity) throws Exception {
                Log.d(TAG, "connectivityObservable = doOnNext 1");
            }
        }).singleOrError().flatMap(new Function<Connectivity, SingleSource<? extends ResourceCarrier<RemoteDataSource>>>() {
            @Override
            public SingleSource<? extends ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull Connectivity connectivity) throws Exception {
                if (connectivity.isAvailable())
                    return Single.just(ResourceCarrier.success(getRemoteDataSource()));
                else return Single.just(ResourceCarrier.error("No internet", 2));
            }
        });
        //return Single.just(ResourceCarrier.success(getRemoteDataSource()));
    }

    Observable<ResourceCarrier<RemoteDataSource>> getRemoteDataSourceObservable() {



       /* behaviorSubject.onNext(isInternetAvailable);

        return behaviorSubject.switchMap(new Function<Boolean, Observable<ResourceCarrier<RemoteDataSource>>>() {
            @Override
            public Observable<ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull Boolean connectivity) throws Exception {
                Log.d(DataStoreFactory.class.getSimpleName(), "Is network available 1 = " + connectivity);
                if (connectivity)
                    return Observable.just(ResourceCarrier.success(getRemoteDataSource()));
                else return Observable.just(ResourceCarrier.error("No internet", 2));
            }
        });*/
        Log.d(TAG, "getRemoteDataSourceObservable()");

        behaviorSubject.onNext(isInternetAvailable);

        return connectivityObservable.map(new Function<Connectivity, Boolean>() {
            @Override
            public Boolean apply(@io.reactivex.annotations.NonNull Connectivity connectivity) throws Exception {
                return connectivity.isAvailable();
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Log.d(TAG, "connectivityObservable = doOnSubscribe 1");
            }
        }).doOnNext(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean connectivity) throws Exception {
                Log.d(TAG, "connectivityObservable = doOnNext 1");
            }
        }).switchMap(new Function<Boolean, Observable<ResourceCarrier<RemoteDataSource>>>() {
            @Override
            public Observable<ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull Boolean connectivity) throws Exception {
                Log.d(TAG, "Is network available 1 = " + connectivity);
                if (connectivity)
                    return Observable.just(ResourceCarrier.success(getRemoteDataSource()));
                else return Observable.just(ResourceCarrier.error("No internet", 2));
            }
        });
    }

    Observable<ResourceCarrier<RemoteDataSource>> getRemoteDataSourceObservable2() {
        return Observable.just(ResourceCarrier.success(remoteDataSource)).switchMap(new Function<ResourceCarrier<RemoteDataSource>, ObservableSource<? extends ResourceCarrier<RemoteDataSource>>>() {
            @Override
            public ObservableSource<? extends ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull ResourceCarrier<RemoteDataSource> remoteDataSourceResourceCarrier) throws Exception {
                Log.d(TAG, "Is network available 2 = " + isInternetAvailable);
                if (isInternetAvailable) return Observable.just(remoteDataSourceResourceCarrier);
                else return Observable.error(new Throwable("Network error"));
            }
        }).retryWhen(new RetryWithDelay(5, 5000)).onErrorReturn(throwable -> ResourceCarrier.error("No internet"));
    }

    Observable<ResourceCarrier<RemoteDataSource>> getRemoteDataSourceObservable3() {
        return Observable.just(ResourceCarrier.success(remoteDataSource)).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).switchMap(new Function<ResourceCarrier<RemoteDataSource>, ObservableSource<? extends ResourceCarrier<RemoteDataSource>>>() {
            @Override
            public ObservableSource<? extends ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull ResourceCarrier<RemoteDataSource> remoteDataSourceResourceCarrier) throws Exception {
                Log.d(TAG, "Is network available 3 = " + isInternetAvailable);
                if (isInternetAvailable) return Observable.just(remoteDataSourceResourceCarrier);
                else
                    return behaviorSubject.switchMap(new Function<Boolean, ObservableSource<? extends ResourceCarrier<RemoteDataSource>>>() {
                        @Override
                        public ObservableSource<? extends ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                            Log.d(TAG, "Is network available 4 = " + aBoolean + " | Is network available 5 = " + isInternetAvailable);
                            if (aBoolean)
                                return Observable.just(ResourceCarrier.success(remoteDataSource));
                            return Observable.just(ResourceCarrier.error("No internet"));
                        }
                    });
            }
        });
    }

    DummyDataSource getDummyDataSource() {
        return dummyDataSource;
    }

    SharedPreferenceDataSource getSharedPreferenceDataSource() {
        return sharedPreferenceDataSource;
    }

    RealmDataSource getRealmDataStore() {
        return realmDataSource;
    }
}
