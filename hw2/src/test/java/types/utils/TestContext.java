package types.utils;

import types.Car;
import types.GarageImpl;
import types.Owner;
import types.interfaces.Garage;

import java.util.Random;

public class TestContext {
    private long currentOwnerID;
    private long currentCarID;
    private final Random random;

    public TestContext() {
        this.random = new Random();
    }

    public long getNextOwnerID() {
        return currentOwnerID++;
    }

    public long getNextCarID() {
        return currentCarID++;
    }

    public long chooseRandomOwnerID() {
        return random.nextLong(0, currentOwnerID);
    }

    public void reset() {
        currentOwnerID = 0;
        currentCarID = 0;
    }

    public Car createCar(
            CarBrand brand,
            String modelName,
            int maxVelocity,
            int power
    ) {
        return createCar(brand, modelName, maxVelocity, power, chooseRandomOwnerID());
    }

    public Car createCar(
            CarBrand brand,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId
    ) {
        return brand.createCar(getNextCarID(), modelName, maxVelocity, power, ownerId);
    }

    public Owner createOwner(
            String name,
            String lastName
    ) {
        return createOwner(name, lastName, getRandomUInt());
    }

    public Owner createOwner(
            String name,
            String lastName,
            int age
    ) {
        return new Owner(getNextOwnerID(), name, lastName, age);
    }

    public Car createRandomCar(String modelName) {
        return getRandomBrand().createCar(getNextCarID(), modelName, getRandomUInt(), getRandomUInt(), chooseRandomOwnerID());
    }

    public static Garage createGarage() {
        return new GarageImpl();
    }

    public int getRandomUInt() {
        return random.nextInt(0, Integer.MAX_VALUE);
    }

    private CarBrand getRandomBrand() {
        return CarBrand.getRandomCarBrand();
    }
}
