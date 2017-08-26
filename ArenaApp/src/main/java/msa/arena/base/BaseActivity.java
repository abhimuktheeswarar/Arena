package msa.arena.base;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.Lazy;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    Lazy<Observable<Boolean>> observeNetworkConnectivity;

    private RxPermissions rxPermissions;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    protected <V extends BaseViewModel> V getViewModelF(Class<V> viewModelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    }

    protected <V extends BaseViewModel> V getViewModelA(Class<V> viewModelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bind();
    }

    @SuppressWarnings("ConstantConditions")
    protected void setToolBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @SuppressWarnings("ConstantConditions")
    protected void setToolBarTitleVisibility(boolean state) {
        getSupportActionBar().setDisplayShowTitleEnabled(state);
    }

    protected void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unBind();
        compositeDisposable.clear();
        compositeSubscription.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        compositeSubscription.unsubscribe();
    }

    public RxPermissions getRxPermissions() {
        if (rxPermissions == null) rxPermissions = new RxPermissions(this);
        return rxPermissions;
    }

    public Observable<Boolean> getObserveNetworkConnectivity() {
        return observeNetworkConnectivity.get().map(aBoolean -> aBoolean && isNetworkAvailable());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected abstract void bind();

    protected abstract void unBind();

}
