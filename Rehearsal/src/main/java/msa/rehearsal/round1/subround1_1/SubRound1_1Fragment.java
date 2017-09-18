/*
 * Copyright 2017, Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package msa.rehearsal.round1.subround1_1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import msa.rehearsal.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SubRound1_1Fragment extends Fragment {

    @BindView(R.id.edit_1)
    EditText editText;

    @BindView(R.id.text_1)
    TextView textView;
    SubRound1_1ViewModel subRound11ViewModel;
    @android.support.annotation.NonNull
    private CompositeDisposable compositeDisposable;


    public static SubRound1_1Fragment newInstance() {
        return new SubRound1_1Fragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subRound11ViewModel = new SubRound1_1ViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_round1_subround1, container, false);
        ButterKnife.bind(this, rootView);
        setupViews();
        return rootView;
    }

    private void setupViews() {


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

    private void bind() {

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(RxTextView.afterTextChangeEvents(editText).map(new Function<TextViewAfterTextChangeEvent, String>() {
            @Override
            public String apply(@NonNull TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                return textViewAfterTextChangeEvent.editable().toString();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                sendMsg(s);
            }
        }));

        compositeDisposable.add(subRound11ViewModel.getMsg().subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                setText(s);
            }
        }));
    }

    private void unBind() {
        compositeDisposable.dispose();
    }

    private void sendMsg(String msg) {
        subRound11ViewModel.setMsg(msg);
    }

    private void setText(String msg) {
        assert msg != null;
        textView.setText(msg);
    }
}
