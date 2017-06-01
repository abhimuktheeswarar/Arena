package msa.dagandapp.round2;

import javax.inject.Inject;
import javax.inject.Named;

import msa.dagandapp.mydaggy.PerActivity;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

@PerActivity
public class Round2Presenter implements BasePresenter {


    private final String msg;

    private BaseView baseView;

    @Inject
    public Round2Presenter(@Named("msg") String msg) {
        this.msg = msg;
    }

    public void setBaseView(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void load() {
        baseView.loadData(msg);
    }
}
