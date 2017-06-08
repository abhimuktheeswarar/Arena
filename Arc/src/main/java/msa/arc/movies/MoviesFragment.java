package msa.arc.movies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import msa.arc.R;
import msa.arc.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends BaseFragment {

    @BindView(R.id.text_identifier)
    TextView textView;

    MoviesViewModel moviesViewModel;

    MovieChildFragment movieChildFragment;

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moviesViewModel = getViewModel(MoviesViewModel.class);
        textView.setText(String.valueOf(moviesViewModel.a));
    }

    @OnClick(R.id.button_add)
    void onClickAdd() {
        moviesViewModel.add();
        if (movieChildFragment == null) movieChildFragment = MovieChildFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, movieChildFragment, MovieChildFragment.class.getSimpleName()).commit();
    }

    @OnClick(R.id.button_remove)
    void onClickRemove() {
        if (getChildFragmentManager().findFragmentByTag(MovieChildFragment.class.getSimpleName()) != null) {
            getChildFragmentManager().beginTransaction().remove(getChildFragmentManager().findFragmentByTag(MovieChildFragment.class.getSimpleName())).commit();
            Toast.makeText(getContext(), "removed", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "not removed", Toast.LENGTH_SHORT).show();

    }
}
