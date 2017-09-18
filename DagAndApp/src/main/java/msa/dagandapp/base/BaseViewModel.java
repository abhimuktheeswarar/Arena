/*
 *   Copyright 2017 Abhi Muktheeswarar
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package msa.dagandapp.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Abhimuktheeswarar on 18-09-2017.
 */

public abstract class BaseViewModel<N> extends ViewModel {

    private N mNavigator;

    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel() {

    }

    public void onViewCreated() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public void onDestroyView() {
        mCompositeDisposable.dispose();
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }


}
