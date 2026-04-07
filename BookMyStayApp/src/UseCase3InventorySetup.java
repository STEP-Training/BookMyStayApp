import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * RoomInventory
 * ============================================================
 * Stores and manages centralized room availability.
 */
class RoomInventory {

    /**
     * Key   -> Room type name
     * Value -> Available room count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes default inventory values.
     */
    public RoomInventory() {
        initializeInventory();
    }

    /**
     * Centralized inventory setup.
     */
    private void initializeInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    /**
     * Returns current availability map.
     */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Updates availability for a given room type.
     */
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/**
 * ============================================================
 * Room (Base Class)
 * ============================================================
 */
class Room1{
    private String type;
    private int beds;
    private int sizeSqFt;
    private double pricePerNight;

    public Room1(String type, int beds, int sizeSqFt, double pricePerNight) {
        this.type = type;
        this.beds = beds;
        this.sizeSqFt = sizeSqFt;
        this.pricePerNight = pricePerNight;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public int getSizeSqFt() {
        return sizeSqFt;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}

/**
 * ============================================================
 * MAIN CLASS – UseCase3InventorySetup
 * ============================================================
 * Use Case 3: Centralized Room Inventory Management
 */
public class UseCase3InventorySetup {

    /**
     * Application entry point.
     */
    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room definitions
        Room1 singleRoom = new Room1("Single", 1, 250, 1500.0);
        Room1 doubleRoom = new Room1("Double", 2, 400, 2500.0);
        Room1 suiteRoom  = new Room1("Suite", 3, 750, 5000.0);

        System.out.println("Hotel Room Inventory Status\n");

        printRoomDetails(singleRoom, inventory);
        printRoomDetails(doubleRoom, inventory);
        printRoomDetails(suiteRoom, inventory);
    }

    /**
     * Prints room details along with availability.
     */
    private static void printRoomDetails(Room1 room, RoomInventory inventory) {
        System.out.println(room.getType() + " Room:");
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Size: " + room.getSizeSqFt() + " sqft");
        System.out.println("Price per night: " + room.getPricePerNight());
        System.out.println(
                "Available Rooms: " +
                        inventory.getRoomAvailability().get(room.getType())
        );
        System.out.println();
    }
}