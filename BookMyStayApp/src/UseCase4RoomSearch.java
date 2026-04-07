import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * CLASS – Room
 * ============================================================
 * Represents room details such as beds, size and pricing.
 */
class Room4 {

    private String type;
    private int beds;
    private int sizeSqFt;
    private double pricePerNight;

    public Room4(String type, int beds, int sizeSqFt, double pricePerNight) {
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
 * CLASS – RoomInventory
 * ============================================================
 * Centralized room availability storage (read-only for UC4).
 */
class RoomInventory4 {

    private Map<String, Integer> roomAvailability;

    public RoomInventory4() {
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

/**
 * ============================================================
 * CLASS – RoomSearchService
 * ============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class provides search functionality
 * for guests to view available rooms.
 *
 * It reads room availability from inventory
 * and room details from Room objects.
 *
 * No inventory mutation or booking logic
 * is performed in this class.
 *
 * @version 4.0
 */
class RoomSearchService {

    /**
     * Displays available rooms along with details and pricing.
     *
     * This method performs read-only access
     * to inventory and room data.
     *
     * @param inventory centralized room inventory
     * @param singleRoom single room definition
     * @param doubleRoom double room definition
     * @param suiteRoom  suite room definition
     */
    public void searchAvailableRooms(
            RoomInventory4 inventory,
            Room4 singleRoom,
            Room4 doubleRoom,
            Room4 suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search\n");

        // Single Room
        if (availability.get("Single") > 0) {
            printRoom(singleRoom, availability.get("Single"));
        }

        // Double Room
        if (availability.get("Double") > 0) {
            printRoom(doubleRoom, availability.get("Double"));
        }

        // Suite Room
        if (availability.get("Suite") > 0) {
            printRoom(suiteRoom, availability.get("Suite"));
        }
    }

    private void printRoom(Room4 room, int available) {
        System.out.println(room.getType() + " Room:");
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Size: " + room.getSizeSqFt() + " sqft");
        System.out.println("Price per night: " + room.getPricePerNight());
        System.out.println("Available: " + available);
        System.out.println();
    }
}

/**
 * ============================================================
 * MAIN CLASS – UseCase4RoomSearch
 * ============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates how guests can view available
 * rooms without modifying inventory data.
 *
 * @version 4.0
 */
public class UseCase4RoomSearch {

    /**
     * Application entry point.
     */
    public static void main(String[] args) {

        // Initialize inventory (read-only usage)
        RoomInventory4 inventory = new RoomInventory4();

        // Define rooms
        Room4 singleRoom = new Room4("Single", 1, 250, 1500.0);
        Room4 doubleRoom = new Room4("Double", 2, 400, 2500.0);
        Room4 suiteRoom  = new Room4("Suite", 3, 750, 5000.0);

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Perform room search
        searchService.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom
        );
    }
}