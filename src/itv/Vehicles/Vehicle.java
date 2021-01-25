package itv.Vehicles;

public class Vehicle {

    private String carRegistration;
    private Motor motorType;
    private int weigth;
    private int numberOfSeats;

    public Vehicle(String carRegistration, Motor motorType, int weigth, int numberOfSeats) {
        this.carRegistration = carRegistration;
        this.motorType = motorType;
        this.weigth = weigth;
        this.numberOfSeats = numberOfSeats;
    }

    public String getCarRegistration() {
        return carRegistration;
    }

    public Motor getMotorType() {
        return motorType;
    }
}
