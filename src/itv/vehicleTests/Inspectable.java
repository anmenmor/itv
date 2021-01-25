package itv.vehicleTests;

import itv.Vehicles.Vehicle;

public interface Inspectable {

    public void inspect(Vehicle vehicle);
    public boolean testPassed();

}
