package types.interfaces;

public interface VehicleUpgrader<From extends AbstractVehicle, To extends AbstractVehicle> {
    To upgrade(From vehicle);
}
