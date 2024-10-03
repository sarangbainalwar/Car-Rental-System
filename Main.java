import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carID;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carID,String brand,String model,double basePricePerDay){
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId(){
        return carID;
    }

    public String getModel(){
        return model;
    }

    public double claculateprice(int rentaldays){
          return basePricePerDay * rentaldays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returncar(){
        isAvailable = true;
    }

    public String getBrand() {
        return brand;
    }
}

class customer {
    private String customerID;
    private String name;

    public customer(String customerID, String name){
        this.customerID = customerID;
        this.name = name;
    }

    public String getCustomerId(){
        return customerID;
    }

    public String getName(){
        return name;
    }
}

class Rental {
    private Car car;

    private customer customer;
    private int days;

    public Rental(Car car, customer customer,int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }
    public customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car,customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car){
        car.returncar();
        Rental rentalToRemove = null;
        for(Rental rental : rentals){
            if(rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.println("Car return successfully");
        } else {
            System.out.println("Car is not rented by this customer.");
        }
    }
    public void menu(){
       Scanner sc = new Scanner(System.in);
       
       while(true){
        System.out.println("==== Car rental services ====");
        System.out.println("1. Rent a car");
        System.out.println("2. Return a car");
        System.out.println("3. Exit");
        System.out.println("enter your choice");

        int choice = sc.nextInt();
        sc.nextLine();

        if(choice == 1){
            System.out.println("\n == Rent a car ==\n");
            System.out.println("Enter your name: ");
            String  customerName = sc.nextLine();

            System.out.println("\n Available cars: ");
            for(Car car : cars ){
                if(car.isAvailable()){
                    System.out.println(car.getCarId() + " - " + " - " +car.getBrand() + car.getModel());
                }
            }

            System.out.println("\nEnter the car ID you want to rent: ");
            String carID = sc.nextLine();

            System.out.println("Enter the number of days for rental: ");
            int DaysforRental = sc.nextInt();
            sc.nextLine();

            customer newCustomer = new customer("CUS"+(customers.size()+1), customerName);
            addCustomer(newCustomer);

            Car selectedCar = null;
            for(Car car : cars) {
                if(car.getCarId().equals(carID) && car.isAvailable()) {
                    selectedCar = car;
                    break;
                }
            }

            if(selectedCar != null){
                double totalPrice = selectedCar.claculateprice(DaysforRental);
                System.out.println("\n == Rental Information ==\n");
                System.out.println("Customer ID: " + newCustomer.getCustomerId());
                System.out.println("Customer Name: " + newCustomer.getName());
                System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                System.out.println("Rental Days: " + DaysforRental);
                System.out.printf("Total price: $%.2f%n", totalPrice);

                System.out.println("\n Confirm rental? (Y/N): ");
                String confirm = sc.nextLine();

                if(confirm.equalsIgnoreCase("Y")) {
                    rentCar(selectedCar, newCustomer, DaysforRental);
                    System.out.println("\n Car rented successfully.");
                } else {
                    System.out.println("\nRental calceled.");
                }
            } else {
                System.out.println("\nInvalid car selection or car not available for rent");
            }
        } else if (choice == 2) {
            System.out.println("\n == Return a Car ==\n ");
            System.out.println("Enter the car ID you want to return: ");
            String carID = sc.nextLine();

            Car carToReturn = null;
            for(Car car : cars){
                if(car.getCarId().equals(carID) && !car.isAvailable()){
                    carToReturn = car;
                    break;
                }
            }

            if(carToReturn != null){
                customer customer = null;
                for(Rental rental : rentals){
                    if(rental.getCar() == carToReturn){
                        customer = rental.getCustomer();
                        break;
                    }
                }

                if(customer != null){
                    returnCar(carToReturn);
                    System.out.println("Car returned successfully by " + customer.getName());
                } else {
                    System.out.println("car was not rented or rental infromation is missing.");
                }
            } else {
                System.out.println("invalid car id car is not rented.");
            }
        } else if(choice == 3){
            break;
        } else {
               System.out.println("Invalid choice. Please enter a valid option.");
           }
       }
        System.out.println("\nThank you for using the Car Rental System");
    }
    
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C002","Tata", "Nexon",80.0);
        Car car3 = new Car("C003","Rolls Royce", "Balckbadge",250.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}
