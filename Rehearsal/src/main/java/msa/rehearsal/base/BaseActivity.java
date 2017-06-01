package msa.rehearsal.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import msa.rehearsal.RehearsalApp;
import msa.rehearsal.injector.HasComponent;
import msa.rehearsal.injector.components.ApplicationComponent;
import msa.rehearsal.injector.modules.ActivityModule;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector(getApplicationComponent());
    }

    public RehearsalApp getMedikoeApplication() {
        return ((RehearsalApp) getApplication());
    }

    public ApplicationComponent getApplicationComponent() {
        return ((RehearsalApp) getApplication()).getApplicationComponent();
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
