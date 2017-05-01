package msa.arena.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import msa.arena.ArenaApplication;
import msa.arena.injector.HasComponent;
import msa.arena.injector.components.ApplicationComponent;
import msa.arena.injector.modules.ActivityModule;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector(getApplicationComponent());
    }

    public ArenaApplication getMedikoeApplication() {
        return ((ArenaApplication) getApplication());
    }

    public ApplicationComponent getApplicationComponent() {
        return ((ArenaApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract void initializeInjector(ApplicationComponent applicationComponent);

    @SuppressWarnings("ConstantConditions")
    protected void setToolBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @SuppressWarnings("ConstantConditions")
    protected void setToolBarTitleVisibility(boolean state) {
        getSupportActionBar().setDisplayShowTitleEnabled(state);
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
