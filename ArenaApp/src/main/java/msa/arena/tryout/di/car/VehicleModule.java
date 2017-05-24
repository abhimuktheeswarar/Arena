package msa.arena.tryout.di.car;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abhimuktheeswarar on 23-05-2017.
 */

@Module
public class VehicleModule {

    @Provides
    @Singleton
    Motor provideMotor() {
        return new Motor();
    }

    @Provides
    @Singleton
    Vehicle provideVehicle(Motor motor) {
        return new Vehicle(motor);
    }
}