package msa.data.repository;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@Singleton
public class DataStoreFac {

    private final Context context;

    @Inject
    public DataStoreFac(Context context) {
        this.context = context;
    }
}
