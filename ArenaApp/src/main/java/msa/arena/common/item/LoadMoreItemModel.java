package msa.arena.common.item;

import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import butterknife.BindView;
import msa.arena.R;
import msa.arena.base.BaseEpoxyHolder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Abhimuktheeswarar on 26-06-2017.
 */
@EpoxyModelClass(layout = R.layout.item_load_more)
public abstract class LoadMoreItemModel extends EpoxyModelWithHolder<LoadMoreItemModel.LoadMoreItemHolder> {

    @EpoxyAttribute
    boolean isFullHeight;

    @Override
    public void onViewAttachedToWindow(LoadMoreItemHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.SetItemHeight(isFullHeight);
    }

    @Override
    public void bind(LoadMoreItemHolder holder) {
        super.bind(holder);
    }

    @Override
    public void unbind(LoadMoreItemHolder holder) {
        super.unbind(holder);
    }

    class LoadMoreItemHolder extends BaseEpoxyHolder {

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        void SetItemHeight(boolean isFullHeight) {
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = isFullHeight ? MATCH_PARENT : WRAP_CONTENT;
            itemView.setLayoutParams(layoutParams);
        }
    }
}
