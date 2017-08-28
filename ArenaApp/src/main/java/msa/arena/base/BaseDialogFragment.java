package msa.arena.base;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Abhimuktheeswarar on 02-07-2017.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected CompositeDisposable compositeDisposable;
    protected CompositeSubscription compositeSubscription;

    protected <V extends BaseViewModel> V getViewModel(Class<V> viewModelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    }

    @Override
    public void onStart() {
        super.onStart();
        compositeDisposable = new CompositeDisposable();
        compositeSubscription = new CompositeSubscription();
        bind();
    }


    @Override
    public void onStop() {
        super.onStop();
        unBind();
        compositeDisposable.dispose();
        compositeSubscription.unsubscribe();

    }


    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected abstract void bind();

    protected abstract void unBind();

}
