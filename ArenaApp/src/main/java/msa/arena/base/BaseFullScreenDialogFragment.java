package msa.arena.base;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import msa.arena.ArenaApplication;
import msa.arena.BuildConfig;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Abhimuktheeswarar on 22-09-2016.
 */

public abstract class BaseFullScreenDialogFragment extends DialogFragment {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected CompositeDisposable compositeDisposable;
    protected CompositeSubscription compositeSubscription;

    protected <V extends BaseViewModel> V getViewModelF(Class<V> viewModelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    }

    protected <V extends BaseViewModel> V getViewModelA(Class<V> viewModelClass) {
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(viewModelClass);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (BuildConfig.DEBUG) {
            RefWatcher refWatcher = ArenaApplication.getRefWatcher(getActivity());
            refWatcher.watch(this);
        }
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

    protected abstract void bind();

    protected abstract void unBind();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void setSupportActionBar(Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }

    protected void setDisplayHomeAsUpEnabled(boolean shouldEnable) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(shouldEnable);

    }

    protected void setDisplayShowTitleEnabled(boolean shouldShow) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(shouldShow);

    }

    public void setTitle(String title) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    protected void setIcon(@DrawableRes int resId) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(resId);
    }
}
