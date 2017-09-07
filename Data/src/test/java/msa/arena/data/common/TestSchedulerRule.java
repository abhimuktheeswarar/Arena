package msa.arena.data.common;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by Abhimuktheeswarar on 05-09-2017.
 */

public class TestSchedulerRule implements TestRule {
    private final Scheduler immediate = new Scheduler() {
        @Override
        public Scheduler.Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(Runnable::run);
        }
    };
    private final TestScheduler testScheduler = new TestScheduler();

    public TestScheduler getTestScheduler() {
        return testScheduler;
    }

    @Override
    public Statement apply(final Statement base, Description d) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(
                        scheduler -> testScheduler);
                RxJavaPlugins.setIoSchedulerHandler(
                        scheduler -> testScheduler);
                RxJavaPlugins.setComputationSchedulerHandler(
                        scheduler -> testScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(
                        scheduler -> testScheduler);
                RxAndroidPlugins.setMainThreadSchedulerHandler(
                        scheduler -> immediate);

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