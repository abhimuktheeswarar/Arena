package msa.arc.base;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

public class BaseFragment extends Fragment implements Injectable {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected <V extends BaseViewModel> V getViewModel(Class<V> viewModelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    }
}
