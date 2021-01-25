package itv;

import itv.Vehicles.*;
import itv.vehicleTests.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ItvProgram {
    private ArrayList<Inspector> inspectors;
    private ArrayList<Inspection> inspections;

    public ItvProgram() {
        inspectors = new ArrayList<>();
        inspections = new ArrayList<>();
    }

    public void showMenu() {
        int action = -1;
        Scanner input = new Scanner(System.in);
        do {
            do {
                System.out.println("Indica que acción quieres realizar: 1. añadir inspector. 2. Comenzar inspección. 3. Salir");
                if (input.hasNextInt()) {
                    action = input.nextInt();
                    input.nextLine();
                } else {
                    input.nextLine();
                    System.out.println("Introduce un número");
                }
            } while (action < 1 || action > 3);
            switch (action) {
                case 1:
                    addInspector();
                    break;
                case 2:
                    if (inspectors.isEmpty()) {
                        System.out.println("No hay inspectores disponibles");
                    } else {
                        startInspection();
                    }
                    break;
            }
        } while (action != 3);
    }

    private void addInspector() {
        int inspectorCode = writeInspectorCode();
        Inspector foundInspector = null;
        for (Inspector element: inspectors) {
            if (element.getInspectorCode() == inspectorCode) {
                foundInspector = element;
            }
        }
        if (foundInspector == null) {
            Inspector inspector = new Inspector(inspectorCode);
            inspectors.add(inspector);
        } else {
            System.out.println("Este inspector ya esta en la lista");
        }
    }

    private Inspector selectInspector() {
        Inspector foundInspector = null;
        do {
            int inspectorCode = writeInspectorCode();
            for (Inspector inspector : inspectors) {
                if (inspector.getInspectorCode() == inspectorCode) {
                    foundInspector = inspector;
                }
            }
            if (foundInspector == null) {
                System.out.println("El inspector selecionado no existe");
            }
        } while (foundInspector == null);
        return foundInspector;
    }

    private int writeInspectorCode() {
        Scanner input = new Scanner(System.in);
        int inspectorCode = -1;
        do {
            System.out.println("Introduce el código de inspector");
            if (input.hasNextInt()) {
                inspectorCode = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine();
                System.out.println("Introduce un número");
            }
        } while (inspectorCode < 1);
        return inspectorCode;
    }

    private void startInspection() {
        Inspector inspectorSelected = selectInspector();
        String carRegistration = writeCarRegistration();
        if (checkDocumentation(carRegistration)) {
            Inspection inspection = selectInspection(carRegistration);
            if (inspection == null) {
                Vehicle vehicle = createVehicle(carRegistration);
                inspection = new Inspection(inspectorSelected, vehicle);
                inspections.add(inspection);
            }
            selectTestMenu(inspection);
            inspection.setResult();
            printReport(inspection);
            if (inspection.isFavorable()) {
                printSticker();
                updateTrafficDB(inspection);
            }

        } else {
            System.out.println("La documentacion no es valida");
        }
    }

    private void updateTrafficDB(Inspection inspection) {
        //TODO actualizar la BBDD de trafico con los resultados de la inspeccion favorable
    }

    private void printSticker() {
        //TODO se imprime la pegatina para poner en el vehiculo
    }

    private void printReport(Inspection inspection) {
        // TODO Se encarga de imprimir el informe para el cliente con los resultados de la inspeccion
    }

    private Vehicle createVehicle(String carRegistration) {
        Motor motorType = writeMotorType();
        Scanner input = new Scanner(System.in);
        Vehicle vehicle = null;
        int vehicleType = -1;
        do {
            System.out.println("Introduce el tipo de vehiculo: 1. Ligero, 2. Pesado, 3. Categoría M2 (+ de 9 plazas)");
            if (input.hasNextInt()) {
                vehicleType = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine();
                System.out.println("Introduce un número");
            }
        } while (vehicleType < 1 || vehicleType > 3);
        switch (vehicleType) {
            case 1:
                vehicle = new LightVehicle(carRegistration, motorType);
                break;
            case 2:
                vehicle = new HeavyVehicle(carRegistration, motorType);
                break;
            case 3:
                vehicle = new CategoryM2Vehicle(carRegistration, motorType);
                break;
        }
        return vehicle;
    }

    private Motor writeMotorType() {
        Scanner input = new Scanner(System.in);
        int motorType = -1;
        Motor motor = null;
        do {
            System.out.println("Introduce el tipo de motor: 1. Gasolina, 2. Diesel, 3. Gas, 4. Electrico");
            if (input.hasNextInt()) {
                motorType = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine();
                System.out.println("Introduce un número");
            }
        } while (motorType < 1 || motorType > 5);
        switch (motorType) {
            case 1:
                motor = Motor.GASOLINE;
                break;
            case 2:
                motor = Motor.DIESEL;
                break;
            case 3:
                motor =  Motor.GAS;
                break;
            case 4:
                motor = Motor.ELECTRIC;
                break;
        }
        return motor;
    }

    private String writeCarRegistration() {
        Scanner input = new Scanner(System.in);
        System.out.println("Escribe la matricula del coche");
        return input.nextLine();
    }

    private boolean checkDocumentation(String carRegistration) {
        //TODO comprobariamos si la documentacion esta en regla o no en funcion de la matricula.
        // Por defecto para que el programa pueda continuar devolvemos true
        return true;
    }

    private Inspection selectInspection (String carRegistration) {
        Inspection foundInspection = null;
        for (Inspection inspection : inspections) {
            if (inspection.getInspectedVehicle().getCarRegistration().equals(carRegistration)) {
                foundInspection = inspection;
            }
        }
        return foundInspection;
    }

    private void selectTestMenu(Inspection inspection) {
        int selectedTest = -1;
        Scanner input = new Scanner(System.in);
        do {
            do {
                System.out.println("Indica que test quieres realizar: 1. Test de luces, 2. Test de neumaticos." +
                        "3. Test de frenos, 4. Test de emisiones, 5. Test de Tacografo, 6.Salir");
                if (input.hasNextInt()) {
                    selectedTest = input.nextInt();
                    input.nextLine();
                } else {
                    input.nextLine();
                    System.out.println("Introduce un número");
                }
            } while (selectedTest < 1 || selectedTest > 6);
            Date date = Calendar.getInstance().getTime();
            switch (selectedTest) {
                case 1:
                    LightTest lightFoundedTest = null;
                    for (Test test: inspection.getTests()) {
                        if (test instanceof LightTest) {
                            lightFoundedTest = (LightTest) test;
                        }
                    }
                    if (lightFoundedTest == null || !lightFoundedTest.isPassed()) {
                        LightTest lightTest = new LightTest(date);
                        lightTest.inspect(inspection.getInspectedVehicle());
                        inspection.addTest(lightTest);
                        System.out.println("Test realizado");
                        if(lightFoundedTest != null) {
                            inspection.deleteTest(lightFoundedTest);
                        }
                    } else {
                        System.out.println("Este test ya se ha pasado");
                    }
                    break;
                case 2:
                    WheelTest wheelFoundedTest = null;
                    for (Test test: inspection.getTests()) {
                        if (test instanceof WheelTest) {
                            wheelFoundedTest = (WheelTest) test;
                        }
                    }
                    if (wheelFoundedTest == null || !wheelFoundedTest.isPassed()) {
                        WheelTest wheelTest = new WheelTest(date);
                        wheelTest.inspect(inspection.getInspectedVehicle());
                        inspection.addTest(wheelTest);
                        System.out.println("Test realizado");
                        if(wheelFoundedTest != null) {
                            inspection.deleteTest(wheelFoundedTest);
                        }
                    } else {
                        System.out.println("Este test ya se ha pasado");
                    }
                    break;
                case 3:
                    BrakeTest brakeFoundedTest = null;
                    for (Test test: inspection.getTests()) {
                        if (test instanceof BrakeTest) {
                            brakeFoundedTest = (BrakeTest) test;
                        }
                    }
                    if (brakeFoundedTest == null || !brakeFoundedTest.isPassed()) {
                        BrakeTest brakeTest = new BrakeTest(date);
                        brakeTest.inspect(inspection.getInspectedVehicle());
                        inspection.addTest(brakeTest);
                        System.out.println("Test realizado");
                        if(brakeFoundedTest != null) {
                            inspection.deleteTest(brakeFoundedTest);
                        }
                    } else {
                        System.out.println("Este test ya se ha pasado");
                    }
                    break;
                case 4:
                    Motor motorType = inspection.getInspectedVehicle().getMotorType();
                    if (motorType.equals(Motor.DIESEL)) {
                        EmissionsAndGasAnalysisTest emAndGasfoundedTest = null;
                        for (Test test: inspection.getTests()) {
                            if (test instanceof EmissionsAndGasAnalysisTest) {
                                emAndGasfoundedTest = (EmissionsAndGasAnalysisTest) test;
                            }
                        }
                        if ( emAndGasfoundedTest == null || !emAndGasfoundedTest.isPassed()) {
                            EmissionsAndGasAnalysisTest emissionsAndGasAnalysisTest = new EmissionsAndGasAnalysisTest(date);
                            emissionsAndGasAnalysisTest.inspect(inspection.getInspectedVehicle());
                            inspection.addTest(emissionsAndGasAnalysisTest);
                            System.out.println("Test realizado");
                            if(emAndGasfoundedTest != null) {
                                inspection.deleteTest(emAndGasfoundedTest);
                            }
                        } else {
                            System.out.println("Este test ya se ha pasado");
                        }
                    } else if (motorType.equals(Motor.ELECTRIC)) {
                        System.out.println("No debe realizar este test");
                    }else {
                        EmissionsTest emissionsFoundedTest = null;
                        for (Test test: inspection.getTests()) {
                            if (test instanceof EmissionsTest) {
                                emissionsFoundedTest = (EmissionsTest) test;
                            }
                        }
                        if (emissionsFoundedTest == null || !emissionsFoundedTest.isPassed()) {
                            EmissionsTest emissionsTest = new EmissionsTest(date);
                            emissionsTest.inspect(inspection.getInspectedVehicle());
                            inspection.addTest(emissionsTest);
                            System.out.println("Test realizado");
                            if(emissionsFoundedTest != null) {
                                inspection.deleteTest(emissionsFoundedTest);
                            }
                        } else {
                            System.out.println("Este test ya se ha pasado");
                        }
                    }
                    break;
                case 5:
                    if (inspection.getInspectedVehicle() instanceof HeavyVehicle
                            || inspection.getInspectedVehicle() instanceof CategoryM2Vehicle) {
                        TacographTest tacograhFoundedTest = null;
                        for (Test test: inspection.getTests()) {
                            if (test instanceof TacographTest) {
                                tacograhFoundedTest = (TacographTest) test;
                            }
                        }
                        if ( tacograhFoundedTest == null || !tacograhFoundedTest.isPassed()) {
                            TacographTest tacographTest = new TacographTest(date);
                            tacographTest.inspect(inspection.getInspectedVehicle());
                            inspection.addTest(tacographTest);
                            System.out.println("Test realizado");
                            if(tacograhFoundedTest != null) {
                                inspection.deleteTest(tacograhFoundedTest);
                            }
                        } else {
                            System.out.println("Este test ya se ha pasado");
                        }
                    } else {
                        System.out.println("No debe realizar este test");
                    }
                    break;
            }
        } while (selectedTest != 6);
    }
}
