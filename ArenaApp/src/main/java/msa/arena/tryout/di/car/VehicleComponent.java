package msa.arena.tryout.di.car;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Abhimuktheeswarar on 23-05-2017.
 */

@Singleton
@Component(modules = {VehicleModule.class})
public interface VehicleComponent {

    Vehicle provideVehicle();

}