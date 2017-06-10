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
