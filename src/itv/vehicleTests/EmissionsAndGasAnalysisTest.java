package itv.vehicleTests;

import itv.Vehicles.Vehicle;

import java.util.Date;

public class EmissionsAndGasAnalysisTest extends Test implements Inspectable {
    public EmissionsAndGasAnalysisTest(Date testDate) {
        super(testDate);
    }

    @Override
    public void inspect(Vehicle vehicle) {
        // TODO Por defecto van a pasar todos los tests sin problemas ya que no tenemos información para saber
        // TODO cuando pasan y cuando no.
        setPassed(true);
    }

    @Override
    public boolean testPassed() {
        return super.isPassed();
    }
}
