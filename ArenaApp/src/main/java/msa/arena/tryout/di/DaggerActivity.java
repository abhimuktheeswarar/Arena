package msa.arena.tryout.di;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import msa.arena.R;
import msa.arena.tryout.di.car.DaggerVehicleComponent;
import msa.arena.tryout.di.car.Vehicle;
import msa.arena.tryout.di.car.VehicleComponent;
import msa.arena.tryout.di.car.VehicleModule;

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
