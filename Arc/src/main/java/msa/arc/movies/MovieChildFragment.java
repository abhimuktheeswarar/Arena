package msa.arc.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import msa.arc.R;
import msa.arc.base.BaseFragment;

/**
 * Created by Abhimuktheeswarar on 08-06-2017.
 */

public class MovieChildFragment extends BaseFragment {

    @BindView(R.id.text_identifier)
    TextView textView;

    MovieChildViewModel movieChildViewModel;

    public static MovieChildFragment newInstance() {
        return new MovieChildFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_child, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieChildViewModel = getViewModel(MovieChildViewModel.class);
        textView.setText(movieChildViewModel.getName());

    }

    @OnClick(R.id.button_add)
    void onClickAdd() {
        movieChildViewModel.setI();
        textView.setText(String.valueOf(movieChildViewModel.getI()));
    }
}
