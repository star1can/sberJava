package types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;


@Getter
@RequiredArgsConstructor
public class Car {
    private final long carId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final long ownerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId);
    }
}

