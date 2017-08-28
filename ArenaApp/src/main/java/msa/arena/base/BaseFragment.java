package msa.arena.base;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import msa.arena.ArenaApplication;
import msa.arena.BuildConfig;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Abhimuktheeswarar on 04-05-2017
 */


/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected <V extends BaseViewModel> V getViewModelF(Class<V> viewModelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    }

    protected <V extends BaseViewModel> V getViewModelA(Class<V> viewModelClass) {
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(viewModelClass);
    }

    @Override
    public void onStart() {
        super.onStart();
        bind();
    }

    @Override
    public void onStop() {
        super.onStop();
        unBind();
        compositeDisposable.clear();
        compositeSubscription.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (BuildConfig.DEBUG) {
            RefWatcher refWatcher = ArenaApplication.getRefWatcher(getActivity());
            refWatcher.watch(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        compositeSubscription.unsubscribe();
    }

    /**
     * Shows a {@link Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        if (message != null && message.length() > 0)
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected int dp2px(float dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    protected int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    protected abstract void bind();

    protected abstract void unBind();


}
