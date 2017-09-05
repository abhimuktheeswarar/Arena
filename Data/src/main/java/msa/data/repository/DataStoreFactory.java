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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import msa.data.repository.datasources.dummy.DummyDataSource;
import msa.data.repository.datasources.local.realm.RealmDataSource;
import msa.data.repository.datasources.local.sp.SharedPreferenceDataSource;
import msa.data.repository.datasources.remote.ArenaApi;
import msa.data.repository.datasources.remote.RemoteConnection;
import msa.data.repository.datasources.remote.RemoteDataSource;
import msa.domain.holder.carrier.ResourceCarrier;

/**
 * Factory that creates different implementations of {@link BaseDataSource}.
 */
@Singleton
public class DataStoreFactory {

    private static final String TAG = DataStoreFactory.class.getSimpleName();

    private final Context context;
    private final RemoteDataSource remoteDataSource;
    private final SharedPreferenceDataSource sharedPreferenceDataSource;
    private final DummyDataSource dummyDataSource;
    private final PublishSubject<Boolean> networkConnectivityObservable;
    private RealmDataSource realmDataSource;
    private boolean isInternetAvailable;

    @Inject
    public DataStoreFactory(@NonNull Context context) {
        this.context = context.getApplicationContext();
        networkConnectivityObservable = PublishSubject.create();
        remoteDataSource = new RemoteDataSource(RemoteConnection.createService(ArenaApi.class), context);

      /*  ReactiveNetwork.observeNetworkConnectivity(context).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).map(connectivity -> {
            Log.d(TAG, "IS INTERNET AVAILABLE = " + connectivity.isAvailable() + " | " + isNetworkAvailable());
            return connectivity.isAvailable() && isNetworkAvailable();
        }).doOnNext(aBoolean -> isInternetAvailable = aBoolean).subscribe(networkConnectivityObservable);*/

        observeNetworkConnectivity().subscribe(networkConnectivityObservable);

        sharedPreferenceDataSource = new SharedPreferenceDataSource(context);
        dummyDataSource = new DummyDataSource(context);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public boolean isInternetAvailable() {
        return isInternetAvailable;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public void setInternetAvailable(boolean internetAvailable) {
        isInternetAvailable = internetAvailable;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public Observable<Boolean> observeNetworkConnectivity() {
        return ReactiveNetwork.observeNetworkConnectivity(context).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).map(connectivity -> {
            Log.d(TAG, "IS INTERNET AVAILABLE = " + connectivity.isAvailable() + " | " + isNetworkAvailable());
            return connectivity.isAvailable() && isNetworkAvailable();
        }).doOnNext(DataStoreFactory.this::setInternetAvailable);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public PublishSubject<Boolean> getNetworkConnectivityObservable() {
        return networkConnectivityObservable;
    }

    /**
     * Create {@link RemoteDataSource} to retrieve data from the Cloud.
     */
    public RemoteDataSource getRemoteDataSource() {

        return remoteDataSource;
    }

    //Original method
    private Observable<ResourceCarrier<RemoteDataSource>> getRemoteDataSourceObservable12() {
        return Observable.just(ResourceCarrier.success(remoteDataSource)).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).switchMap(new Function<ResourceCarrier<RemoteDataSource>, ObservableSource<? extends ResourceCarrier<RemoteDataSource>>>() {
            @Override
            public ObservableSource<? extends ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull ResourceCarrier<RemoteDataSource> remoteDataSourceResourceCarrier) throws Exception {
                Log.d(TAG, "Is network available 3 = " + isInternetAvailable());
                if (isInternetAvailable()) return Observable.just(remoteDataSourceResourceCarrier);
                else {
                    PublishSubject<Boolean> publishSubject = PublishSubject.create();
                    networkConnectivityObservable.subscribe(publishSubject);
                    return publishSubject.startWith(false).switchMap(new Function<Boolean, ObservableSource<? extends ResourceCarrier<RemoteDataSource>>>() {
                        @Override
                        public ObservableSource<? extends ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                            Log.d(TAG, "Is network available 4 = " + aBoolean + " | Is network available 5 = " + isInternetAvailable());
                            if (aBoolean) {
                                return Observable.just(ResourceCarrier.success(remoteDataSource)).doAfterNext(new Consumer<ResourceCarrier<RemoteDataSource>>() {
                                    @Override
                                    public void accept(ResourceCarrier<RemoteDataSource> remoteDataSourceResourceCarrier) throws Exception {
                                        publishSubject.onComplete();
                                    }
                                });
                            }
                            return Observable.just(ResourceCarrier.error("No internet"));
                        }
                    });
                }
            }
        });
    }

    public Observable<ResourceCarrier<RemoteDataSource>> getRemoteDataSourceObservable() {
        return Observable.just(ResourceCarrier.success(remoteDataSource)).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).switchMap(remoteDataSourceResourceCarrier -> {
            if (isInternetAvailable()) return Observable.just(remoteDataSourceResourceCarrier);
            else {
                PublishSubject<Boolean> publishSubject = PublishSubject.create();
                networkConnectivityObservable.debounce(2, TimeUnit.SECONDS).subscribe(publishSubject);
                return publishSubject.startWith(false).switchMap(new Function<Boolean, ObservableSource<? extends ResourceCarrier<RemoteDataSource>>>() {
                    @Override
                    public ObservableSource<? extends ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return Observable.just(ResourceCarrier.success(remoteDataSource)).doAfterNext(remoteDataSourceResourceCarrier1 -> publishSubject.onComplete());
                        }
                        return Observable.just(ResourceCarrier.error("No internet connection"));
                    }
                });
            }
        });
    }

    Flowable<ResourceCarrier<RemoteDataSource>> getRemoteDataSourceFlowable() {
        return Observable.just(ResourceCarrier.success(remoteDataSource)).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).switchMap(remoteDataSourceResourceCarrier -> {
            if (isInternetAvailable()) return Observable.just(remoteDataSourceResourceCarrier);
            else {
                PublishSubject<Boolean> publishSubject = PublishSubject.create();
                networkConnectivityObservable.debounce(2, TimeUnit.SECONDS).subscribe(publishSubject);
                return publishSubject.startWith(false).switchMap(new Function<Boolean, ObservableSource<? extends ResourceCarrier<RemoteDataSource>>>() {
                    @Override
                    public ObservableSource<? extends ResourceCarrier<RemoteDataSource>> apply(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            return Observable.just(ResourceCarrier.success(remoteDataSource)).doAfterNext(remoteDataSourceResourceCarrier1 -> publishSubject.onComplete());
                        }
                        return Observable.just(ResourceCarrier.error("No internet connection"));
                    }
                });
            }
        }).toFlowable(BackpressureStrategy.BUFFER);
    }

    DummyDataSource getDummyDataSource() {
        return dummyDataSource;
    }

    SharedPreferenceDataSource getSharedPreferenceDataSource() {
        return sharedPreferenceDataSource;
    }

    RealmDataSource getRealmDataStore() {
        if (realmDataSource == null) {
            Realm.init(context);
            realmDataSource = new RealmDataSource(Realm.getDefaultInstance());
        }
        return realmDataSource;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
