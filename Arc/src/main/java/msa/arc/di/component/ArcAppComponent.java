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

package msa.arc.di.component;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import io.reactivex.Scheduler;
import msa.arc.ArcApp;
import msa.arc.di.module.ArcAppModule;
import msa.arc.di.module.BuildersModule;
import msa.arc.di.module.UseCaseModule;
import msa.domain.Repository;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, ArcAppModule.class, BuildersModule.class, UseCaseModule.class})
public interface ArcAppComponent {

    void inject(ArcApp arcApp);

    @Named("ui_thread")
    Scheduler uiThread();

    @Named("executor_thread")
    Scheduler executorThread();

    Repository repository();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ArcAppComponent build();
    }
}

