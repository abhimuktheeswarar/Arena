package msa.arena.tryout.di.car;

import javax.inject.Inject;

/**
 * Created by Abhimuktheeswarar on 23-05-2017.
 */

public class Vehicle {

    private Motor motor;

    @Inject
    public Vehicle(Motor motor) {
        this.motor = motor;
    }

    public void increaseSpeed(int value) {
        motor.accelerate(value);
    }

    public void stop() {
        motor.brake();
    }

    public int getSpeed() {
        return motor.getRpm();
    }
}