package msa.arena.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import msa.data.repository.DataStoreFactory;

/**
 * Created by Abhimuktheeswarar on 05-09-2017.
 */

public class MockDataStoreFactory extends DataStoreFactory {

    public MockDataStoreFactory(@NonNull Context context) {
        super(context);
    }
}
