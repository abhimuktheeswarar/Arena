package msa.rehearsal.base;

import android.support.annotation.CallSuper;
import android.view.View;

import com.airbnb.epoxy.EpoxyHolder;

import butterknife.ButterKnife;

/**
 * Created by Abhimuktheeswarar on 11-03-2017.
 */
public abstract class BaseEpoxyHolder extends EpoxyHolder {

    public View itemView;

    @CallSuper
    @Override
    protected void bindView(View itemView) {
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }
}
