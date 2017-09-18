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

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

public class BaseFragment extends Fragment implements Injectable {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected CompositeDisposable compositeDisposable;
    protected CompositeSubscription compositeSubscription;

    protected <V extends BaseViewModel> V getViewModel(Class<V> viewModelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    }

    public Observable<Boolean> listenForInternetConnectivity() {
        return ((BaseActivity) getActivity()).listenForInternetConnectivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        compositeDisposable = new CompositeDisposable();
        compositeSubscription = new CompositeSubscription();
    }


    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
        compositeSubscription.unsubscribe();
    }
}
