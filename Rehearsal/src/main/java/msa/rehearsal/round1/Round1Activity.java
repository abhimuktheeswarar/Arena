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

package msa.rehearsal.round1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import msa.rehearsal.R;
import msa.rehearsal.round1.subround1_1.SubRound1_1Fragment;
import msa.rehearsal.round1.subround1_2.SubRound1_2Fragment;

public class Round1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round1);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SubRound1_1Fragment.newInstance(), SubRound1_1Fragment.class.getSimpleName()).commit();
    }

    @OnClick(R.id.fab)
    void onClickSwitchFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SubRound1_2Fragment.newInstance(), SubRound1_2Fragment.class.getSimpleName()).addToBackStack(SubRound1_2Fragment.class.getSimpleName()).commit();

    }

}
