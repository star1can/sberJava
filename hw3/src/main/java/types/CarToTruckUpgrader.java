package types;

import types.interfaces.Vehicle;
import types.interfaces.VehicleUpgrader;
import types.vehicle.Car;
import types.vehicle.Truck;

public class CarToTruckUpgrader implements VehicleUpgrader<Car, Truck> {
    @Override
    public Truck upgrade(Car car) {
        return new Truck(
                car.id(),
                car.brand(),
                car.modelName(),
                car.maxVelocity(),
                car.power(),
                car.ownerId(),
                6.0,
                4
        );
    }
}
