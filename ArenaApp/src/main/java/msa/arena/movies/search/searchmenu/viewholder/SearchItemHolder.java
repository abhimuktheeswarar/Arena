package msa.arena.movies.search.searchmenu.viewholder;

import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import msa.arena.R;
import msa.arena.base.BaseEpoxyHolder;

/**
 * Created by Abhimuktheeswarar on 19-07-2017.
 */

public class SearchItemHolder extends BaseEpoxyHolder {

    @BindView(R.id.frameLayout_searchItem)
    public FrameLayout frameLayout;

    @BindView(R.id.text_search)
    public TextView textView;
}
