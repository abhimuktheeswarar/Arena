package msa.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Abhimuktheeswarar on 31-08-2017.
 */
@Singleton
public class ArenaRep {

    private final DataStoreFac dataStoreFac;

    @Inject
    public ArenaRep(DataStoreFac dataStoreFac) {
        this.dataStoreFac = dataStoreFac;
    }
}
