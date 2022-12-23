package org.startsevds.garage.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.startsevds.report.annotations.ColumnName;

import java.util.Objects;


@Getter
@RequiredArgsConstructor
public class Car {
    @ColumnName(name = "VIN")
    private final long carId;

    @ColumnName(name = "марка")
    private final String brand;

    @ColumnName(name = "модель")
    private final String modelName;

    @ColumnName(name = "макс. скорость, км/ч")
    private final int maxVelocity;

    @ColumnName(name = "мощность, л/с")
    private final int power;

    @ColumnName(name = "ID владельца")
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

