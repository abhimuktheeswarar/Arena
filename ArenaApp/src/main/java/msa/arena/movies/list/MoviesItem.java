package msa.arena.movies.list;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import msa.arena.R;

/**
 * Created by Abhimuktheeswarar on 01-05-2017.
 */
@EpoxyModelClass(layout = R.layout.item_movie)
public abstract class MoviesItem extends EpoxyModelWithHolder<MoviesHolder> {

    @EpoxyAttribute
    public String movieId, movieName;

    @Override
    public void bind(MoviesHolder holder) {
        super.bind(holder);
        holder.textView.setText(movieName);
    }

    @Override
    public void unbind(MoviesHolder holder) {
        super.unbind(holder);
  }
}
