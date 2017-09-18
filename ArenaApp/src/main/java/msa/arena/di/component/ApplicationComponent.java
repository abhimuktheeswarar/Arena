
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

package msa.arena.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import msa.arena.ArenaApplication;
import msa.arena.di.module.ActivityBuildersModule;
import msa.arena.di.module.ApplicationModule;
import msa.arena.di.module.UseCaseModule;
import msa.domain.Repository;
import msa.domain.executor.PostExecutionThread;
import msa.domain.executor.ThreadExecutor;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */
@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                ApplicationModule.class,
                ActivityBuildersModule.class,
                UseCaseModule.class
        }
)
public interface ApplicationComponent {

    void inject(ArenaApplication arenaApplication);

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    Repository repository();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
  }
}
