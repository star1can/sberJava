package types;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import types.interfaces.Garage;
import types.interfaces.Vehicle;
import types.utils.TestContext;
import types.vehicle.Car;
import types.vehicle.Truck;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static types.utils.CarBrand.*;
import static types.utils.TestContext.createGarage;

public class GarageTest {

    private static TestContext context;

    @BeforeAll
    public static void createTestContext() {
        context = new TestContext();
    }

    @BeforeEach
    public void resetTestUtilState() {
        context.reset();
    }

    @Test
    public void addTest() {
        var owner = context.createOwner("Vahbet", "Vahbetov");
        var car = context.createRandomCar("superModel");

        var garage = createGarage();

        assertAll(
                () -> assertTrue(garage.addCar(car, owner)),
                () -> assertFalse(garage.addCar(car, owner))
        );
    }

    @Test
    public void allCarsOfBrandTest() {

        var owner = context.createOwner("Vahbet", "Vahbetov");

        var BMWs = Arrays.stream(new Car[]{
                context.createCar(BMW, "m3", 250, 250),
                context.createCar(BMW, "x7", 300, 250),
                context.createCar(BMW, "x5", 250, 625),
                context.createCar(BMW, "m4", 300, 320),
        }).toList();

        Garage<Car> BMWGarage = createGarage();

        BMWs.forEach(bmw -> BMWGarage.addCar(bmw, owner));

        var allBMWFromGarageSorted = BMWGarage
                .allCarsOfBrand(String.valueOf(BMW))
                .stream().sorted(Comparator.comparingLong(Car::id))
                .toList();

        assertEquals(BMWs.stream().toList(), allBMWFromGarageSorted);
    }

    @Test
    void allCarsOfOwnerTest() {
        var owners = Arrays.stream(new Owner[]{
                context.createOwner("Vahbet", "Vahbetov"),
                context.createOwner("Dmitrii", "Startsev"),
                context.createOwner("Mikhail", "Mikhailov")
        }).toList();

        List<Car> carList = new LinkedList<>();

        Garage<Car> garage = createGarage();
        IntStream.range(0, context.getRandomUInt() % 1000).forEach(i -> {
                    var car = context.createRandomCar("testModel");
                    carList.add(car);
                    garage.addCar(
                            car,
                            owners.get((int) car.ownerId())
                    );
                }
        );

        var randomOwner = owners.get((int) context.chooseRandomOwnerID());

        var expected = carList
                .stream()
                .filter(car -> car.ownerId() == randomOwner.getOwnerId())
                .toList();

        var result = garage
                .allCarsOfOwner(randomOwner)
                .stream().sorted(Comparator.comparingLong(Car::id))
                .toList();

        assertEquals(expected, result);
    }

    @Test
    public void allCarsUniqueOwners() {
        var owners = Arrays.stream(new Owner[]{
                context.createOwner("Vahbet", "Vahbetov"),
                context.createOwner("Dmitrii", "Startsev"),
                context.createOwner("Valerii", "Jmishenko"),
                context.createOwner("Vladimir", "Vladimirov"),
                context.createOwner("Mikhail", "Kosolapov"),
        }).toList();

        Garage<Car> BWMGarage = createGarage();

        HashSet<Owner> BMWOwners = new HashSet<>();
        IntStream.range(0, context.getRandomUInt() % 1000)
                .forEach(
                        i -> {
                            var bmw = context.createCar(BMW, "x5", 250, 625);
                            BWMGarage.addCar(
                                    bmw,
                                    owners.get((int) bmw.ownerId())
                            );
                            BMWOwners.add(owners.get((int) bmw.ownerId()));
                        }
                );


        var expected = BMWOwners.stream().sorted(Comparator.comparingLong(Owner::getOwnerId)).toList();

        var result = BWMGarage
                .allCarsUniqueOwners()
                .stream().sorted(Comparator.comparingLong(Owner::getOwnerId))
                .toList();

        assertEquals(expected, result);

    }

    @Test
    public void carsWithPowerMoreThanTest1() {
        var owner = context.createOwner("Vahbet", "Vahbetov");

        var cars = Arrays.stream(new Car[]{
                context.createCar(LADA, "granta", 180, 90),
                context.createCar(MERCEDES, "c63", 300, 500),
                context.createCar(BMW, "m5", 250, 625),
                context.createCar(FERRARI, "f8", 350, 725),
        }).toList();

        Garage<Car> garage = createGarage();

        cars.forEach(car -> garage.addCar(car, owner));

        var mustBeAllCars = garage
                .carsWithPowerMoreThan(
                        Collections
                                .min(cars, Comparator.comparingInt(Car::power))
                                .power() - 1
                )
                .stream().toList();

        assertEquals(cars, mustBeAllCars);
    }

