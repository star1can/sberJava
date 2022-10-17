package types.vehicle;

import types.interfaces.AbstractVehicle;

import java.util.Objects;

public class SuperCar extends AbstractVehicle {
    private final double accTime;

    public SuperCar(
            long vehicleId,
            String brand,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId,
            double accTime
    ) {
        super(vehicleId, brand, modelName, maxVelocity, power, ownerId);
        this.accTime = accTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperCar superCar = (SuperCar) o;
        return this.id() == superCar.id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id());
    }
}
