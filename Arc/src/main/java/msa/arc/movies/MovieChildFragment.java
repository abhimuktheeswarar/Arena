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
