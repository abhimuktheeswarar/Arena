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

package msa.arena;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.amitshekhar.DebugDB;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.plugins.RxJavaPlugins;
import jp.wasabeef.takt.Takt;
import msa.arena.di.ApplicationInjector;
import rx_activity_result2.RxActivityResult;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
public class ArenaApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        ArenaApplication arenaApplication = (ArenaApplication) context.getApplicationContext();
        return arenaApplication.refWatcher;
    }

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
            refWatcher = LeakCanary.install(this);
            Takt.stock(this).play();
        }

        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);
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
}
