package msa.arena;

import android.app.Activity;
import android.app.Application;

import com.amitshekhar.DebugDB;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.plugins.RxJavaPlugins;
import jp.wasabeef.takt.Takt;
import msa.arena.di.ApplicationInjector;
import msa.arena.injector.components.ApplicationComponent;
import msa.arena.injector.components.DaggerApplicationComponent;
import msa.arena.injector.modules.ApplicationModule;
import rx_activity_result2.RxActivityResult;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public class ArenaApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationInjector.init(this);
        RxActivityResult.register(this);
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
            DebugDB.getAddressLog();
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            Takt.stock(this).play();
        }

        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);

        this.initializeInjector();
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onTerminate() {
        if (BuildConfig.DEBUG) {
            Takt.finish();
        }
        super.onTerminate();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
