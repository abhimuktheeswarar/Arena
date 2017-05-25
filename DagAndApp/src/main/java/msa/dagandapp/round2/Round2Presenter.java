package msa.dagandapp.round2;

import javax.inject.Inject;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

public class Round2Presenter implements BasePresenter {

    private final int a;

    private final BaseView baseView;

    @Inject
    public Round2Presenter(int a, BaseView baseView) {
        this.a = a;
        this.baseView = baseView;
    }

    @Override
    public void load() {
        baseView.loadData(a);
    }
}
