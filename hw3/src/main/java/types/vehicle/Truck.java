package types.vehicle;

import types.interfaces.AbstractVehicle;

import java.util.Objects;

public class Truck extends AbstractVehicle {
    private final double maxCarrying;
    private final int axlesCount;

    public Truck(
            long vehicleId,
            String brand,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId,
            double maxCarrying,
            int axlesCount
    ) {
        super(vehicleId, brand, modelName, maxVelocity, power, ownerId);
        this.maxCarrying = maxCarrying;
        this.axlesCount = axlesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return this.id() == truck.id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id());
    }
}
