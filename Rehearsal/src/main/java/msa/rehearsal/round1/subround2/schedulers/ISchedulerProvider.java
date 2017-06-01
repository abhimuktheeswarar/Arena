package msa.rehearsal.round1.subround2.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Allow providing different types of {@link Scheduler}s.
 */
public interface ISchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler ui();
}
