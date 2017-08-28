package msa.arena.movies.search.searchmenu.itemmodel;

import android.view.View;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import msa.arena.R;
import msa.arena.movies.search.searchmenu.viewholder.SearchItemHolder;

/**
 * Created by Abhimuktheeswarar on 19-07-2017.
 */

@EpoxyModelClass(layout = R.layout.item_search)
public abstract class SearchItem extends EpoxyModelWithHolder<SearchItemHolder> {

    @EpoxyAttribute
    String movieId, movieName, queryText;
    @EpoxyAttribute(hash = false)
    SearchItemActionListener searchItemActionListener;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void bind(SearchItemHolder holder) {
        super.bind(holder);
        holder.textView.setText(movieName);
        holder.frameLayout.setOnClickListener(onClickListener);
    }

    @Override
    public void unbind(SearchItemHolder holder) {
        super.unbind(holder);
        holder.frameLayout.setOnClickListener(null);
    }

    public interface SearchItemActionListener {

    }
}
