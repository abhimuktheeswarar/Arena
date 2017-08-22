package msa.rehearsal.round2.subround2_1;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import java.util.List;

import butterknife.BindView;
import msa.domain.entities.Movie;
import msa.rehearsal.R;
import msa.rehearsal.base.BaseEpoxyHolder;

/**
 * Created by Abhimuktheeswarar on 01-06-2017.
 */

@EpoxyModelClass(layout = R.layout.item_movie_simple)
abstract class MovieItemTypeModel extends EpoxyModelWithHolder<MovieItemTypeModel.MovieItemHolder> {

    @EpoxyAttribute
    Movie movie;
    @EpoxyAttribute(hash = false)
    MovieItemTypeClickListener movieItemTypeClickListener;

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            movie.setFavorite(isChecked);
            if (movieItemTypeClickListener != null)
                movieItemTypeClickListener.onClickFavorite(movie);
        }
    };


    @Override
    public void bind(MovieItemHolder holder, List<Object> payloads) {
        super.bind(holder, payloads);
    }

    @Override
    public void bind(MovieItemHolder holder, EpoxyModel<?> previouslyBoundModel) {
        super.bind(holder, previouslyBoundModel);
    }

    @Override
    public void bind(MovieItemHolder holder) {
        super.bind(holder);
        holder.tV_MovieName.setText(movie.getMovieId());
        holder.checkBox.setChecked(movie.isFavorite());
        holder.checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        //holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public void unbind(MovieItemHolder holder) {
        super.unbind(holder);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.itemView.setOnClickListener(null);
    }

    @Override
    public boolean shouldSaveViewState() {
        return true;
    }

    interface MovieItemTypeClickListener {
        void onClickFavorite(Movie movie);
    }

    class MovieItemHolder extends BaseEpoxyHolder {

        @BindView(R.id.text_movie_name)
        TextView tV_MovieName;

        @BindView(R.id.checkbox_movie_favorite)
        CheckBox checkBox;

    }
}
