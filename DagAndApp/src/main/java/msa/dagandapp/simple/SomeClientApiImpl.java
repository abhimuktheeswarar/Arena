package msa.dagandapp.simple;

/**
 * Created by Abhimuktheeswarar on 24-05-2017.
 */

public class SomeClientApiImpl implements SomeClientApi {

    private final int a;

    public SomeClientApiImpl(int a) {
        this.a = a;
    }

    @Override
    public int getA() {
        return a;
    }
}
