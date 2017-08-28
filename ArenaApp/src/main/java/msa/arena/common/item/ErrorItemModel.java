package msa.arena.common.item;

import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import butterknife.BindView;
import msa.arena.R;
import msa.arena.base.BaseEpoxyHolder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Abhimuktheeswarar on 26-08-2017.
 */
@EpoxyModelClass(layout = R.layout.item_error)
public abstract class ErrorItemModel extends EpoxyModelWithHolder<ErrorItemModel.ErrorItemHolder> {

    @EpoxyAttribute
    boolean isFullHeight;

    @EpoxyAttribute
    int errorType;

    @EpoxyAttribute
    String errorMessage;

    @Override
    public void onViewAttachedToWindow(ErrorItemHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.SetItemHeight(isFullHeight);
    }

    @Override
    public void bind(ErrorItemHolder holder) {
        super.bind(holder);
        holder.textView.setText(errorMessage != null ? errorMessage : "Something went wrong");
    }

    class ErrorItemHolder extends BaseEpoxyHolder {

        @BindView(R.id.text_error)
        TextView textView;

        void SetItemHeight(boolean isFullHeight) {
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = isFullHeight ? MATCH_PARENT : WRAP_CONTENT;
            itemView.setLayoutParams(layoutParams);
        }

    }
}
