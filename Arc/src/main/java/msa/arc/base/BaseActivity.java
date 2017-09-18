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

package msa.arc.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.tapadoo.alerter.Alerter;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import msa.arc.R;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

public class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    protected CompositeDisposable compositeDisposable;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private Alerter alerter;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alerter = Alerter.create(BaseActivity.this)
                .setTitle("No internet connectivity")
                .setBackgroundColor(R.color.colorAccent)
                .enableInfiniteDuration(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        compositeDisposable = new CompositeDisposable();
    }

    public Observable<Boolean> listenForInternetConnectivity() {
        return ReactiveNetwork.observeNetworkConnectivity(getApplicationContext()).map(new Function<Connectivity, Boolean>() {
            @Override
            public Boolean apply(@NonNull Connectivity connectivity) throws Exception {
                return connectivity.isAvailable();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                Log.d(BaseActivity.class.getSimpleName(), "Is network available = " + aBoolean);
                if (!aBoolean) {
                    alerter = Alerter.create(BaseActivity.this)
                            .setTitle("No internet connectivity")
                            .setBackgroundColor(R.color.colorAccent)
                            .enableInfiniteDuration(true)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(BaseActivity.this, "Please check for network connectivity", Toast.LENGTH_LONG).show();
                                }
                            });
                    alerter.show();
                } else if (alerter != null) alerter.hide();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
