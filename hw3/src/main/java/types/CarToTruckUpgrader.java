package types;

import types.interfaces.Vehicle;
import types.interfaces.VehicleUpgrader;
import types.vehicle.Car;
import types.vehicle.Truck;

public class CarToTruckUpgrader implements VehicleUpgrader {
    @Override
    public Object upgrade(Object vehicle) {
        Vehicle car = (Vehicle) vehicle;
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
