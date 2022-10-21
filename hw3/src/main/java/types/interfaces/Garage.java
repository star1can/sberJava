package types.interfaces;

import types.Owner;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface Garage<T extends AbstractVehicle> {

    Collection<Owner> allCarsUniqueOwners();

    /**
     * Complexity should be less than O(n)
     */
    Collection<T> topThreeCarsByMaxVelocity();

    /**
     * Complexity should be O(1)
     */
    Collection<T> allCarsOfBrand(String brand);

    /**
     * Complexity should be less than O(n)
     */
    Collection<T>  carsWithPowerMoreThan(int power);

    /**
     * Complexity should be O(1)
     */
    Collection<T> allCarsOfOwner(Owner owner);

    /**
     * @return mean value of owner age that has cars with given brand
     */
    int meanOwnersAgeOfCarBrand(String brand);

    /**
     * @return mean value of cars for all owners
     */
    int meanCarNumberForEachOwner();

    /**
     * Complexity should be less than O(n)
     *
     * @return removed car
     */
    T removeCar(Object car);

    Collection<T> removeCars(List<? extends T> cars);

    /**
     * Complexity should be less than O(n)
     */
    boolean addCar(T car, Owner owner);

    boolean addCars(List<? extends T> cars, List<Owner> owner);

    List<T> filterCars(Predicate<? super T> predicate);

    <To extends AbstractVehicle> List<To> upgradeCars(VehicleUpgrader<T, To> upgrader);
}

