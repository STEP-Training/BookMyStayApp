/**
 * UseCase2: Basic Room Types & Static Availability
 *
 * This class demonstrates Use Case 2 of the Book My Stay App:
 * Basic Room Types & Static Availability.
 * It introduces object-oriented concepts such as abstraction,
 * inheritance, and polymorphism while showing static availability.
 *
 * @author STEP Training
 * @version 2.1
 */

// Abstract class representing a generalized Room
abstract class Room {
    protected String type;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public abstract void displayDetails();
}

// Concrete class for Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1,250, 1500.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(type+":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: ₹" + price);
    }
}

// Concrete class for Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2,400, 2500.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(type+":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: ₹" + price);
    }
}

// Concrete class for Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 750, 5000.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(type+":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: ₹" + price);
    }
}

public class App {
    // Static availability variables
    private static int singleRoomAvailability = 5;
    private static int doubleRoomAvailability = 3;
    private static int suiteRoomAvailability = 2;

    /**
     * Entry point of the application.
     * Initializes room objects and prints details with availability.
     *
     * @param args command-line arguments (not used here)
     */
    public static void main(String[] args) {
        // Initialize room objects
        Room single = new SingleRoom();
        Room doubleR = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display room details and availability
        single.displayDetails();
        System.out.println("Availability: " + singleRoomAvailability + "\n");

        doubleR.displayDetails();
        System.out.println("Availability: " + doubleRoomAvailability + "\n");

        suite.displayDetails();
        System.out.println("Availability: " + suiteRoomAvailability + "\n");
    }
}
