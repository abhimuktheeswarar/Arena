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