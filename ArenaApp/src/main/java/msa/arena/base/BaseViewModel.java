package msa.arena.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Abhimuktheeswarar on 25-06-2017.
 */

public abstract class BaseViewModel extends ViewModel {

    protected final String TAG = this.getClass().getSimpleName();

    protected CompositeDisposable compositeDisposable;

    protected void initializeViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) compositeDisposable.clear();
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }
}
