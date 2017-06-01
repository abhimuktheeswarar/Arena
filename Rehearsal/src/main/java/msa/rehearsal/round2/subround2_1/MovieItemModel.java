package msa.rehearsal.round2.subround2_1;

import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import butterknife.BindView;
import msa.rehearsal.R;
import msa.rehearsal.base.BaseEpoxyHolder;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

@EpoxyModelClass(layout = R.layout.item_movie_simple)
abstract class MovieItemModel extends EpoxyModelWithHolder<MovieItemModel.MovieItemHolder> {

    @EpoxyAttribute
    String movieId;
    @EpoxyAttribute
    String movieName;

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener onClickListener;

    @Override
    public void bind(MovieItemHolder holder) {
        super.bind(holder);
        holder.tV_MovieName.setText(movieName);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public void unbind(MovieItemHolder holder) {
        super.unbind(holder);
        holder.itemView.setOnClickListener(null);
    }

    class MovieItemHolder extends BaseEpoxyHolder {

        @BindView(R.id.text_movie_name)
        TextView tV_MovieName;

    }
}
