package msa.arena.movies.search.searchmenu.itemmodel;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import msa.arena.R;
import msa.arena.movies.search.searchmenu.viewholder.SearchErrorItemHolder;

/**
 * Created by Abhimuktheeswarar on 19-07-2017.
 */

@EpoxyModelClass(layout = R.layout.item_search_error)
public abstract class SearchErrorItem extends EpoxyModelWithHolder<SearchErrorItemHolder> {

    @EpoxyAttribute
    String errorMessage;

    @Override
    public void bind(SearchErrorItemHolder holder) {
        super.bind(holder);
        holder.textView.setText(errorMessage);
    }
}
