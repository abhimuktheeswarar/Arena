package msa.rehearsal.databind;

import android.util.Log;
import android.view.View;

/**
 * Created by Abhimuktheeswarar on 07-06-2017.
 */

public class DataBindPresenter {

    public void onClickUser(User user) {
        Log.d(DataBindPresenter.class.getSimpleName(), "Clicked Presenter 0 -> " + user.getFirstName());
    }

    public void onClickUser(View view, User user) {
        Log.d(DataBindPresenter.class.getSimpleName(), "Clicked Presenter 1 -> " + user.getFirstName());
        user.setLastName("Abhi 2");
    }
}
