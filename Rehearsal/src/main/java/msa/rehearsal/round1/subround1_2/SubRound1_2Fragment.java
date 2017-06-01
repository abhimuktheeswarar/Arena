package msa.rehearsal.round1.subround1_2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import msa.rehearsal.R;
import msa.rehearsal.round1.subround1_2.datamodel.DataModel;
import msa.rehearsal.round1.subround1_2.model.Language;
import msa.rehearsal.round1.subround1_2.schedulers.SchedulerProvider;

/**
 * Created by Abhimuktheeswarar on 31-05-2017.
 */

public class SubRound1_2Fragment extends Fragment {

    @BindView(R.id.spinner_languages)
    Spinner spinner;

    @BindView(R.id.text_greeting)
    TextView textView;

    private SubRound1_2ViewModel subRound12ViewModel;

    private LanguageSpinnerAdapter languageSpinnerAdapter;

    private CompositeDisposable compositeDisposable;

    public static SubRound1_2Fragment newInstance() {
        return new SubRound1_2Fragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subRound12ViewModel = new SubRound1_2ViewModel(new DataModel(), SchedulerProvider.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_round1_subround2, container, false);
        ButterKnife.bind(this, rootView);
        setupViews();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        bind();

    }

    @Override
    public void onPause() {
        super.onPause();
        unBind();
    }

    private void setupViews() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                itemSelected(position);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                //nothing to do here
            }
        });
    }

    private void bind() {

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(subRound12ViewModel.getGreeting().subscribeOn(io.reactivex.schedulers.Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String greeting) throws Exception {
                        setGreeting(greeting);

                    }
                }));

        compositeDisposable.add(subRound12ViewModel.getSupportedLanguages().subscribeOn(io.reactivex.schedulers.Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Language>>() {
                    @Override
                    public void accept(@NonNull List<Language> languages) throws Exception {
                        setLanguages(languages);
                    }
                }));

    }

    private void unBind() {
        compositeDisposable.dispose();

    }

    private void setGreeting(@android.support.annotation.NonNull final String greeting) {
        textView.setText(greeting);
    }

    private void setLanguages(@android.support.annotation.NonNull final List<Language> languages) {
        languageSpinnerAdapter = new LanguageSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, languages);
        spinner.setAdapter(languageSpinnerAdapter);
    }

    private void itemSelected(final int position) {
        Language languageSelected = languageSpinnerAdapter.getItem(position);
        subRound12ViewModel.languageSelected(languageSelected);
    }

}
