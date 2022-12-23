package org.startsevds;

import org.startsevds.garage.types.Car;
import org.startsevds.garage.types.GarageImpl;
import org.startsevds.garage.types.Owner;
import org.startsevds.garage.types.interfaces.Garage;
import org.startsevds.mercedes.MercedesCreator;
import org.startsevds.report.XLSReportGenerator;
import org.startsevds.report.interfaces.Report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    private static Report generateAnalytics(List<Car> entities, String name) {
        return new XLSReportGenerator<Car>().generate(entities, name);
    }

    private static void fillGarageWithMercedes(Owner owner, Garage garage, int carCount) {
        IntStream
                .range(0, carCount)
                .mapToObj(i -> MercedesCreator.createMercedes(owner.getOwnerId()))
                .forEach(car -> garage.addCar(car, owner));
    }

    public static void main(String[] args) throws IOException {
        Garage mercedesGarage = new GarageImpl();
        Random random = new Random();

        var owner = new Owner(1, "Valerii", "Jmishenko", 54);
        fillGarageWithMercedes(owner, mercedesGarage, random.nextInt(0, 4222));

        int randomPower = random.nextInt(300, 500);
        String reportName = "Cars with power more than " + randomPower;

        var analytics = generateAnalytics(
                mercedesGarage.carsWithPowerMoreThan(randomPower)
                        .stream()
                        .toList()
                , reportName
        );

        try (FileOutputStream f = new FileOutputStream("garageAnalytics.xls");) {
            analytics.writeTo(f);
        }
    }
}
