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

package msa.arena.data.common;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Abhimuktheeswarar on 05-09-2017.
 */

public class RunnableSchedulerRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description d) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(__ -> Schedulers.from(Runnable::run));
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.from(Runnable::run));
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.from(Runnable::run));
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.from(Runnable::run));
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.from(Runnable::run));

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}