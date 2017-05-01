package msa.arena.injector.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import msa.arena.injector.PerActivity;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the appCompatActivity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }


}

