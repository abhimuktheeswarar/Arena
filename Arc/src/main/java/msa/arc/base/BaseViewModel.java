package msa.arc.base;

import android.arch.lifecycle.ViewModel;

import com.msa.domain.entities.User;
import com.msa.domain.usecases.GetUserTypeOne;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Observable;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

public abstract class BaseViewModel extends ViewModel {

    @Inject
    Lazy<GetUserTypeOne> getUserTypeOneLazy;

    public Observable<User> getUser() {
        return getUserTypeOneLazy.get().execute(null);
    }

}
