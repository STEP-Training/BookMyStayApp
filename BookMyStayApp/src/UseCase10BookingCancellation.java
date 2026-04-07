import java.util.*;

class RoomInventory10 {

    private Map<String, Integer> availability;

    public RoomInventory10() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void incrementAvailability(String roomType) {
        availability.put(roomType, availability.get(roomType) + 1);
    }
}

class CancellationService {

    /** Stack that stores recently released room IDs. */
    private Stack<String> releasedRoomIds;

    /** Maps reservation ID to room type. */
    private Map<String, String> reservationRoomTypeMap;

    /** Initializes cancellation tracking structures. */
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory10 inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation request.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        inventory.incrementAvailability(roomType);

        releasedRoomIds.push(reservationId);

        reservationRoomTypeMap.remove(reservationId);

        System.out.println(
                "Booking cancelled successfully. Inventory restored for room type: "
                        + roomType
        );
    }

    public void showRollbackHistory() {

        System.out.println();
        System.out.println("Rollback History (Most Recent First):");

        while (!releasedRoomIds.isEmpty()) {
            System.out.println(
                    "Released Reservation ID: " + releasedRoomIds.pop()
            );
        }
    }
}

/**
 * ============================================================
 * MAIN CLASS – UseCase10BookingCancellation
 * ============================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * This class demonstrates how confirmed
 * bookings can be cancelled safely.
 *
 * Inventory is restored and rollback
 * history is maintained.
 *
 * @version 10.0
 */
public class UseCase10BookingCancellation {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        // Initialize inventory
        RoomInventory10 inventory = new RoomInventory10();

        // Initialize cancellation service
        CancellationService cancellationService =
                new CancellationService();

        // Simulated confirmed booking
        String reservationId = "Single-1";
        String roomType = "Single";

        cancellationService.registerBooking(
                reservationId,
                roomType
        );

        // Perform cancellation
        cancellationService.cancelBooking(
                reservationId,
                inventory
        );

        // Display rollback history
        cancellationService.showRollbackHistory();

        // Display updated inventory
        System.out.println();
        System.out.println(
                "Updated Single Room Availability: "
                        + inventory.getAvailability("Single")
        );
    }
}