    @Test
    public void carsWithPowerMoreThanTest2() {
        var owner = context.createOwner("Vahbet", "Vahbetov");

        var cars = Arrays.stream(new Car[]{
                context.createCar(LADA, "granta", 180, 90),
                context.createCar(MERCEDES, "c63", 300, 500),
                context.createCar(BMW, "m5", 250, 625),
                context.createCar(FERRARI, "f8", 350, 725),
        }).toList();

        Garage<Car> garage = createGarage();

        cars.forEach(car -> garage.addCar(car, owner));

        var mustBeEmpty = garage
                .carsWithPowerMoreThan(
                        Collections
                                .max(cars, Comparator.comparingInt(Car::power))
                                .power()
                );

        assertTrue(mustBeEmpty.isEmpty());
    }

    @Test
    public void carsWithPowerMoreThanTest3() {
        var owner = context.createOwner("Vahbet", "Vahbetov");

        var cars = Arrays.stream(new Car[]{
                context.createCar(MERCEDES, "c63", 300, 500),
                context.createCar(LADA, "granta", 180, 90),
                context.createCar(BMW, "m5", 250, 625),
                context.createCar(FERRARI, "f8", 350, 725),
        }).toList();

        Garage<Car> garage = createGarage();

        cars.forEach(car -> garage.addCar(car, owner));

        var result = garage
                .carsWithPowerMoreThan(cars.get(0).power())
                .stream().toList();

        assertEquals(cars.stream().skip(2).toList(), result);
    }

    @Test
    public void topThreeCarsByMaxVelocityTest() {

        var owner = context.createOwner("Vahbet", "Vahbetov");

        var cars = Arrays.stream(new Car[]{
                context.createCar(MERCEDES, "c63", 300, 500),
                context.createCar(LADA, "granta", 300, 90),
                context.createCar(BMW, "m5", 250, 625),
                context.createCar(FERRARI, "f8", 250, 725),
        }).toList();

        Garage<Car> garage = createGarage();
        cars.forEach(car -> garage.addCar(car, owner));

        var expected = cars.stream().sorted(Comparator.comparingLong(Car::id)).limit(3).toList();

        var result = garage.topThreeCarsByMaxVelocity();

        assertEquals(expected, result);
    }

    @Test
    public void meanCarNumberForEachOwnerTest() {
        var owners = Arrays.stream(new Owner[]{
                context.createOwner("Vahbet", "Vahbetov"),
                context.createOwner("Dmitrii", "Startsev"),
                context.createOwner("Valerii", "Jmishenko"),
        }).collect(Collectors.toMap(Owner::getOwnerId, Function.identity()));

        var cars = Arrays.stream(new Car[]{
                context.createCar(MERCEDES, "c63", 300, 500, 0, 4),
                context.createCar(LADA, "granta", 180, 90, 0, 4),
                context.createCar(BMW, "x5", 250, 625, 1, 4),
                context.createCar(FERRARI, "f8", 300, 666, 1, 4),
                context.createCar(SKODA, "octavia rs", 280, 252, 2, 4),
        }).toList();

        Garage<Car> garage = createGarage();

        cars.forEach(car -> garage.addCar(car, owners.get(car.ownerId())));


        assertEquals(2, garage.meanCarNumberForEachOwner());
    }

    @Test
    public void meanOwnersAgeOfCarBrandTest() {
        var owners = Arrays.stream(new Owner[]{
                context.createOwner("Vahbet", "Vahbetov", 54),
                context.createOwner("Dmitrii", "Startsev", 21),
                context.createOwner("Valerii", "Jmishenko", 27),
                context.createOwner("Vladimir", "Vladimirov", 63),
                context.createOwner("Mikhail", "Kosolapov", 33),
        }).toList();

        Garage<Car> mercedesGarage = createGarage();

        HashSet<Owner> mercedesOwners = new HashSet<>();
        IntStream.range(0, 10000)
                .forEach(
                        i -> {
                            var mercedes = context.createCar(MERCEDES, "e63", 250, 625);
                            mercedesOwners.add(owners.get((int) mercedes.ownerId()));
                            mercedesGarage.addCar(
                                    mercedes,
                                    owners.get((int) mercedes.ownerId())
                            );
                        }
                );

        int expected = (int) Math.round(
                mercedesOwners.stream()
                        .filter(mercedesOwners::contains)
                        .mapToInt(Owner::getAge)
                        .average().getAsDouble()
        );

        assertEquals(expected, mercedesGarage.meanOwnersAgeOfCarBrand(String.valueOf(MERCEDES)));
    }

