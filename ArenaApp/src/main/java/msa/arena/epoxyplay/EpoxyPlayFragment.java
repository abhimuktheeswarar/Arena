package msa.arena.epoxyplay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import msa.arena.R;
import msa.arena.base.BaseFragment;
import msa.arena.utilities.EndlessRecyclerViewScrollListener;

/**
 * Created by Abhimuktheeswarar on 30-05-2017.
 */

public class EpoxyPlayFragment extends BaseFragment {


    @BindView(R.id.recycler_movies)
    RecyclerView recyclerView_Movies;

    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_epoxyplay, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_Movies.setLayoutManager(linearLayoutManager);
    }
}
