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

package msa.rehearsal.databind;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import msa.rehearsal.R;
import msa.rehearsal.databinding.ActivityDataBindBinding;

public class DataBindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindBinding activityDataBindBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind);
        User user = new User("Abhi", "Muktheeswarar");
        activityDataBindBinding.setUser(user);
        activityDataBindBinding.setMyHandlers(new MyHandlers());
        activityDataBindBinding.setPresenter(new DataBindPresenter());
    }
}
