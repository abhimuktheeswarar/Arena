package msa.arena.base;

import com.airbnb.epoxy.EpoxyAdapter;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import java.util.List;

/**
 * Created by Abhimuktheeswarar on 02-01-2017.
 */

public class BaseEpoxyAdapter<T extends EpoxyModelWithHolder<EpoxyHolder>> extends EpoxyAdapter {

    private static final String TAG = BaseEpoxyAdapter.class.getSimpleName();

    public BaseEpoxyAdapter() {
        enableDiffing();
    }

    public BaseEpoxyAdapter(boolean isDefault) {
        enableDiffing();
    }

    public void addItem(List<T> items) {
        addModels(items);
    }

    public void addItem(T item) {
        addModel(item);
    }

    public void updateItem(T item) {
        notifyModelChanged(item);
    }

    public void removeItem(T item) {
        removeModel(item);
    }

    public int getItemPosition(T item) {
        return getModelPosition(item);
    }

    public void removeAllItems() {
        removeAllModels();
    }

}
