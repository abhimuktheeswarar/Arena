package msa.arc;

import android.app.Activity;
import android.app.Application;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import msa.arc.base.AppInjector;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

public class ArcApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
