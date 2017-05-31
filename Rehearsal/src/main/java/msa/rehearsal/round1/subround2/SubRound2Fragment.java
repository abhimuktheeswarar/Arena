package msa.rehearsal.round1.subround2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import msa.rehearsal.R;

/**
 * Created by Abhimuktheeswarar on 31-05-2017.
 */

public class SubRound2Fragment extends Fragment {

    public static SubRound2Fragment newInstance() {
        return new SubRound2Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_round2, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
