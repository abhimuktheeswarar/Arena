package msa.arc.movies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import msa.arc.R;
import msa.arc.base.BaseFragment;
import msa.domain.entities.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends BaseFragment {

    @BindView(R.id.text_identifier)
    TextView textView;

    @BindView(R.id.text_name)
    TextView textView_Name;

    @BindView(R.id.edit_name)
    EditText editText_Name;

    MoviesViewModel moviesViewModel;

    MovieChildFragment movieChildFragment;

    CompositeDisposable compositeDisposable;

    Alerter alerter;

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
        alerter = Alerter.create(getActivity())
                .setTitle("No internet connectivity")
                .setBackgroundColor(R.color.colorAccent)
                .enableInfiniteDuration(true)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "OnClick Called", Toast.LENGTH_LONG).show();
                    }
                });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moviesViewModel = getViewModel(MoviesViewModel.class);
        textView.setText(String.valueOf(moviesViewModel.a));

        listenForInternetConnectivity().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                Log.d(MoviesFragment.class.getSimpleName(), "network change detected");

            }
        });
    }

    @OnClick(R.id.button_add)
    void onClickAdd() {
        moviesViewModel.add();
        if (movieChildFragment == null) movieChildFragment = MovieChildFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, movieChildFragment, MovieChildFragment.class.getSimpleName()).commit();

        moviesViewModel.getUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(@NonNull User user) throws Exception {
                textView.setText(user.getDisplayName());

            }
        });

        alerter = Alerter.create(getActivity())
                .setTitle("No internet connectivity")
                .setBackgroundColor(R.color.colorAccent)
                .enableInfiniteDuration(true)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "OnClick Called", Toast.LENGTH_LONG).show();
                    }
                });
        alerter.show();
    }

    @OnClick(R.id.button_remove)
    void onClickRemove() {
        if (getChildFragmentManager().findFragmentByTag(MovieChildFragment.class.getSimpleName()) != null) {
            getChildFragmentManager().beginTransaction().remove(getChildFragmentManager().findFragmentByTag(MovieChildFragment.class.getSimpleName())).commit();
            Toast.makeText(getContext(), "removed", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "not removed", Toast.LENGTH_SHORT).show();
        alerter.hide();

    }

    @OnClick(R.id.button_update)
    void onClickUpdate() {
        if (editText_Name.getText().toString() != null && editText_Name.getText().toString().length() > 0)
            compositeDisposable.add(moviesViewModel.updateUser(editText_Name.getText().toString()).subscribe());
        else Toast.makeText(getContext(), "Invalid user name", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        compositeDisposable = new CompositeDisposable();
        bind();
    }

    private void bind() {
        compositeDisposable.add(moviesViewModel.getUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(@NonNull User user) throws Exception {
                textView_Name.setText(user.getDisplayName());

            }
        }));
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
