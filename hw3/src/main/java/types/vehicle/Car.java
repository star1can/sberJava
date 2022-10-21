package types.vehicle;

import lombok.Getter;
import types.interfaces.AbstractVehicle;

import java.util.Objects;

@Getter
public class Car extends AbstractVehicle {
    private final int doorsCount;

    public Car(
            long vehicleId,
            String brand,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId,
            int doorsCount
    ) {
        super(vehicleId, brand, modelName, maxVelocity, power, ownerId);
        this.doorsCount = doorsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return this.id() == car.id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id());
    }
}


