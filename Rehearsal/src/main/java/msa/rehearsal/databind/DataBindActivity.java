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
