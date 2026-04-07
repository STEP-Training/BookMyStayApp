import java.util.*;

/**
 * ============================================================
 * CLASS – InvalidBookingException
 * ============================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * This custom exception represents
 * invalid booking scenarios in the system.
 *
 * Using a domain-specific exception
 * makes error handling clearer and safer.
 *
 * @version 9.0
 */
class InvalidBookingException extends Exception {

    /**
     * Creates an exception with
     * a descriptive error message.
     *
     * @param message error description
     */
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * ============================================================
 * CLASS – RoomInventory
 * ============================================================
 *
 * Provides room availability data
 * for validation purposes.
 *
 * @version 9.0
 */
class RoomInventory9{

    private Map<String, Integer> availability;

    public RoomInventory9() {
        availability = new HashMap<>();
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return availability.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

/**
 * ============================================================
 * CLASS – ReservationValidator
 * ============================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * This class is responsible for validating
 * booking requests before they are processed.
 *
 * All validation rules are centralized
 * to avoid duplication and inconsistency.
 *
 * @version 9.0
 */
class ReservationValidator {

    /**
     * Validates booking input provided by the user.
     *
     * @param guestName name of the guest
     * @param roomType requested room type
     * @param inventory centralized room inventory
     * @throws InvalidBookingException if validation fails
     */
    public void validate(
            String guestName,
            String roomType,
            RoomInventory9 inventory
    ) throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException(
                    "Guest name cannot be empty."
            );
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException(
                    "Invalid room type selected."
            );
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for selected type."
            );
        }
    }
}

/**
 * ============================================================
 * CLASS – BookingRequestQueue
 * ============================================================
 *
 * Simple placeholder to show
 * validated requests can be queued.
 *
 * @version 9.0
 */
class BookingRequestQueue9 {

    private Queue<String> queue = new LinkedList<>();

    public void addRequest(String guestName) {
        queue.offer(guestName);
    }
}

/**
 * ============================================================
 * MAIN CLASS – UseCase9ErrorHandlingValidation
 * ============================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * Demonstrates how user input
 * is validated before booking is processed.
 *
 * The system:
 * - Accepts user input
 * - Validates input centrally
 * - Handles errors gracefully
 *
 * @version 9.0
 */
public class UseCase9ErrorHandlingValidation {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        // Display application header
        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        // Initialize required components
        RoomInventory9 inventory = new RoomInventory9();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue9 bookingQueue = new BookingRequestQueue9();

        try {
            // Accept user input
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print(
                    "Enter room type (Single/Double/Suite): "
            );
            String roomType = scanner.nextLine();

            // Validate input
            validator.validate(
                    guestName,
                    roomType,
                    inventory
            );

            // If validation succeeds, queue request
            bookingQueue.addRequest(guestName);

            System.out.println(
                    "Booking request accepted for " + guestName
            );

        } catch (InvalidBookingException e) {

            // Handle domain-specific validation errors
            System.out.println(
                    "Booking failed: " + e.getMessage()
            );

        } finally {
            scanner.close();
        }
    }
}