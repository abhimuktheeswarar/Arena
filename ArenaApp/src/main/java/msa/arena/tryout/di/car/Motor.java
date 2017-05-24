package msa.arena.tryout.di.car;

/**
 * Created by Abhimuktheeswarar on 23-05-2017.
 */

class Motor {

    private int rpm;

    public Motor() {
        this.rpm = 0;
    }

    public int getRpm() {
        return rpm;
    }

    public void accelerate(int value) {
        rpm = rpm + value;
    }

    public void brake() {
        rpm = 0;
    }
}