    @Test
    public void removeCarTest() {
        var owner = context.createOwner("Vahbet", "Vahbetov");
        var car = context.createCar(BMW, "m3", 250, 250);

        Garage<Car> garage = createGarage();
        garage.addCar(car, owner);

        assertAll(
                () -> assertEquals(car, garage.removeCar(car)),
                () -> assertTrue(garage.allCarsOfBrand("BMW").isEmpty()),
                () -> assertTrue(garage.allCarsOfOwner(owner).isEmpty()),
                () -> assertTrue(garage.carsWithPowerMoreThan(car.power() - 1).isEmpty()),
                () -> assertTrue(garage.allCarsUniqueOwners().isEmpty()),
                () -> assertTrue(garage.topThreeCarsByMaxVelocity().isEmpty()),
                () -> assertEquals(0, garage.meanOwnersAgeOfCarBrand("BMW")),
                () -> assertEquals(0, garage.meanCarNumberForEachOwner())
        );
    }

    @Test
    public void addCarsTest() {
        var owners = Arrays.stream(new Owner[]{
                context.createOwner("Vahbet", "Vahbetov"),
                context.createOwner("Dmitrii", "Startsev"),
                context.createOwner("Valerii", "Jmishenko"),
        }).toList();

        var cars = Arrays.stream(new Car[]{
                context.createCar(MERCEDES, "c63", 300, 500, 0, 4),
                context.createCar(LADA, "granta", 180, 90, 0, 4),
                context.createCar(BMW, "x5", 250, 625, 1, 4),
        }).toList();

        Garage<Car> garage = createGarage();
        assertAll(
                () -> assertTrue(garage.addCars(cars, owners)),
                () -> assertFalse(garage.addCars(cars, owners))
        );
    }

    @Test
    public void removeCarsTest() {
        var owners = Arrays.stream(new Owner[]{
                context.createOwner("Vahbet", "Vahbetov"),
                context.createOwner("Dmitrii", "Startsev"),
                context.createOwner("Valerii", "Jmishenko"),
        }).toList();

        var cars = Arrays.stream(new Car[]{
                context.createCar(MERCEDES, "c63", 300, 500, 0, 4),
                context.createCar(LADA, "granta", 180, 90, 1, 4),
                context.createCar(BMW, "x5", 250, 625, 2, 4),
        }).toList();

        Garage<Car> garage = createGarage();
        garage.addCars(cars, owners);

        assertAll(
                () -> assertEquals(cars, garage.removeCars(cars))
        );
    }

    @Test
    public void upgradeVehiclesTest() {
        var owner = context.createOwner("Vahbet", "Vahbetov");
        var car = context.createCar(MERCEDES, "c63", 300, 500, 0, 4);

        Garage<Car> garage = createGarage();
        garage.addCar(car, owner);

        var trucks = garage.upgradeCars(new CarToTruckUpgrader());

        assertDoesNotThrow(() -> trucks.get(0));
    }

    @Test
    public void filterCarsTest() {
        var owners = Arrays.stream(new Owner[]{
                context.createOwner("Vahbet", "Vahbetov"),
                context.createOwner("Dmitrii", "Startsev"),
                context.createOwner("Valerii", "Jmishenko"),
        }).toList();

        var cars = Arrays.stream(new Car[]{
                context.createCar(MERCEDES, "c63", 300, 500, 0, 4),
                context.createCar(LADA, "granta", 180, 90, 0, 4),
                context.createCar(BMW, "x5", 250, 625, 1, 4),
        }).toList();

        Garage<Car> garage = createGarage();
        garage.addCars(cars, owners);

        var predicate = new Predicate<Vehicle>() {
            @Override
            public boolean test(Vehicle car) {
                return car.maxVelocity() > 180;
            }
        };

        var expected = cars.stream().filter(predicate).toList();
        var result = garage.filterCars(predicate).stream().sorted(Comparator.comparingLong(Car::id)).toList();

        assertEquals(expected, result);
    }
}
