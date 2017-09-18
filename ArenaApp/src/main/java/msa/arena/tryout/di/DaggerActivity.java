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

package msa.arena.tryout.di;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import msa.arena.R;
import msa.arena.tryout.di.car.DaggerVehicleComponent;
import msa.arena.tryout.di.car.Vehicle;
import msa.arena.tryout.di.car.VehicleComponent;

public class DaggerActivity extends AppCompatActivity {

    @BindView(R.id.text_speed)
    TextView textView_Speed;

    Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);

        VehicleComponent v = DaggerVehicleComponent.create();

        vehicle = v.provideVehicle();
    }

    @OnClick(R.id.button_accelerate)
    void onPressAccelerate() {
        vehicle.increaseSpeed(10);
        textView_Speed.setText(String.valueOf(vehicle.getSpeed()));
    }
}
