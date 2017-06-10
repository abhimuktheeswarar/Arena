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
