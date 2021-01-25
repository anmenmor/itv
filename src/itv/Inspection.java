package itv;

import itv.Vehicles.*;
import itv.vehicleTests.*;

import java.util.ArrayList;

public class Inspection {

    private Inspector inspector;
    private Vehicle inspectedVehicle;
    private ArrayList<Test> tests;
    private boolean favorable;

    public Inspection(Inspector inspector, Vehicle inspectedVehicle) {
        this.inspector = inspector;
        this.inspectedVehicle = inspectedVehicle;
        this.tests = new ArrayList<>();
        this.favorable = false;
    }

    public Vehicle getInspectedVehicle() {
        return inspectedVehicle;
    }
    public ArrayList<Test> getTests() {
        return tests;
    }

    public void addTest(Test test) {
        tests.add(test);
    }

    public void deleteTest(Test test) {
        tests.remove(test);
    }

    public void setResult() {
        LightTest foundedLightTest = null;
        WheelTest wheelFoundedTest = null;
        BrakeTest brakeFoundedTest = null;
        EmissionsTest emissionsFoundedTest = null;
        EmissionsAndGasAnalysisTest emAndGasFoundedTest = null;
        TacographTest tacographFoundedTest = null;
        for (Test test: tests) {
            if (test instanceof LightTest) {
                foundedLightTest = (LightTest) test;
            } else if (test instanceof WheelTest) {
                wheelFoundedTest = (WheelTest) test;
            } else if (test instanceof BrakeTest) {
                brakeFoundedTest = (BrakeTest) test;
            } else if (test instanceof EmissionsTest) {
                emissionsFoundedTest = (EmissionsTest) test;
            } else if (test instanceof EmissionsAndGasAnalysisTest) {
                emAndGasFoundedTest = (EmissionsAndGasAnalysisTest) test;
            } else if (test instanceof TacographTest) {
                tacographFoundedTest = (TacographTest) test;
            }
        }
        if (inspectedVehicle instanceof LightVehicle){
            if (inspectedVehicle.getMotorType().equals(Motor.DIESEL)) {
                if( checkBaseTests(foundedLightTest, wheelFoundedTest, brakeFoundedTest) &&
                        checkEmAndGasTest(emAndGasFoundedTest)) {
                    this.favorable = true;
                }
            } else if (inspectedVehicle.getMotorType().equals(Motor.ELECTRIC)) {
                if(checkBaseTests(foundedLightTest, wheelFoundedTest, brakeFoundedTest)) {
                    this.favorable = true;
                }
            } else {
                if(checkBaseTests(foundedLightTest, wheelFoundedTest, brakeFoundedTest) &&
                        checkEmissionsTest(emissionsFoundedTest)) {
                    this.favorable = true;
                }
            }
        } else if (inspectedVehicle instanceof HeavyVehicle || inspectedVehicle instanceof CategoryM2Vehicle) {
            if (inspectedVehicle.getMotorType().equals(Motor.DIESEL)) {
                if(checkBaseTests(foundedLightTest, wheelFoundedTest, brakeFoundedTest) &&
                        checkEmAndGasTest(emAndGasFoundedTest) && checkTacographTest(tacographFoundedTest)) {
                    this.favorable = true;
                }
            } else if (inspectedVehicle.getMotorType().equals(Motor.ELECTRIC)) {
                if(checkBaseTests(foundedLightTest, wheelFoundedTest, brakeFoundedTest) &&
                        checkTacographTest(tacographFoundedTest)) {
                    this.favorable = true;
                }
            } else {
                if(checkBaseTests(foundedLightTest, wheelFoundedTest, brakeFoundedTest) &&
                        checkEmissionsTest(emissionsFoundedTest) && checkTacographTest(tacographFoundedTest)) {
                    this.favorable = true;
                }
            }
        } else {
            System.out.println("El tipo de vehiculo no esta controlodo por el sistema");
        }

    }

    private boolean checkBaseTests(LightTest lightTest, WheelTest wheelTest, BrakeTest brakeTest) {
        return (lightTest != null && lightTest.isPassed()) &&
                (wheelTest != null && wheelTest.isPassed()) &&
                (brakeTest != null && brakeTest.isPassed());
    }

    private boolean checkEmAndGasTest(EmissionsAndGasAnalysisTest emissionsAndGasAnalysisTest) {
        return (emissionsAndGasAnalysisTest != null && emissionsAndGasAnalysisTest.isPassed());
    }

    private boolean checkEmissionsTest (EmissionsTest emissionsTest) {
        return (emissionsTest != null && emissionsTest.isPassed());
    }

    private boolean checkTacographTest (TacographTest tacographTest) {
        return (tacographTest != null && tacographTest.isPassed());
    }

    public boolean isFavorable() {
        return favorable;
    }
}
